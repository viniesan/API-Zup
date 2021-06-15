package br.com.api.zupapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.zupapi.model.Pessoa;
import br.com.api.zupapi.service.PessoaService;

@Controller
@RestController
@RequestMapping("pessoa")
public class PessoaController {

	@Autowired
	PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<Pessoa> post(@RequestBody @Valid Pessoa pessoa){
		Pessoa person = pessoaService.post(pessoa);
		return new ResponseEntity<>(person, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Pessoa>> get(){
		return ResponseEntity.ok(pessoaService.get());
	}
}
