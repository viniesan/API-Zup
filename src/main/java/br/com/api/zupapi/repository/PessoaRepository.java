package br.com.api.zupapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.zupapi.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	Optional<Pessoa> findByEmail(String nome);
	Pessoa findByCpf(String cpf);
}
