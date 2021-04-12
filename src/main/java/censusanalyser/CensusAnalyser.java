package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<StateCodeCSV> stateCSVList = null;

    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder<IndiaCensusCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
                censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
                numberOfEnteries = this.getCount(censusCSVList);
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
            if (extension.equalsIgnoreCase("csv") && (censusCSVList == null || censusCSVList.size() == 0)) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder<StateCodeCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
                stateCSVList = csvBuilder.getCSVFileList(reader, StateCodeCSV.class);
                numberOfEnteries = this.getCount(stateCSVList);
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
            Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
            censusCSVList = this.censusCSVList.stream().sorted(censusComparator).collect(Collectors.toList());
            String sortedState = new Gson().toJson(censusCSVList);
            return sortedState;
    }

    public String getSortedCensusDataByStateCode(){
            Comparator<StateCodeCSV> censusComparator = Comparator.comparing(census -> census.stateCode);
            stateCSVList = this.stateCSVList.stream().sorted(censusComparator).collect(Collectors.toList());
            String sortedStateCode = new Gson().toJson(stateCSVList);
            return sortedStateCode;
    }
}