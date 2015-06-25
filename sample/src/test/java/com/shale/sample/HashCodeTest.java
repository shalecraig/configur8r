package com.shale.sample;

import org.apache.commons.lang3.AnnotationUtils;
import org.junit.Assert;
import org.junit.Test;

public class HashCodeTest {
    @Test
    public void testFoo() throws Exception {
        MyConfiguration generated = MyConfiguration__Generated.populate();
        int properCode = AnnotationUtils.hashCode(generated);
        Assert.assertEquals(properCode, generated.hashCode());
    }
}
