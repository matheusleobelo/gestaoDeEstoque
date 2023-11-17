package com.revisao.aula.api.revisaoapirest.Modelagem;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "nome", columnDefinition = "varchar(100)", nullable = false)
    public String nome;

    @Column(name = "descricao", columnDefinition = "varchar(280)", nullable = false)
    public String descricao;

    @Column(name = "quantidadeMinima", nullable = false)
    public int quantidadeMinima;

    @Column(name = "quantidadeMaxima", nullable = false)
    public int quantidadeMaxima;

    @Column(name = "criadoEm", nullable = false)
    public LocalDateTime criadoEm;


//    public Produto(String nome, String descricao, Integer quantidadeMinima, Integer quantidadeMaxima){
//        this.nome = nome;
//        this.descricao = descricao;
//        this.quantidadeMinima = quantidadeMinima;
//        this.quantidadeMaxima = quantidadeMaxima;
//        this.criadoEm  =LocalDateTime.now();
//    }
//
//    public Produto() {
//    }
//
//    public Produto(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
}
