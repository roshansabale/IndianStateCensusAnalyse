package censusanalyser;

public class IndiaCensusCSVDAO {
    public  int densityPerSqKm;
    public  int population;
    public  int areaInSqKm;
    public String state;
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaCensusCSVDAO{" +
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
}
