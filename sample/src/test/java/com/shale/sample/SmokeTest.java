package com.shale.sample;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple Annotations and stuff.
 */
public class SmokeTest {
    @Test
    public void sillyTest() {
        MyConfiguration generated = MyConfiguration__Generated.populate();
        Assert.assertEquals(1, generated.foo());
        Assert.assertEquals(true, generated.bar());
        Assert.assertEquals("hi", generated.name());
    }
}
