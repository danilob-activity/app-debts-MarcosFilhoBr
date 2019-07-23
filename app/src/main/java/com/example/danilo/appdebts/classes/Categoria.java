package com.example.danilo.appdebts.classes;

public class Categoria {
    private long mId;
    private String mType;

    public Categoria(String s) {

    }
    public String getTipo() {
        return mType;
    }

    public void setTipo(String tipo) {

        mType = tipo;
    }


    public long getId() {

        return mId;
    }

    public void setId(long id) {

        mId = id;
    }


}
