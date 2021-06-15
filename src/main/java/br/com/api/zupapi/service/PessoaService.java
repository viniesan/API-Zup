package br.com.api.zupapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
