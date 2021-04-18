package censusanalyser.utility;
import censusanalyser.*;
import com.bridgelabz.CSVBuilderException;

import java.io.IOException;
import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, IndiaCensusCSVDAO> getCensusDataObject(CensusAnalyser.Country country,
                                                                     String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        return new USCensusAdapter().loadCensusData(csvFilePath);
    }
}
