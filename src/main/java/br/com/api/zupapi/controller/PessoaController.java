package br.com.api.zupapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.zupapi.model.Pessoa;
import br.com.api.zupapi.service.PessoaService;

@Controller
@RestController
@RequestMapping("zup-api")
public class PessoaController {
	
	@Autowired
	PessoaService pessoaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> put(@RequestBody @Valid Pessoa pessoa){
		Pessoa p = pessoaService.put(pessoa);
		return new ResponseEntity<>(p, HttpStatus.CREATED);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Pessoa> get(){
		return pessoaService.get();
	}

}
