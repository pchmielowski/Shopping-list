package net.chmielowski.bindinterface;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class HasFactoryProcessor extends BaseHasFactoryProcessor {
}

