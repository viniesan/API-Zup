package br.com.api.zupapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.api.zupapi.model.Carro;
import br.com.api.zupapi.service.CarroService;

@Controller
@RestController
@RequestMapping("carro")
public class CarroController {

	@Autowired
	CarroService carroService;

	@PostMapping
	public ResponseEntity<Carro> post(@RequestBody @Valid Carro carro){
		Carro car = carroService.post(carro);
		return new ResponseEntity<>(car, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Carro>> get(){
		return ResponseEntity.ok(carroService.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@RequestBody Carro carro, @PathVariable Long id){
		Optional<Carro> car = carroService.put(id, carro);
		if(!car.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(car);    	
	}

	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		carroService.delete(id);
	}

}
