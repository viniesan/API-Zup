package br.com.api.zupapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.zupapi.exceptions.CarroNotFoundException;
import br.com.api.zupapi.model.Carro;
import br.com.api.zupapi.repository.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;

	public Carro post(Carro carro) {
		return carroRepository.save(carro);
	}

	public List<Carro> get(){
		return carroRepository.findAll();
	}

	public Optional<Carro> put(Long id, Carro carro){
		Optional<Carro> car = carroRepository.findById(id);
		carro.setId(id);
		carroRepository.save(carro);
		return car;
	}

	public void delete(Long id){
		Optional<Carro> car = carroRepository.findById(id);
		if(car.isPresent())  {
			carroRepository.delete(car.get());
		}else{
			throw new CarroNotFoundException();
		}
	}

}

