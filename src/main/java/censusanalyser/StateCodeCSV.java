package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {
    @CsvBindByName(column = "StateName", required = true)
    private String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    private String stateCode;

    @Override
    public String toString() {
        return "StateCodeCSV{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
