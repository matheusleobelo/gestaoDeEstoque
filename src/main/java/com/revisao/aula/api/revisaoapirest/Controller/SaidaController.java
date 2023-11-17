package com.revisao.aula.api.revisaoapirest.Controller;

import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioEntrada;
import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioSaida;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revisao.aula.api.revisaoapirest.dto.ListaSaidaResponseDTO;
import com.revisao.aula.api.revisaoapirest.dto.SaidaResponseDTO;
import com.revisao.aula.api.revisaoapirest.Modelagem.Entrada;
import com.revisao.aula.api.revisaoapirest.Modelagem.Saida;
import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioSaida;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("Saida")
@Api(value = "API REST Saida")
public class SaidaController {
    @Autowired
    private RepositorioSaida RepositorioSaida;
    
    @Autowired
	private RepositorioEntrada RepositorioEntrada;

    @GetMapping("/listar")
    public ResponseEntity<ListaSaidaResponseDTO> listar() {
        ListaSaidaResponseDTO response = new ListaSaidaResponseDTO();
        response.setStatusCode("200");
        List<Saida> lista = (List<Saida>) RepositorioSaida.findAll();
        response.quantidadeTotal = lista.size();
        if (lista.size() == 0) {
            response.getMensagem().add("Consulta sem Resultados");
        } else {
            response.saidas = lista;
        }
        return new ResponseEntity<>(

                response, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<SaidaResponseDTO> cadastrar(@Valid @RequestBody Saida dados,
            BindingResult bindingResult) {
        SaidaResponseDTO response = new SaidaResponseDTO();
        response.setStatusCode("200");
        if (bindingResult.hasErrors()) {
            response.setStatusCode("199");
            for (ObjectError obj : bindingResult.getAllErrors()) {
                response.getMensagem().add(obj.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
            	Integer qtdEstoque = this.getEstoqueAtualProduto(dados.produto.id);
            	if(dados.quantidade > qtdEstoque) {
            		response.getMensagem().add("A quantidade informada é maior do que a quantidade em estoque: " + qtdEstoque);
            		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            	}
                dados = RepositorioSaida.save(dados);
                response.Saidas = dados;
                response.getMensagem().add("Saida cadastrado com sucesso");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (DataIntegrityViolationException e) {
                response.Saidas = dados;
                response.getMensagem().add(e.getLocalizedMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/getSaida/{id}")
    public ResponseEntity<SaidaResponseDTO> getSaida(@PathVariable Long id) {
        SaidaResponseDTO response = new SaidaResponseDTO();
        response.setStatusCode("200");
        Optional<Saida> buscarSaida = RepositorioSaida.findById(id);
        if (buscarSaida.isPresent() == false) {
            response.setStatusCode("199");
            response.getMensagem().add("Saida não encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.getMensagem().add("Saida encontrado");
            response.Saidas = buscarSaida.get();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<SaidaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Saida dados,
            BindingResult bindingResult) {
        SaidaResponseDTO response = new SaidaResponseDTO();
        response.setStatusCode("200");
        if (bindingResult.hasErrors()) {
            response.setStatusCode("199");
            for (ObjectError obj : bindingResult.getAllErrors()) {
                response.getMensagem().add(obj.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Optional<Saida> buscarSaida = RepositorioSaida.findById(id);
            if (buscarSaida.isPresent() == false) {
                response.setStatusCode("199");
                response.getMensagem().add("Saida não encontrado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.getMensagem().add("Saida atualizado");
                dados.id = buscarSaida.get().id;
                response.Saidas = RepositorioSaida.save(dados);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SaidaResponseDTO> delete(@PathVariable Long id) {
        SaidaResponseDTO response = new SaidaResponseDTO();
        response.setStatusCode("200");
        Optional<Saida> buscarSaida = RepositorioSaida.findById(id);
        if (buscarSaida.isPresent() == false) {
            response.setStatusCode("199");
            response.getMensagem().add("Saida não encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.getMensagem().add("Saida removido");
            RepositorioSaida.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    
    private Integer getEstoqueAtualProduto(Long id) {
		Integer total = 0;
		List<Entrada> listaEntrada = RepositorioEntrada.findByProdutoId(id);
        Integer qtdTotalEntrada = 0;
        for (Entrada entrada : listaEntrada) {
        	qtdTotalEntrada = qtdTotalEntrada + entrada.quantidade;
		}
        
        List<Saida> listaSaida = RepositorioSaida.findByProdutoId(id);
        Integer qtdTotalSaida = 0;
        for (Saida saida : listaSaida) {
        	qtdTotalSaida = qtdTotalSaida + saida.quantidade;
		}
		total = qtdTotalEntrada - qtdTotalSaida;
		return total;
	}
}
