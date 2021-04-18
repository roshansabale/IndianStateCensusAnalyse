package censusanalyser.utility;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.IndiaCensusCSVDAO;
import com.bridgelabz.CSVBuilderException;

import java.io.IOException;
import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter{

    public Map<String, IndiaCensusCSVDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException{
        return super.loadCensusData(IndiaCensusCSV.class, csvFilePath);
    }
}
