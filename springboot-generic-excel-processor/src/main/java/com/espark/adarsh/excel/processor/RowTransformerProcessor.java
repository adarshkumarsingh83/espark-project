package com.espark.adarsh.excel.processor;


import com.espark.adarsh.excel.annotation.RowTransformer;
import com.espark.adarsh.excel.transformer.row.RowTransformers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Component
public class RowTransformerProcessor {

    private static final Map<String, Object> rowTransformerMap = new HashMap<>();

    private final ConfigurableApplicationContext applicationContext;

    public RowTransformerProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(com.espark.adarsh.excel.annotation.RowTransformer.class);
        for (final Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            final Object object = entry.getValue();
            final String beanName = object.getClass().getAnnotation(com.espark.adarsh.excel.annotation.RowTransformer.class).name();
            rowTransformerMap.put(beanName, object);
            log.info("RowTransformerProcessor :: init :: Bean Name : {} , Bean Object : {}", beanName, object);
        }
    }

    public static final Function<String, RowTransformers> getRowTransformer = (name) -> (RowTransformers) rowTransformerMap.get(name);

    public static final Supplier<Map<String, Object>> getRowTransformerMap = () -> rowTransformerMap;
}
