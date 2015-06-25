package com.shale.compiler;

import com.shale.common.lookup.ConfigLookupStrategy;
import com.shale.common.lookup.SystemEnvLookupStrategy;
import com.shale.common.provider.ConfigurationSupplier;
import com.shale.common.specification.ConfigParamSpecification;
import com.shale.common.specification.ConfigSpecification;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public final class ConfigurationLookup {

    public static final TypeName STRING_TYPE_NAME = TypeName.get(String.class);

    private ConfigurationLookup() {
    }

    public static ConfigurationSupplier lookup(ConfigSpecification spec, ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        ConfigurationSupplier.Builder configurationSupplier = ConfigurationSupplier.builder();
        for (ConfigParamSpecification configParamSpecification : spec.getParamSpecifications()) {
            String key = configParamSpecification.getParamKey();
            ConfigLookupStrategy ls = new SystemEnvLookupStrategy();
            TypeName returnType = configParamSpecification.getReturnType();
            if (returnType.equals(STRING_TYPE_NAME)) {
                configurationSupplier.addParam(key, () -> ls.getString(key));
            } else if (returnType.equals(TypeName.BOOLEAN)) {
                configurationSupplier.addParam(key, () -> ls.getBoolean(key));
            } else if (returnType.equals(TypeName.INT)) {
                configurationSupplier.addParam(key, () -> ls.getInteger(key));
            }                            else {
                throw new AssertionError(String.format("Unsupported type: %s!", returnType));
            }
        }
        return configurationSupplier.build();
    }
}
