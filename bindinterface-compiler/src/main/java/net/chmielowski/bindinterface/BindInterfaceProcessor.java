package net.chmielowski.bindinterface;

import com.google.auto.service.AutoService;
import com.squareup.kotlinpoet.*;
import dagger.Module;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Set;

import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static net.chmielowski.bindinterface.FileFactory.newKotlinFile;

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class BindInterfaceProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        FileFactory.initialize(processingEnv);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return singleton("kapt.kotlin.generated");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return singleton(net.chmielowski.bindinterface.BindInterface.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        try {
            generateModule(roundEnv.getElementsAnnotatedWith(BindInterface.class));
        } catch (IOException e) {
            throw new RuntimeException("Error while creating a file: " + e.getMessage());
        }
        return false;
    }

    private void generateModule(final Set<? extends Element> elements) throws IOException {
        if (elements.isEmpty()) {
            return;
        }
        final TypeSpec.Builder moduleBuilder = TypeSpec.classBuilder("GeneratedModule")
                .addModifiers(KModifier.ABSTRACT)
                .addAnnotation(Module.class);
        for (final Element element : elements) {
            final TypeElement typeElement = (TypeElement) element;
            final String[] qualifiers = element.getAnnotation(BindInterface.class)
                    .qualifiers();
            for (@SuppressWarnings("SpellCheckingInspection") final TypeMirror iface : typeElement.getInterfaces()) {
                if (qualifiers.length == 0) {
                    moduleBuilder.addFunction(Utils.INSTANCE.function(typeElement, iface, null));
                }
                for (final String qualifier : qualifiers) {
                    moduleBuilder.addFunction(Utils.INSTANCE.function(typeElement, iface, qualifier));
                }
            }
        }
        FileSpec.builder("net.chmielowski.bindinterface", "GeneratedModule")
                .addType(moduleBuilder.build())
                .build()
                .writeTo(newKotlinFile());
    }

}