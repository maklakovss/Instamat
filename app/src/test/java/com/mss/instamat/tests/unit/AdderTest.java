package com.mss.instamat.tests.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdderTest {

    private Adder adder;

    @Before
    public void setUp() throws Exception {
        adder = new Adder();
    }

    @After
    public void tearDown() throws Exception {
        adder = null;
    }

    @Test
    public void twoAndThree_add_isEqualsFive() {
        assertEquals(adder.add(2, 3), 5);
    }

    @Test
    public void twoAndThree_add_isNotEqualsSix() {
        assertNotEquals(adder.add(2, 3), 4);
    }
}