package com.revisao.aula.api.revisaoapirest.Modelagem;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Entrada")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Produto produto;

    @Column(nullable = false)
    public LocalDateTime dataEntrada;

    @Column(nullable = false)
    public Double precoUnitario;

    @Column(nullable = false)
    public Integer quantidade;

}
