package com.shale.common.lookup;

import java.util.Optional;

public class SystemEnvLookupStrategy implements AbstractLookupStrategy {
    @Override
    public Optional<String> getString(String name) {
        return Optional.ofNullable(System.getenv(name));
    }
}
