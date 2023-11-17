package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;
import java.util.List;

import com.revisao.aula.api.revisaoapirest.Modelagem.Entrada;

public class ListaEntradaResponseDTO extends BasicDTO{
    public int quantidadeTotal;
    public List<Entrada> entradas;
    public ListaEntradaResponseDTO() {
        super.setMensagem(new ArrayList<>());
    }
}