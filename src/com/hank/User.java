package com.hank;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String psw;

    public User(String id, String psw) {
        this.id = id;
        this.psw = psw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
