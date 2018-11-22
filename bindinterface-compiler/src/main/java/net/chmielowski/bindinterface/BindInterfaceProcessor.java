package net.chmielowski.bindinterface;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static net.chmielowski.bindinterface.FileFactory.newKotlinFile;
import static net.chmielowski.bindinterface.KputilsKt.fileWithModule;

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
        if (set.isEmpty()) {
            return false;
        }
        try {
            fileWithModule(roundEnv)
                    .writeTo(newKotlinFile());
        } catch (IOException e) {
            throw new RuntimeException("Error while creating a file: " + e.getMessage());
        }
        return false;
    }
}