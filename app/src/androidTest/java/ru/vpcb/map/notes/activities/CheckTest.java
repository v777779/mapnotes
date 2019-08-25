package ru.vpcb.map.notes.activities;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

@RunWith(AndroidJUnit4.class)
public class CheckTest extends MockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void name() {
        String s  = permissionRule.toString();
        locationProvider.startLocationUpdates();
        int k = 1;
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
