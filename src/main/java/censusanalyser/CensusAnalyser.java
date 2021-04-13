package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class CensusAnalyser {
    List<IndiaCensusCSVDAO> censusList = null;
    List<IndiaCensusCSVDAO> stateList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusCSVDAO> ();
        this.stateList = new ArrayList<IndiaCensusCSVDAO>();
    }

    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
                while(csvFileIterator.hasNext()) {
                   this.censusList.add(new IndiaCensusCSVDAO(csvFileIterator.next()));
                }

                numberOfEnteries = this.getCount(censusList);
            } else {
                throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
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
        return numberOfEnteries;
    }

    public int loadStateCodeData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);

        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv") && (censusList == null || censusList.size() == 0)) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<StateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
                while(csvFileIterator.hasNext()) {
                    this.stateList.add(new IndiaCensusCSVDAO(csvFileIterator.next()));
                }
                numberOfEnteries = this.getCount(stateList);
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

    private <E> int getCount(List<E> list) {
        List<E> csvList = list;
        int numberOfEnteries = csvList.size();
        return numberOfEnteries;
    }

    public String getSortedCensusDataByStateName() {
            Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
            censusList = this.censusList.stream().sorted(censusComparator).collect(Collectors.toList());
            String sortedState = new Gson().toJson(censusList);
            return sortedState;
    }

    public String getSortedCensusDataByStateCode(){
            Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
            stateList = this.stateList.stream().sorted(censusComparator).collect(Collectors.toList());
            String sortedStateCode = new Gson().toJson(stateList);
            return sortedStateCode;
    }

    public String getSortedCensusDataByMostPopulationState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedState = new Gson().toJson(censusList);
        return sortedState;
    }

    public String getSortedCensusDataByMostDensityState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedState = new Gson().toJson(censusList);
        return sortedState;
    }

    public String getSortedCensusDataByLargestAreaState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedState = new Gson().toJson(censusList);
        return sortedState;
    }
}