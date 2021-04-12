package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        List<IndiaCensusCSV> indiaCensusCSVList = null;
        int numberOfEnteries;
        try {
                if (extension.equalsIgnoreCase("csv")) {
                    Reader reader = Files.newBufferedReader(Paths.get(filePath));
                    ICSVBuilder<IndiaCensusCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
                    //Iterator<IndiaCensusCSV> indiaCensusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
                    //numberOfEnteries = this.getCount(indiaCensusCSVIterator);
                    indiaCensusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
                    numberOfEnteries = this.getCount(indiaCensusCSVList);
                    //numberOfEnteries = indiaCensusCSVList.size();
                    System.out.println(indiaCensusCSVList);
                } else {
                    throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
                                                                                            .ExceptionType.INVALID_FILE_EXTENSION);
                }
            return numberOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException
                                                                            .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Invalid Header or delimeter",CensusAnalyserException
                                                                            .ExceptionType.INVALID_DELIMETER_OR_HEADER);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadStateCodeData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        List<StateCodeCSV> stateCSVList = null;
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                ICSVBuilder<StateCodeCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
                //Iterator<StateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,StateCodeCSV.class);
                stateCSVList = csvBuilder.getCSVFileList(reader,StateCodeCSV.class);
                numberOfEnteries = this.getCount(stateCSVList);
            } else {
                throw new CensusAnalyserException("Invalid extension type of file",CensusAnalyserException
                                                                                        .ExceptionType.INVALID_FILE_EXTENSION);
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException
                                                                    .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Invalid Header or delimeter",CensusAnalyserException
                                                                                        .ExceptionType.INVALID_DELIMETER_OR_HEADER);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
        return numberOfEnteries;
    }
    public String findExtenstionTypeOfFile(String pathValue) {
        int index = pathValue.lastIndexOf('.');
        String extension = null;
        if( index > 0) {
            extension = pathValue.substring(index + 1);
            System.out.println("File extension is " + extension);
        }
        return extension;
    }

    private <E> int getCount(List<E> list) {
        //Iterable<E> csvIterator = () -> list;
        List<E> csvList = list;
        //int numberOfEnteries = (int) StreamSupport.stream(csvIterator.spliterator(),false).count();
        int numberOfEnteries = csvList.size();
        return numberOfEnteries;
    }
}
