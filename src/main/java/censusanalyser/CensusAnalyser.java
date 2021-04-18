package censusanalyser;
import censusanalyser.utility.CensusAdapterFactory;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    Map<String, IndiaCensusCSVDAO> censusMap = null;
    Map<String, IndiaCensusCSVDAO> usIndMap = null;
    public enum Country {INDIA, US}
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusDataObject(country, csvFilePath);
        return censusMap.size();
    }

    public static String findExtenstionTypeOfFile(String pathValue) {
        int index = pathValue.lastIndexOf('.');
        String extension = null;
        if (index > 0) {
            extension = pathValue.substring(index + 1);
        }
        return extension;
    }

    public String getSortedCensusDataByStateName() {
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        String sortedState = getSortedJsonString(censusComparator);
        return sortedState;
    }

    public String getSortedCensusDataByStateCode(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        String sortedState = getSortedJsonString(censusComparator);
        return sortedState;
    }

    public String getSortedCensusDataByMostPopulationState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        String sortedState = getSortedJsonString(censusComparator.reversed());
        return sortedState;
    }

    public String getSortedCensusDataByMostDensityState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        String sortedState = getSortedJsonString(censusComparator.reversed());
        return sortedState;
    }

    public String getSortedCensusDataByLargestAreaState(){
        Comparator<IndiaCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        String sortedState = getSortedJsonString(censusComparator.reversed());
        return sortedState;
    }

    public String getSortedJsonString(Comparator<IndiaCensusCSVDAO> censusComparator) {
        String sortedState = null;
        ArrayList sortedList = null;
        sortedList = this.censusMap
                .values()
                .stream()
                .sorted(censusComparator)
                .collect(Collectors.toCollection(ArrayList::new));
        sortedState = new Gson().toJson(sortedList);
        System.out.println(sortedList);
        return sortedState;
    }
}