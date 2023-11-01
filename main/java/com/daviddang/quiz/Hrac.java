package com.daviddang.quiz;

public class Hrac {
    private int id;
    private String jmeno;
    private int body;

    public Hrac(int id, String jmeno, int body) {
        this.id = id;
        this.jmeno = jmeno;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }
}

