package com.shale.common.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class ConfigurationSupplier {
    private final Map<String, Supplier> paramSuppliers;

    private ConfigurationSupplier(Map<String, Supplier> paramSuppliers) {
        // TODO: make this immutable
        this.paramSuppliers = new HashMap<>(paramSuppliers);
    }

    // TODO: consider removing this?
    @SuppressWarnings("unchecked")
    private static <T> Supplier<T> cast(Supplier<?> uncastSupplier) {
        return (Supplier<T>) uncastSupplier;
    }

    public <T> Supplier<T> getParamSupplier(String key) {
        // this is gross, but there's no way to annotate only one statement.
        return cast(paramSuppliers.get(key));
    }

    public static final class Builder {
        private Map<String, Supplier> paramSuppliers;

        private Builder() {
            paramSuppliers = new HashMap<>();
        }

        public <T> Builder addParam(String key, Supplier<Optional<T>> p1) {
            paramSuppliers.put(key, p1);
            return this;
        }

        public ConfigurationSupplier build() {
            return new ConfigurationSupplier(paramSuppliers);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
