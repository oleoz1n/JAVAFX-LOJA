package com.example.model;

public class Produto {
    private Integer id;
    private String nome;
    private int valor;
    private Marca marca;

    public Produto(String nome, int valor, Marca marca) {
        this.nome = nome;
        this.valor = valor;
        this.marca = marca;
    }

    public Produto(Integer id, String nome, int valor, Marca marca) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Produto valor(int valor) {
        this.valor = valor;
        return this;
    }

    public Produto marca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
