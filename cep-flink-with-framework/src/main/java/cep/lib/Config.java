package cep.lib;

import java.io.Serializable;

public class Config<T> implements Serializable {

    public static final long serialVersionUID = 1L;
    private String name;

    private T value;

    public Config(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  T getValue() {
        return (T) value;
    }

    public void setValue(T value) {
        this.value =  value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
