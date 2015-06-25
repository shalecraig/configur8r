package com.shale.sample;

import com.squareup.burst.BurstJUnit4;
import org.apache.commons.lang3.AnnotationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(BurstJUnit4.class)
public class EqualityTest {
    private final int foo;
    private final boolean bar;
    private final String name;

    public EqualityTest(FOO foo, BARS bar, NAMES name) {
        this.foo = foo.create();
        this.bar = bar.create();
        this.name = name.create();
    }

    @Test
    public void equalityTest() {
        MyConfiguration generated = MyConfiguration__Generated.populate();
        MyConfiguration variant = new InstantiableConfiguration(foo, bar, name);

        boolean expected = AnnotationUtils.equals(generated, variant);
        Assert.assertEquals(expected, generated.equals(variant));
    }

    // Used for Burst.
    public enum FOO {
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})ONE {
            @Override
            public int create() {
                return 1;
            }
        },
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})ZERO {
            @Override
            public int create() {
                return 0;
            }
        };

        public abstract int create();
    }

    public enum BARS {
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})TRUE {
            @Override
            public boolean create() {
                return true;
            }
        },
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})FALSE {
            @Override
            public boolean create() {
                return false;
            }
        };

        public abstract boolean create();
    }

    public enum NAMES {
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})HI() {
            @Override
            public String create() {
                return "hi";
            }
        },
        @SuppressWarnings({"unused", "InnerClassTooDeeplyNested"})NO_MATCH() {
            @Override
            public String create() {
                return "NO_MATCH";
            }
        };

        public abstract String create();
    }

}
