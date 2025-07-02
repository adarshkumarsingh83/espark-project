package com.espark.adarsh.excel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBean<K,V> extends AbstractBean {

    private List<DataRow<K,V>> entities = new LinkedList<>() ;

    public static class DataRow <K,V> extends HashMap<K,V>{

        public DataRow() {
           super();
       }

       public V put(K key, V value) {
           return super.put(key, value);
       }
    }

    public void addEntry(DataRow<K,V> dataRow){
        this.entities.add(dataRow);
    }

    public DataRow<K,V> createDataRow(){
        return new DataRow<K,V>();
    }

    @Override
    public String toString() {
        return "" + entities ;
    }
}
