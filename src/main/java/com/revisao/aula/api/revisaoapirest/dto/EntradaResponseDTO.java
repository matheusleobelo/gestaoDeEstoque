package com.revisao.aula.api.revisaoapirest.dto;

import java.util.ArrayList;

import com.revisao.aula.api.revisaoapirest.Modelagem.Entrada;

public class EntradaResponseDTO extends BasicDTO{
	public Entrada entradas;
	public EntradaResponseDTO() {
		super.setMensagem(new ArrayList<>());
	}
}

