package com.shale.compiler;

import com.shale.common.Configuration;
import com.shale.common.provider.ConfigurationSupplier;
import com.shale.common.specification.ConfigSpecification;
import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 */
public class ConfigurationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Configuration.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Configuration.class);

        for (Element element : elements) {
            ConfigSpecification spec = ConfigurationParser.parseConfiguration(element, processingEnv, roundEnv);
            spec.validate();

            ConfigurationSupplier provider = ConfigurationLookup.lookup(spec, processingEnv, roundEnv);

            JavaFile file = ConfigurationWriter.write(spec, provider, processingEnv, roundEnv);
            try {
                file.writeTo(processingEnv.getFiler());
                file.writeTo(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
