package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class CensusAnalyser {
    public int loadIndiaCensusData(String filePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(filePath);

        int numberOfEnteries = 0;
        try {
                if (extension.equalsIgnoreCase("csv")) {
                    Reader reader = Files.newBufferedReader(Paths.get(filePath));
                    CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                    csvToBeanBuilder.withType(IndiaCensusCSV.class);
                    csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                    CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
                    Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();

                    while (censusCSVIterator.hasNext()) {
                        numberOfEnteries++;
                        IndiaCensusCSV censusData = censusCSVIterator.next();
                    }
                } else {
                    throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
                                                                                            .ExceptionType
                                                                                            .INVALID_FILE_EXTENSION);
                }
            return numberOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
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

    public long loadStateCodeData(String filePath) {
        String extension = findExtenstionTypeOfFile(filePath);
        long numberOfEnteries = 0;
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CsvToBeanBuilder<StateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(StateCodeCSV.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<StateCodeCSV> csvToBean = csvToBeanBuilder.build();
                List<StateCodeCSV> stateCodeCSVIterator = csvToBean.parse();
                numberOfEnteries = stateCodeCSVIterator.stream().count();
                System.out.println("Number of Entries"+numberOfEnteries);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfEnteries;
    }
}
