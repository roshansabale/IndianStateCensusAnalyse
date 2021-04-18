package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    List<IndiaCensusCSVDAO> censusList = null;
    List<StateCodeCSV> stateList = null;
    List<IndiaCensusCSVDAO> usList = null;
    Map<String, IndiaCensusCSVDAO> censusStateMap = new HashMap<>();
    Map<String, IndiaCensusCSVDAO> usStateMap = new HashMap<>();
    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusCSVDAO> ();
        this.usList = new ArrayList<IndiaCensusCSVDAO>();
    }
    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
                Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> csvFileIterator;
                StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new IndiaCensusCSVDAO(censusCSV)));
                System.out.println(censusStateMap);
                int count = censusStateMap.size();
                System.out.println(censusStateMap);
                numberOfEnteries = count;
            } else {
                throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
                                                            .ExceptionType.INVALID_FILE_EXTENSION);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException
                                                             .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CensusAnalyserException("Invalid Header or delimeter", CensusAnalyserException
                                                             .ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
        return numberOfEnteries;
    }

    public int loadStateCodeData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int count = 0;
        try {
            if (extension.equalsIgnoreCase("csv") && (censusList == null || censusList.size() == 0)) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder<StateCodeCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<StateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
                Iterable<StateCodeCSV> codeCSVIterable = () -> csvFileIterator;
                StreamSupport.stream(codeCSVIterable.spliterator(), false)
                                            .filter(csvState -> censusStateMap.get(csvState.stateName) != null)
                                            .forEach(csvState -> censusStateMap.get(csvState.stateName)
                                            .stateCode = csvState.stateCode);
                System.out.println(censusStateMap+"\n"+censusStateMap.size());
                count = censusStateMap.size();
            } else {
                throw new CensusAnalyserException("Invalid extension type of file", CensusAnalyserException
                                                             .ExceptionType.INVALID_FILE_EXTENSION);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException
                                                            .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Invalid Header or delimeter", CensusAnalyserException
                                                            .ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
        return count;
    }

    public int loadUSCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<USCensusCSV> usCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
                Iterable<USCensusCSV> usCensusCSVIterable = () -> usCensusCSVIterator;
                StreamSupport.stream(usCensusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> usStateMap.put(censusCSV.state, new IndiaCensusCSVDAO(censusCSV)));

                System.out.println(usStateMap+"\n\n"+usStateMap.size());
                numberOfEnteries = usStateMap.size();
            } else {
                throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
                        .ExceptionType.INVALID_FILE_EXTENSION);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException
                    .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CensusAnalyserException("Invalid Header or delimeter", CensusAnalyserException
                    .ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
        return numberOfEnteries;
    }

    public String findExtenstionTypeOfFile(String pathValue) {
        int index = pathValue.lastIndexOf('.');
        String extension = null;
        if (index > 0) {
            extension = pathValue.substring(index + 1);
        }
        return extension;
    }

    public String getSortedCensusDataByStateName(String country) {
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        String sortedState = getSortedJsonString(censusComparator,country);
        return sortedState;
    }

    public String getSortedCensusDataByStateCode(String country){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        String sortedState = getSortedJsonString(censusComparator,country);
        return sortedState;
    }

    public String getSortedCensusDataByMostPopulationState(String country){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        String sortedState = getSortedJsonString(censusComparator.reversed(),country);
        return sortedState;
    }

    public String getSortedCensusDataByMostDensityState(String country){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        String sortedState = getSortedJsonString(censusComparator.reversed(),country);
        return sortedState;
    }

    public String getSortedCensusDataByLargestAreaState(String country){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        String sortedState = getSortedJsonString(censusComparator.reversed(),country);
        return sortedState;
    }

    public String getSortedJsonString(Comparator<IndiaCensusCSVDAO> censusComparator, String country) {
        String sortedState = null;
        ArrayList sortedList = null;
        if (country.equalsIgnoreCase("india")){
            sortedList = this.censusStateMap
                                        .values()
                                        .stream()
                                        .sorted(censusComparator)
                                        .collect(Collectors.toCollection(ArrayList::new));
        sortedState = new Gson().toJson(sortedList);
        } if (country.equalsIgnoreCase("us")) {
            sortedList = this.usStateMap
                                        .values()
                                        .stream()
                                        .sorted(censusComparator)
                                        .collect(Collectors.toCollection(ArrayList::new));
            sortedState = new Gson().toJson(sortedList);
        }
        System.out.println(sortedList);
        return sortedState;
    }
}