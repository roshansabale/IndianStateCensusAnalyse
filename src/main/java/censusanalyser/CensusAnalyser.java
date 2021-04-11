package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
                if (extension.equalsIgnoreCase("csv")) {
                    Reader reader = Files.newBufferedReader(Paths.get(filePath));
                    Iterator<IndiaCensusCSV> indiaCensusCSVIterator = this.getCSVFileIterator(reader,IndiaCensusCSV.class);
                    numberOfEnteries = this.getCount(indiaCensusCSVIterator);
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
        }
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

    public int loadStateCodeData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);
        int numberOfEnteries;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                Iterator<StateCodeCSV> stateCSVIterator = this.getCSVFileIterator(reader,StateCodeCSV.class);
                numberOfEnteries = this.getCount(stateCSVIterator);
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
        }
        return numberOfEnteries;
    }

    private<E> Iterator<E>  getCSVFileIterator(Reader reader,Class csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterator = () -> iterator;
        int numberOfEnteries = (int) StreamSupport.stream(csvIterator.spliterator(),false).count();
        return numberOfEnteries;
    }
}
