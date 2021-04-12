package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String CENSUS_WITHDIFFERENT_DELIMETER_PATH = "./src/test/resources/IndiaStateCensusDataDiffDelimeter1.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_EXTENSION_FILE_PATH = "./src/main/resources/IndiaStateCensusData.txt";
    private static final String WRONG_FILE_HEADER = "./src/test/resources/IndiaStateCensusDataWrongHeader.csv";
    private static final String STATE_CODE_DATA_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() throws CensusAnalyserException  {
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
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY,e.type);
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
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY,e.type);
        }
    }

    @Test
    public void givenStateCodeData_ReturnExactRecordCount() throws CensusAnalyserException {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            long numOfRecords = censusAnalyser.loadStateCodeData(STATE_CODE_DATA_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
    }

    @Test
    public void givenStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadStateCodeData(WRONG_EXTENSION_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadStateCodeData(CENSUS_WITHDIFFERENT_DELIMETER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadStateCodeData(WRONG_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER_OR_FILE_EMPTY,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getSortedCensusDataByStateName();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
    }

    @Test
    public void getStateCodeCSVData_WhenSortedOnStateCode_ShouldReturnSortedResut() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadStateCodeData(STATE_CODE_DATA_FILE_PATH);
        String sortedState = censusAnalyser.getSortedCensusDataByStateCode();
        StateCodeCSV[] stateCodeCSVS = new Gson().fromJson(sortedState, StateCodeCSV[].class);
        Assert.assertEquals("AD",stateCodeCSVS[0].stateCode);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnMostPopulation_ShouldReturnSortedResut() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedState = censusAnalyser.getSortedCensusDataByMostPopulationState();
        IndiaCensusCSV[] censusCSVS = new Gson().fromJson(sortedState, IndiaCensusCSV[].class);
        Assert.assertEquals(199812341,censusCSVS[0].population);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnMostDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedState = censusAnalyser.getSortedCensusDataByMostDensityState();
        IndiaCensusCSV[] censusCSVS = new Gson().fromJson(sortedState, IndiaCensusCSV[].class);
        Assert.assertEquals("Bihar",censusCSVS[0].state);
    }
}
