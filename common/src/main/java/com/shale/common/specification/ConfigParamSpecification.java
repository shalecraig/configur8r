package com.shale.common.specification;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Name;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class ConfigParamSpecification {
    // TODO: populate.
    public static final Set<TypeName> allowedReturnTypes = ImmutableSet.of(TypeName.INT, TypeName.BOOLEAN, TypeName.get(String.class));
    private final Name simpleName;
    private final TypeName returnType;

    // TODO: make this into a builder.
    private ConfigParamSpecification(Name simpleName, TypeName returnType) {
        this.simpleName = simpleName;
        this.returnType = returnType;
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public String getParamKey() {
        return simpleName.toString().toUpperCase(Locale.ENGLISH);
    }

    public TypeName getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "ConfigParamSpecification{" +
                "simpleName=" + simpleName +
                ", returnType=" + returnType +
                '}';
    }

    public void validate() {
        Objects.requireNonNull(simpleName);
        Objects.requireNonNull(returnType);
        if (!allowedReturnTypes.contains(returnType)) {
            throw new AssertionError(String.format("Return type unsupported: `%s`.", returnType));
        }
    }

    public static class Builder {
        private Name simpleName;
        private TypeName returnType;

        public Builder simpleName(Name simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder returnType(TypeName returnType) {
            this.returnType = returnType;
            return this;
        }

        public ConfigParamSpecification build() {
            return new ConfigParamSpecification(simpleName, returnType);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
