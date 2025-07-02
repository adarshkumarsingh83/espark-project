package com.espark.adarsh.excel.processor;

import com.espark.adarsh.excel.validator.RowValidators;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class RowValidatorProcessor {

    private static final Map<String, Object> rowValidators = new HashMap<>();

    private final ConfigurableApplicationContext applicationContext;

    public RowValidatorProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(com.espark.adarsh.excel.annotation.RowValidator.class);
        for (final Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            final Object object = entry.getValue();
            final String beanName = object.getClass().getAnnotation(com.espark.adarsh.excel.annotation.RowValidator.class).name();
            rowValidators.put(beanName, object);
            log.info("RowValidatorProcessor :: init :: Bean Name : {} , Bean Object : {}", beanName, object);
        }
    }

    public static final java.util.function.Function<String, RowValidators> getRowValidator = (name) -> (RowValidators) rowValidators.get(name);

    public static final java.util.function.Supplier<Map<String, Object>> getRowValidators = () -> rowValidators;

}
