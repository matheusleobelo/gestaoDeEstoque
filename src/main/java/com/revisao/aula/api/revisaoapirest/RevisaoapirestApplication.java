package com.revisao.aula.api.revisaoapirest;

import com.revisao.aula.api.revisaoapirest.Modelagem.Produto;
import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RevisaoapirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevisaoapirestApplication.class, args);
	}

//	@Bean
	public CommandLineRunner inserindoProduto(RepositorioProduto repository) {
		return (args) -> {
			try {


				Produto a = new Produto();
				a.nome = "Produto";
				a.descricao = "Produto descrição";
				a.quantidadeMinima = 2;
				a.quantidadeMaxima = 2;
				a = repository.save(a);
				System.out.println("Aluno: " + a.id + " Nome: " + a.nome + " salvo com sucesso");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		};
	}

}
