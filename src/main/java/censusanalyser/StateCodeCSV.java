package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {
    @CsvBindByName(column = "StateName", required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "StateCodeCSV{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
