package com.espark.adarsh.excel.config;

import com.espark.adarsh.excel.exception.MappingNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Setter
@Getter
@Component
@ConfigurationProperties("espark")
public class MetaDataConfig {


    private Map<String, RowMetaData> excel = new HashMap<>();

    private Map<String, String> mappings = new HashMap<>();

    @Setter
    @Getter
    @ToString
    public static class RowMetaData {
        private String name;
        private String description;
        private String className;
        private List<Column> columns;
        private String rowTransformer;
        private List<String> validators;
        private List<Integer> sheet;
    }


    @Setter
    @Getter
    @ToString
    public static class Column {
        private String columnName;
        private Integer columnIndex;
        private List<String> transformers;
        private ColumnMeta columnMeta;
        private String setterMethod;
    }

    private static final Map<String, Class> TYPE_CLASS_MAP = Map.of(
            "String", String.class,
            "string", String.class,
            "Integer", Integer.class,
            "integer", Integer.class,
            "int", Integer.class,
            "Long", Long.class,
            "Double", Double.class,
            "Boolean", Boolean.class,
            "Character", Character.class,
            "Short", Short.class
    );

    @Setter
    @Getter
    @ToString
    public static class ColumnMeta {
        private String type;
        private String format;
        private String defaultValue;
        private Boolean required;
        private String description;
        public Supplier<Class> typeClass = () -> {
            return TYPE_CLASS_MAP.get(type);
        };
    }

    public final Function<String, RowMetaData> rowMetaData = (metaKey) -> {
        if (mappings.containsKey(metaKey)) {
            return this.excel.get(mappings.get(metaKey));
        }
        throw new MappingNotFoundException("Mapping not found for metaKey: " + metaKey, null);
    };
}
