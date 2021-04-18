package censusanalyser.utility;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSVDAO;
import censusanalyser.USCensusCSV;
import com.bridgelabz.CSVBuilderException;

import java.io.IOException;
import java.util.Map;

public class USCensusAdapter extends CensusAdapter{

    public Map<String, IndiaCensusCSVDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return loadCensusData(USCensusCSV.class, csvFilePath);
    }
}
