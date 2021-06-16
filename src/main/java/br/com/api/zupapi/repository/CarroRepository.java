package br.com.api.zupapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.zupapi.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
	

}
