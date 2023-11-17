package com.revisao.aula.api.revisaoapirest.Repositorio;

import com.revisao.aula.api.revisaoapirest.Modelagem.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProduto extends JpaRepository<Produto, Long> {
}
