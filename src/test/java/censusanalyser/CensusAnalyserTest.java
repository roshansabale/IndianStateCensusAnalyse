package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String CENSUS_WITHDIFFERENT_DELIMETER_PATH = "./src/test/resources/IndiaStateCensusDataDiffDelimeter1.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_EXTENSION_FILE_PATH = "./src/main/resources/IndiaStateCensusData.txt";
    private static final String WRONG_FILE_HEADER = "./src/test/resources/IndiaStateCensusDataWrongHeader.csv";
    private static final String STATE_CODE_DATA_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() throws CensusAnalyserException {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_EXTENSION_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(CENSUS_WITHDIFFERENT_DELIMETER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER,e.type);
        }
    }

    @Test
    public void givenStateCodeData_ReturnExactRecordCount()  {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            long numOfRecords = censusAnalyser.loadStateCodeData(STATE_CODE_DATA_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        }

}
