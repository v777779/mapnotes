package ru.vpcb.map.notes.ext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = 28,
        application = MainApp.class
)
public class ValidationExtTests {

    private String emptyInput = "";
    private String errorInput = "temp-1+2/*?";
    private String validInput = "temp@mail.ru";

    @Before
    public void setUp() throws Exception {

    }

// 0    isValidEmail    input matches pattern

    @Test
    public void isValidEmailInputMatchesPatternReturnTrue() {
        boolean isResult = ValidationExt.isValidEmail(validInput);

        Assert.assertTrue(isResult);
    }

 // 1    isValidEmail    input not matches pattern

    @Test
    public void isValidEmailInputNotMatchesPatternReturnFalse() {
        boolean isResult = ValidationExt.isValidEmail(errorInput);

        Assert.assertFalse(isResult);
    }

// 2    isValidEmail   null input

    @Test
    public void isValidEmailNullInputReturnFalse() {
        boolean isResult = ValidationExt.isValidEmail(null);

        Assert.assertFalse(isResult);
    }

// 3    isValidEmail    empty input

    @Test
    public void isValidEmailEmptyInputReturnFalse() {
        boolean isResult = ValidationExt.isValidEmail(emptyInput);

        Assert.assertFalse(isResult);
    }

    @After
    public void tearDown() throws Exception {

    }
}
