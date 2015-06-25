package com.shale.compiler;

import com.shale.common.provider.ConfigurationSupplier;
import com.shale.common.specification.ConfigParamSpecification;
import com.shale.common.specification.ConfigSpecification;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.AnnotationUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class ConfigurationWriter.
 */
public final class ConfigurationWriter {

    public static final String GENERATED = "__Generated";
    public static final String POPULATE = "populate";
    public static final AnnotationSpec OVERRIDES_ANNOTATION_SPEC = AnnotationSpec.builder(Override.class).build();

    private ConfigurationWriter() {
        throw new UnsupportedOperationException("The utility class 'ConfigurationWriter' cannot be instantiated.");
    }

    public static JavaFile write(ConfigSpecification spec, ConfigurationSupplier provider, ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {

        TypeSpec.Builder annotationImpl = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(spec.getBaseClass());

        for (ConfigParamSpecification configParamSpecification : spec.getParamSpecifications()) {

            String key = configParamSpecification.getParamKey();
            Supplier<Optional<?>> paramSupplier = provider.getParamSupplier(key);
            // TODO: ugggh this is so bad. The paramSupplier should hide that the contents are known at compile-time (or not)
            Optional<?> realValue = paramSupplier.get();
            TypeName returnType = configParamSpecification.getReturnType();
            String simpleName = configParamSpecification.getSimpleName().toString();
            if (!realValue.isPresent()) {
                throw new AssertionError(String.format("Was expecting the %s %s.%s to be populated, but it wasn't.", returnType, simpleName, key));
            }
            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(simpleName)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(OVERRIDES_ANNOTATION_SPEC)
                    .returns(returnType);
            if (returnType.equals(ConfigurationLookup.STRING_TYPE_NAME)) {
                methodSpecBuilder.addStatement("return $S", realValue.get());
            } else {
                methodSpecBuilder.addStatement("return $L", realValue.get());
            }
            MethodSpec methodSpec = methodSpecBuilder.build();
            annotationImpl.addMethod(methodSpec);
        }

        MethodSpec equals = MethodSpec.methodBuilder("equals")
                .addParameter(TypeName.OBJECT, "o")
                .addAnnotation(OVERRIDES_ANNOTATION_SPEC)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.BOOLEAN)
                .addStatement("if (this == o) return true")
                .addStatement("if (!(o instanceof $T)) return false", spec.getBaseClass())
                        // TODO: implement this without using reflection.
                .addStatement("return $T.equals(this, ($T) o)", AnnotationUtils.class, spec.getBaseClass())
                .build();
        annotationImpl.addMethod(equals);
        MethodSpec hashCode = MethodSpec.methodBuilder("hashCode")
                .addAnnotation(OVERRIDES_ANNOTATION_SPEC)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.INT)
                        // TODO: implement this without using reflection.
                .addStatement("return $T.hashCode(this)", AnnotationUtils.class)
                .build();
        annotationImpl.addMethod(hashCode);

        MethodSpec annotationType = MethodSpec.methodBuilder("annotationType")
                .addAnnotation(OVERRIDES_ANNOTATION_SPEC)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), spec.getBaseClass()))
                .addStatement("return $T.class", spec.getBaseClass())
                .build();
        annotationImpl.addMethod(annotationType);

        MethodSpec loadConfiguration = MethodSpec.methodBuilder(POPULATE)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .returns(spec.getBaseClass())
                .addCode(CodeBlock.builder().add("//TODO: implement hashCode.\n").build())
                .addStatement("return $L", annotationImpl.build())
                .build();
        TypeSpec outer = TypeSpec.classBuilder(spec.getClassName() + GENERATED)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(loadConfiguration)
                .build();
        return JavaFile
                .builder(spec.getPackageName().toString(), outer)
                .addFileComment("----------------------------\n")
                .addFileComment("Editing this file is futile!\n")
                .addFileComment("----------------------------")
                .build();
    }
}
