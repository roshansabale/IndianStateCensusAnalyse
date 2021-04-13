package censusanalyser;

public class IndiaCensusCSVDAO {
    public String stateName;
    public String stateCode;
    public  int densityPerSqKm;
    public  int population;
    public  int areaInSqKm;
    public String state;

    public IndiaCensusCSVDAO(IndiaCensusCSV indiacensusCSV) {
        state = indiacensusCSV.state;
        areaInSqKm = indiacensusCSV.areaInSqKm;
        population = indiacensusCSV.population;
        densityPerSqKm = indiacensusCSV.densityPerSqKm;
    }

    public IndiaCensusCSVDAO(StateCodeCSV stateCodeCSV) {
         stateCode = stateCodeCSV.stateCode;
         stateName = stateCodeCSV.stateName;
    }
}
