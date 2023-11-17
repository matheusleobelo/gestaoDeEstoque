package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;
import java.util.List;
import com.revisao.aula.api.revisaoapirest.Modelagem.Produto;

public class ListaProdutoResponseDTO extends BasicDTO{
    public int quantidadeTotal;
    public List<Produto> produtos;
    public ListaProdutoResponseDTO() {
        super.setMensagem(new ArrayList<>());
    }
}