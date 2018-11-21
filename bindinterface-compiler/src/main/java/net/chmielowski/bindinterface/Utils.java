package net.chmielowski.bindinterface;

import java.io.File;
import java.util.Objects;

import javax.annotation.processing.ProcessingEnvironment;

public class Utils {

    private final KotlinFiler kotlinFiler;

    private static Utils instance = null;

    public static void initialize(ProcessingEnvironment pe) {
        instance = null;
        getInstance(pe);
    }

    private synchronized static Utils getInstance(ProcessingEnvironment env) {
        if (instance == null) {
            instance = new Utils(env);
        }
        return instance;
    }

    private Utils(ProcessingEnvironment processingEnv) {
        kotlinFiler = new KotlinFiler(processingEnv);
    }

    public static File newKotlinFile() {
        final Utils result;
        synchronized (Utils.class) {result = getInstance(null);}
        return result.kotlinFiler.newFile();
    }

    public static final class KotlinFiler {
        private final String directory;

        private KotlinFiler(ProcessingEnvironment processingEnv) {
            directory = Objects.requireNonNull(
                    processingEnv.getOptions().get("kapt.kotlin.generated"),
                    "Can't find the target directory for generated Kotlin files."
            );
        }

        File newFile() {
            return new File(Objects.requireNonNull(directory, "Can't generate Kotlin files."));
        }
    }
}
