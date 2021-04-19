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

    Map<String, IndiaCensusCSVDAO> censusMap = new HashMap<>();
    Map<String, IndiaCensusCSVDAO> usIndMap = new HashMap<>();
    public enum Country {INDIA, US}
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusDataObject(country, csvFilePath);
        usIndMap.putAll(censusMap);
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

    public String getMostPopulaousAndDensityStateFromUsAndInd (String ...csvFilePath) throws CensusAnalyserException {
        this.loadCensusData(Country.INDIA,csvFilePath[0]);
        this.loadCensusData(Country.US,csvFilePath[1]);
         /* Comparator.comparing((IndiaCensusCSVDAO p)->p.population)
                .thenComparing(p->p.densityPerSqKm);*/
        /*System.out.println(usIndMap.get(Comparator.comparing((IndiaCensusCSVDAO p)->p.population).reversed()
                .thenComparing(p->p.densityPerSqKm)).stateCode);*/
        /*Comparator.comparingInt((IndiaCensusCSVDAO p)->p.population)
                .thenComparingDouble(p->p.densityPerSqKm).toString();*/
        //System.out.println("Values"+usIndMap.values().stream().sorted().filter((a->a.population>0)).filter((a->a.densityPerSqKm>0)).collect(Collectors.toList()));
        List<IndiaCensusCSVDAO> result2 = new ArrayList(usIndMap.values());
        Comparator<IndiaCensusCSVDAO> compareByName = Comparator
                .comparing(IndiaCensusCSVDAO::getPopulation)
                .thenComparing(IndiaCensusCSVDAO::getDensityPerSqKm);
        List<IndiaCensusCSVDAO> sortedState = result2.stream()
                .sorted(compareByName.reversed())
                .collect(Collectors.toList());
        System.out.println("LargetsPopulation and Density in US and India\t: StateName: "+ sortedState.get(0).state + " Population: "+ sortedState.get(0).population + " Density:" + sortedState.get(0).densityPerSqKm );
        System.out.println(usIndMap.size()+"Sorted list size()"+result2.size());
        return sortedState.get(0).state;
       // System.out.println("Result"+result2);

        //System.out.println("USIND"+"\n\n"+usIndMap);
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