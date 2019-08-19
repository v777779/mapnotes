package ru.vpcb.map.notes.data.formatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateFormatterTests {
    private double sydneyLat;
    private double sydneyLon;
    private String sydneyExpectedResult;
    
    private CoordinateFormatter formatter;

    @Before
    public void setUp() throws Exception {

        sydneyLat = -33.8651;
        sydneyLon = 151.2099;
        sydneyExpectedResult = "(-33.8651; 151.2099)";

        formatter = new CoordinateFormatter();
    }

// 0    format  any input

    @Test
    public void formatInputFormattedOutput() {
        Assert.assertEquals(sydneyExpectedResult, formatter.format(sydneyLat,sydneyLon));
    }

    @After
    public void tearDown() throws Exception {
        
    }
}
