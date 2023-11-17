package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;
import java.util.List;

import com.revisao.aula.api.revisaoapirest.Modelagem.Saida;

public class ListaSaidaResponseDTO extends BasicDTO {
    public int quantidadeTotal;
    public List<Saida> saidas;

    public ListaSaidaResponseDTO() {
        super.setMensagem(new ArrayList<>());
    }
}