package com.example.danilo.appdebts.classes;

public class Debts {
    private long mId;
    private Categoria mCategoria;
    private float mValor;
    private String mDescricao;
    private String mDataVencimento;
    private String mDataPagamento;

    public Debts(){

    }
    public long getId() {
        return mId;
    }

    public void setId(long id) {

        mId = id;
    }

    public Categoria getCategoria() {

        return mCategoria;
    }

    public void setCategoria(Categoria categoria) {

        mCategoria = categoria;
    }

    public float getValor() {

        return mValor;
    }

    public void setValor(float valor) {

        mValor = valor;
    }

    public String getDescricao() {

        return mDescricao;
    }

    public void setDescricao(String descricao) {

        mDescricao = descricao;
    }

    public String getDataVencimento() {

        return mDataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {

        mDataVencimento = dataVencimento;
    }

    public String getDataPagamento() {

        return mDataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {

        mDataPagamento = dataPagamento;
    }
    public Debts(Categoria categoria, String descricao, float val, String dataVenc, String dataPg){
        this.mCategoria = categoria;
        this.mDescricao = descricao;
        this.mValor = val;
        this.mDataVencimento = dataVenc;
        this.mDataPagamento = dataPg;
    }
}
