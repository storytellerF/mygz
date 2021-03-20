package com.storyteller_f.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Site {
    ArrayList<Redirect> redirects;
    ArrayList<Translate> translates;
    private String addr;
    private int port;
    private String dir;

    public ArrayList<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(ArrayList<Redirect> redirects) {
        this.redirects = redirects;
    }

    public ArrayList<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(ArrayList<Translate> translates) {
        this.translates = translates;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }


}
