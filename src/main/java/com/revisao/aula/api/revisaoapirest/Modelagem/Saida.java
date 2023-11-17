package com.revisao.aula.api.revisaoapirest.Modelagem;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Saida")
public class Saida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    public Produto produto;
    @Column(nullable = false)
    public Integer quantidade;
    @Column(nullable = false)
    public LocalDateTime dataSaida;
}
