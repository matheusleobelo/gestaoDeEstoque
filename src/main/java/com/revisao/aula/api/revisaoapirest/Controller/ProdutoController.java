package com.revisao.aula.api.revisaoapirest.Controller;

import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioProduto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

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

import com.revisao.aula.api.revisaoapirest.dto.ListaProdutoResponseDTO;
import com.revisao.aula.api.revisaoapirest.dto.ProdutoResponseDTO;
import com.revisao.aula.api.revisaoapirest.Modelagem.Produto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("Produto")
@Api(value = "API REST Produto")
public class ProdutoController {
    @Autowired
    private RepositorioProduto RepositorioProduto;

    @GetMapping("/listar")
    public ResponseEntity<ListaProdutoResponseDTO> listar() {
        ListaProdutoResponseDTO response = new ListaProdutoResponseDTO();
        response.setStatusCode("200");
        List<Produto> lista = (List<Produto>) RepositorioProduto.findAll();
        response.quantidadeTotal = lista.size();
        if (lista.size() == 0) {
            response.getMensagem().add("Consulta sem Resultados");
        } else {
            response.produtos = lista;
        }
        return new ResponseEntity<>(

                response, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@Valid @RequestBody Produto dados,
                                                          BindingResult bindingResult) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setStatusCode("200");
        if (bindingResult.hasErrors()) {
            response.setStatusCode("199");
            for (ObjectError obj : bindingResult.getAllErrors()) {
                response.getMensagem().add(obj.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                dados = RepositorioProduto.save(dados);
                response.produto = dados;
                response.getMensagem().add("Produto cadastrado com sucesso");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (DataIntegrityViolationException e) {
                response.produto = dados;
                response.getMensagem().add(e.getLocalizedMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/getProduto/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProduto(@PathVariable Long id) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setStatusCode("200");
        Optional<Produto> buscarProduto = RepositorioProduto.findById(id);
        if (buscarProduto.isPresent() == false) {
            response.setStatusCode("199");
            response.getMensagem().add("Produto não encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.getMensagem().add("Produto encontrado");
            response.produto = buscarProduto.get();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados,
                                                          BindingResult bindingResult) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setStatusCode("200");
        if (bindingResult.hasErrors()) {
            response.setStatusCode("199");
            for (ObjectError obj : bindingResult.getAllErrors()) {
                response.getMensagem().add(obj.getDefaultMessage());
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Optional<Produto> buscarProduto = RepositorioProduto.findById(id);
            if (buscarProduto.isPresent() == false) {
                response.setStatusCode("199");
                response.getMensagem().add("Produto não encontrado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.getMensagem().add("Produto atualizado");
                dados.id = buscarProduto.get().id;
                response.produto = RepositorioProduto.save(dados);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProdutoResponseDTO> delete(@PathVariable Long id) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setStatusCode("200");
        Optional<Produto> buscarProduto = RepositorioProduto.findById(id);
        if (buscarProduto.isPresent() == false) {
            response.setStatusCode("199");
            response.getMensagem().add("Produto não encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.getMensagem().add("Produto removido");
            RepositorioProduto.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
