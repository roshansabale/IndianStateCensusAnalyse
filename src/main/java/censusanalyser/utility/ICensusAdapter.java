package censusanalyser.utility;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSVDAO;
import com.bridgelabz.CSVBuilderException;

import java.io.IOException;
import java.util.Map;

public interface ICensusAdapter {
    public <E> Map<String, IndiaCensusCSVDAO> loadCensusData(Class<E> censusCSVClass,String... csvFilePath)
            throws CensusAnalyserException, IOException, CSVBuilderException;
}
