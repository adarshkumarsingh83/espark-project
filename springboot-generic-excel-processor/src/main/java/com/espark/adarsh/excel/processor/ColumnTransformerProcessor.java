package com.espark.adarsh.excel.processor;

import com.espark.adarsh.excel.transformer.column.ColumnTransformers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class ColumnTransformerProcessor {

    private static final Map<String, Object> columnTransformerMap = new HashMap<>();

    private final ConfigurableApplicationContext applicationContext;

    public ColumnTransformerProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeanFactory().getBeansWithAnnotation(com.espark.adarsh.excel.annotation.ColumnTransformer.class);
        for (final Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            final Object object = entry.getValue();
            final String beanName = object.getClass().getAnnotation(com.espark.adarsh.excel.annotation.ColumnTransformer.class).name();
            columnTransformerMap.put(beanName, object);
            log.info("ColumnTransformerProcessor :: init :: Bean Name : {} , Bean Object : {}", beanName, object);
        }
    }

    public static final java.util.function.Function<String, ColumnTransformers> getColumnTransformer = (name) ->{
        return  (ColumnTransformers) columnTransformerMap.get(name);
    };

    public static final java.util.function.Supplier<Map<String, Object>> getColumnTransformerMap = () -> columnTransformerMap;

}
