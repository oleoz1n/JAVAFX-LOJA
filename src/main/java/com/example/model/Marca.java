package com.example.model;

public class Marca {
    private Integer id;
    private String nome;;

    public Marca(String nome) {
        this.nome = nome;
    }

    public Marca(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Marca nome(String nome) {
        this.nome = nome;
        return this;
    }

            
    @Override
    public String toString() {
        return nome;
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

}
