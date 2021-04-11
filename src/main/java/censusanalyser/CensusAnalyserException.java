package censusanalyser;

public class CensusAnalyserException extends Exception {

   enum ExceptionType {
        RECORD_COUNT_NOT_MATCH,CENSUS_FILE_PROBLEM,INVALID_FILE_EXTENSION,INVALID_DELIMETER_OR_HEADER;
    }

    public CensusAnalyserException(String message, String name) {
       super(message);
       this.type = ExceptionType.valueOf(name);
    }
    ExceptionType type;

        public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
