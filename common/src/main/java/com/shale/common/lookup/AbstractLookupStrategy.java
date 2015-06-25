package com.shale.common.lookup;

import java.util.Optional;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface AbstractLookupStrategy extends ConfigLookupStrategy {
    @Override
    default Optional<Integer> getInteger(String name) {
        return getString(name).map(Integer::parseInt);
    }

    @Override
    default Optional<Boolean> getBoolean(String name) {
        return getString(name).map(Boolean::valueOf);
    }
}
