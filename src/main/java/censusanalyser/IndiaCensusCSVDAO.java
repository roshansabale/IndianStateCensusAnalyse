package censusanalyser;

public class IndiaCensusCSVDAO {
    public  double densityPerSqKm;
    public  int population;
    public  double areaInSqKm;
    public String state;
    public String stateCode;

    @Override
    public String toString() {
        return "CensusData{"+
                ", densityPerSqKm=" + densityPerSqKm +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", state='" + state + '\'' +
                ", statecode='" + stateCode + '\''+
                '}';
    }

    public IndiaCensusCSVDAO(IndiaCensusCSV indiacensusCSV) {
        state = indiacensusCSV.state;
        areaInSqKm = indiacensusCSV.areaInSqKm;
        population = indiacensusCSV.population;
        densityPerSqKm = indiacensusCSV.densityPerSqKm;
    }
    public IndiaCensusCSVDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        areaInSqKm = usCensusCSV.areaInSqKm;
        population = usCensusCSV.population;
        densityPerSqKm = usCensusCSV.densityPerSqKm;
        stateCode = usCensusCSV.statecode;
    }

}
