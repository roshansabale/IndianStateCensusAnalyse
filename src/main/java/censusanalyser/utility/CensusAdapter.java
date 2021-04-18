package censusanalyser.utility;

import censusanalyser.*;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter implements ICensusAdapter{
    Map<String, IndiaCensusCSVDAO> censusMap = new HashMap<>();

    public <E> Map<String, IndiaCensusCSVDAO> loadCensusData(Class<E> censusCSVClass,String... filePath) throws CensusAnalyserException {
        String extension = CensusAnalyser.findExtenstionTypeOfFile(filePath[0]);
        try {
            if (extension.equalsIgnoreCase("csv")) {
                Reader reader = Files.newBufferedReader(Paths.get(filePath[0]));
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> indiaCensusCSVIterable = () -> csvFileIterator;
                String className = censusCSVClass.getName();

                switch (className) {
                    case "censusanalyser.IndiaCensusCSV":
                        StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false)
                                .map(IndiaCensusCSV.class::cast)
                                .forEach(censusCSV -> censusMap.put(censusCSV.state, new IndiaCensusCSVDAO(censusCSV)));
                        System.out.println(censusMap);

                        if (filePath.length == 2) {
                            try (Reader codeReader = Files.newBufferedReader(Paths.get(filePath[1]))) {
                                Iterator<StateCodeCSV> stateCodeIterator = CSVBuilderFactory.createCSVBuilder()
                                        .getCSVFileIterator(codeReader, StateCodeCSV.class);
                                Iterable<StateCodeCSV> codeCSVIterable = () -> stateCodeIterator;
                                StreamSupport.stream(codeCSVIterable.spliterator(), false)
                                        .filter(csvState -> censusMap.get(csvState.stateName) != null)
                                        .forEach(csvState -> censusMap.get(csvState.stateName).stateCode = csvState.stateCode);
                            }
                            System.out.println(censusMap);
                        }
                        break;
                    case "censusanalyser.USCensusCSV":
                        StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false)
                                .map(USCensusCSV.class::cast)
                                .forEach(csvCensus -> censusMap.put(csvCensus.state, new IndiaCensusCSVDAO(csvCensus)));
                        break;
                }
                return censusMap;
            }
            throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException
                                                                .ExceptionType.INVALID_FILE_EXTENSION);
        }catch(IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException
                                                                .ExceptionType.CENSUS_FILE_PROBLEM);
        } catch(RuntimeException e){
            throw new CensusAnalyserException("Invalid Header or delimeter", CensusAnalyserException
                                                                .ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY);
        } catch(CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}
