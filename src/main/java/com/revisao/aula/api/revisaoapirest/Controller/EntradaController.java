package com.revisao.aula.api.revisaoapirest.Controller;

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

import com.revisao.aula.api.revisaoapirest.Modelagem.Entrada;
import com.revisao.aula.api.revisaoapirest.Modelagem.Saida;
import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioEntrada;
import com.revisao.aula.api.revisaoapirest.Repositorio.RepositorioSaida;
import com.revisao.aula.api.revisaoapirest.dto.EntradaResponseDTO;
import com.revisao.aula.api.revisaoapirest.dto.ListaEntradaResponseDTO;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;

@RestController
@RequestMapping("Entrada")
@Api(value = "API REST Entrada")
public class EntradaController {
	@Autowired
	private RepositorioEntrada RepositorioEntrada;


	@GetMapping("/listar")
	public ResponseEntity<ListaEntradaResponseDTO> listar() {
		ListaEntradaResponseDTO response = new ListaEntradaResponseDTO();
		response.setStatusCode("200");
		List<Entrada> lista = (List<Entrada>) RepositorioEntrada.findAll();
		response.quantidadeTotal = lista.size();
		if (lista.size() == 0) {
			response.getMensagem().add("Consulta sem Resultados");
		} else {
			response.entradas = lista;
		}
		return new ResponseEntity<>(

				response, HttpStatus.OK);
	}

	

	@PostMapping("/cadastrar")
	public ResponseEntity<EntradaResponseDTO> cadastrar(@Valid @RequestBody Entrada dados,
			BindingResult bindingResult) {
		EntradaResponseDTO response = new EntradaResponseDTO();
		response.setStatusCode("200");
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			try {
				dados = RepositorioEntrada.save(dados);
				response.entradas = dados;
				response.getMensagem().add("Entrada cadastrado com sucesso");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (DataIntegrityViolationException e) {
				response.entradas = dados;
				response.getMensagem().add(e.getLocalizedMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}

	@GetMapping("/getEntrada/{id}")
	public ResponseEntity<EntradaResponseDTO> getEntrada(@PathVariable Long id) {
		EntradaResponseDTO response = new EntradaResponseDTO();
		response.setStatusCode("200");
		Optional<Entrada> buscarEntrada = RepositorioEntrada.findById(id);
		if (buscarEntrada.isPresent() == false) {
			response.setStatusCode("199");
			response.getMensagem().add("Entrada não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.getMensagem().add("Entrada encontrado");
			response.entradas = buscarEntrada.get();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<EntradaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Entrada dados,
			BindingResult bindingResult) {
		EntradaResponseDTO response = new EntradaResponseDTO();
		response.setStatusCode("200");
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			Optional<Entrada> buscarEntrada = RepositorioEntrada.findById(id);
			if (buscarEntrada.isPresent() == false) {
				response.setStatusCode("199");
				response.getMensagem().add("Entrada não encontrado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				response.getMensagem().add("Entrada atualizado");
				dados.id = buscarEntrada.get().id;
				response.entradas = RepositorioEntrada.save(dados);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<EntradaResponseDTO> delete(@PathVariable Long id) {
		EntradaResponseDTO response = new EntradaResponseDTO();
		response.setStatusCode("200");
		Optional<Entrada> buscarEntrada = RepositorioEntrada.findById(id);
		if (buscarEntrada.isPresent() == false) {
			response.setStatusCode("199");
			response.getMensagem().add("Entrada não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.getMensagem().add("Entrada removido");
			RepositorioEntrada.deleteById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
			//
		}
	}

}
