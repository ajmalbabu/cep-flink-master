package cep.lib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Configs implements Serializable {

    public static final long serialVersionUID = 1L;
    private List<Config> configList;

    public Configs(List<Config> configList) {
        this.configList = configList;
    }

    public List<Config> getConfigList() {
        return new ArrayList<>(configList);
    }


    public Optional<Config> findConfig(String withName) {
        return configList.stream().filter(e -> e.getName().equalsIgnoreCase(withName)).findFirst();
    }

    @Override
    public String toString() {
        return "Configs{" +
                "configList=" + configList +
                '}';
    }
}
