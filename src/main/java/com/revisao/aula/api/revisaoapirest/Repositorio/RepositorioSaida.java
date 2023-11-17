package com.revisao.aula.api.revisaoapirest.Repositorio;

import com.revisao.aula.api.revisaoapirest.Modelagem.Saida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioSaida extends JpaRepository<Saida, Long> {
	List<Saida> findByProdutoId(Long id);
}
