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
public class BindInterfaceProcessor extends BaseBindInterfaceProcessor {}

