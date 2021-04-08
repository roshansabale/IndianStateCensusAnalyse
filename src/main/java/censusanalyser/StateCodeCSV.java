package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    private String stateName;

    @CsvBindByName(column = "TIN", required = true)
    private String TIN;

    @CsvBindByName(column = "StateCode", required = true)
    private String stateCode;

    @Override
    public String toString() {
        return "StateCodeCSV{" +
                "stateName='" + stateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
