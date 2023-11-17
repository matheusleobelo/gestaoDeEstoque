package com.revisao.aula.api.revisaoapirest.Repositorio;

import com.revisao.aula.api.revisaoapirest.Modelagem.Entrada;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEntrada extends JpaRepository<Entrada, Long> {
	List<Entrada> findByProdutoId(Long id);
}
