package br.com.api.zupapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.api.zupapi.exceptions.PessoaNotFoundException;
import br.com.api.zupapi.model.Pessoa;
import br.com.api.zupapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa post(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public List<Pessoa> get(){
		return pessoaRepository.findAll();
	}

	public Optional<Pessoa> put(Long id, Pessoa pessoa){
		Optional<Pessoa> person = pessoaRepository.findById(id);
		pessoa.setId(id);
		pessoaRepository.save(pessoa);
		return person;
	}



	public void delete(Long id){
		Optional<Pessoa> person = pessoaRepository.findById(id);
		if(person.isPresent())  {
			pessoaRepository.delete(person.get());
		}else{
			throw new PessoaNotFoundException();
		}
	}

}


