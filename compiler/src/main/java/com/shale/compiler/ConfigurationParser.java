package com.shale.compiler;

import com.shale.common.Configuration;
import com.shale.common.specification.ConfigParamSpecification;
import com.shale.common.specification.ConfigSpecification;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Utility class ConfigurationParser.
 */
public final class ConfigurationParser {
    private ConfigurationParser() {
        throw new UnsupportedOperationException("The utility class 'ConfigurationParser' cannot be instantiated.");
    }

    // TODO: remove validation from parseConfiguration? (e.g. `myConfigurationSpecification.validate()`)
    private static void checkInput(boolean okay, String format, Object... args) {
        if (!okay) {
            throw new AssertionError(String.format(format, args));
        }
    }

    public static ConfigSpecification parseConfiguration(
            Element element,
            ProcessingEnvironment processingEnv,
            RoundEnvironment roundEnv) {

        // TODO: use a visitor instead?
        Name className = element.getSimpleName();
        if (!(element instanceof TypeElement)) {
            throw new AssertionError(String.format("The %s annotation was found on %s, but it wasn't a %s", Configuration.class.getSimpleName(), className, TypeElement.class.getSimpleName()));
        }
        TypeElement annotationElement = (TypeElement) element;

        Element maybeEnclosingElement = element.getEnclosingElement();
        if (!(maybeEnclosingElement instanceof PackageElement)) {
            throw new AssertionError(String.format("Expected %s to be a top level class, but it wasn't.", className));
        }
        PackageElement packageElement = (PackageElement) maybeEnclosingElement;

        ConfigSpecification.Builder configSpecification = ConfigSpecification
                .builder()
                .baseClass(TypeName.get(element.asType()))
                .className(className)
                .packageName(packageElement.getQualifiedName());
        List<? extends Element> enclosedElements = annotationElement.getEnclosedElements();

        enclosedElements
                .stream()
                .map(enclosed -> {
                    // TODO: use a visitor instead?
                    if (!(enclosed instanceof ExecutableElement)) {
                        // TODO: should I filter, or just skip?
                        throw new AssertionError(String.format("Element %s.%s was not an %s", className, enclosed.getSimpleName(), ExecutableElement.class.getSimpleName()));
                    }
                    return (ExecutableElement) enclosed;
                })
                .filter(executableElement -> {
                    checkInput(executableElement.getParameters().isEmpty(), "Element %s.%s had parameters. It shouldn't.", className, executableElement.getSimpleName());
                    checkInput(executableElement.getThrownTypes().isEmpty(), "Element %s.%s throws something. It shouldn't.", className, executableElement.getSimpleName());
                    return true;
                })
                .map(executableElement -> ConfigParamSpecification
                        .builder()
                        .returnType(TypeName.get(executableElement.getReturnType()))
                        .simpleName(executableElement.getSimpleName())
                        .build())
                .forEach(configSpecification::addParamSpecification);
        return configSpecification.build();
    }
}
