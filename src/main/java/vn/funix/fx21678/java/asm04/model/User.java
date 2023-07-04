package vn.funix.fx21678.java.asm04.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String customerId;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
