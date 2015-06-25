package com.shale.common.specification;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ConfigSpecification {
    private final Name className;
    private final List<ConfigParamSpecification> paramSpecifications;
    private final Name packageName;
    private final TypeName baseClass;

    public ConfigSpecification(Name className, List<ConfigParamSpecification> paramSpecifications, Name packageName, TypeName baseClass) {
        this.className = className;
        this.packageName = packageName;
        this.baseClass = baseClass;
        this.paramSpecifications = new ArrayList<>(paramSpecifications);
    }

    public Name getClassName() {
        return className;
    }

    public List<ConfigParamSpecification> getParamSpecifications() {
        return paramSpecifications;
    }

    @Override
    // TODO: remove or update.
    public String toString() {
        return "ConfigSpecification{" +
                "className=" + className +
                ", paramSpecifications=" + paramSpecifications +
                ", packageName=" + packageName +
                ", baseClass=" + baseClass +
                '}';
    }

    public void validate() {
        Objects.requireNonNull(className);
        Objects.requireNonNull(paramSpecifications);
        if (paramSpecifications.isEmpty()) {
            throw new AssertionError("paramSpecifications.isEmpty()");
        }
        paramSpecifications.forEach(ConfigParamSpecification::validate);
    }

    public Name getPackageName() {
        return packageName;
    }

    public TypeName getBaseClass() {
        return baseClass;
    }

    public static final class Builder {
        public List<ConfigParamSpecification> paramSpecifications;
        private Name className;
        private Name packageName;
        private TypeName baseClass;

        private Builder() {
            paramSpecifications = new LinkedList<>();
        }

        public Builder className(Name className) {
            this.className = className;
            return this;
        }

        public Builder addParamSpecification(ConfigParamSpecification... paramSpecifications) {
            Collections.addAll(this.paramSpecifications, paramSpecifications);
            return this;
        }

        public Builder packageName(Name packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder baseClass(TypeName element) {
            this.baseClass = element;
            return this;
        }

        public ConfigSpecification build() {
            return new ConfigSpecification(className, paramSpecifications, packageName, baseClass);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
