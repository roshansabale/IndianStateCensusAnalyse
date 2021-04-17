package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "StateId", required = true)
    public String statecode;
    @CsvBindByName(column = "State", required = true)
    public String state;
    @CsvBindByName(column = "Population", required = true)
    public int population;
    @CsvBindByName(column = "PopulationDensity", required = true)
    public double densityPerSqKm;
    @CsvBindByName(column = "TotalArea", required = true)
    public double areaInSqKm;

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "statecode='" + statecode + '\'' +
                ", state='" + state + '\'' +
                ", population='" + population + '\'' +
                ", densityPerSqKm='" + densityPerSqKm + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                '}';
    }
}
