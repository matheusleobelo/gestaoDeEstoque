package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;

import com.revisao.aula.api.revisaoapirest.Modelagem.Saida;

public class SaidaResponseDTO extends BasicDTO{
	public Saida Saidas;
	public SaidaResponseDTO() {
		super.setMensagem(new ArrayList<>());
	}
}

