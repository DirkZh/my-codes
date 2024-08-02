package com.dirk.zh.cal24;

import org.junit.Assert;
import org.junit.Test;

public class Cal24ErTest {

    @Test
    public void test1() {
        Calculator cal = new Calculator();
        System.out.println(cal.compute("2+(3+(5-(1+2))*3)"));

    }

    @Test
    public void test10() {

        Calculator cal = new Calculator();
        Assert.assertEquals(cal.compute("(3 + 2) * (5 - 1)"), 20);
        Assert.assertEquals(cal.compute("7 - (3 * (4 + 2))"), -11);
        Assert.assertEquals(cal.compute("9 - ((1 + 3) * 2)"), 1);
        Assert.assertEquals(cal.compute("6 * (8 - (2 + 1))"), 30);
        Assert.assertEquals(cal.compute("3 + (4 * (8 - 2))"), 27);

    }

}
