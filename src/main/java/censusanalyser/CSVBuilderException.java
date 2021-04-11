package censusanalyser;

public class CSVBuilderException extends Exception {

    public enum ExceptionType {
        NO_FILE, INCORRECT_FILE, UNABLE_TO_PARSE
    }
    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }


}
