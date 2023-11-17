package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;

import com.revisao.aula.api.revisaoapirest.Modelagem.Produto;

public class ProdutoResponseDTO extends BasicDTO{
	public Produto produto;
	public ProdutoResponseDTO() {
		super.setMensagem(new ArrayList<>());
	}
}
