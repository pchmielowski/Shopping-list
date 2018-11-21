package net.chmielowski.bindinterface;

import java.io.File;
import java.util.Objects;

import javax.annotation.processing.ProcessingEnvironment;

class FileFactory {

    private static ProcessingEnvironment environment;

    static void initialize(final ProcessingEnvironment env) {
        environment = env;
    }

    static File newKotlinFile() {
        final String directory = Objects.requireNonNull(
                environment.getOptions().get("kapt.kotlin.generated"),
                "Can't find the target directory for generated Kotlin files."
        );
        return new File(directory);
    }
}
