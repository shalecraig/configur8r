package com.shale.common.lookup;

import java.util.Optional;

public interface ConfigLookupStrategy {
    Optional<String> getString(String name);
    // Sigh.
    Optional<Integer> getInteger(String name);
    Optional<Boolean> getBoolean(String name);
}
