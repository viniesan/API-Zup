package br.com.api.zupapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.zupapi.exceptions.CarroNotFoundException;
import br.com.api.zupapi.model.Carro;
import br.com.api.zupapi.model.CarroRequest;
import br.com.api.zupapi.model.ModelGeneric;
import br.com.api.zupapi.model.ResponseAllModels;
import br.com.api.zupapi.repository.CarroRepository;

@Service
public class CarroService {


	@Autowired
	private CarroRepository carroRepository;

	public Carro post(CarroRequest carro) {
        return carroRepository.save(this.callApiFipe(carro));
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

	private Carro callApiFipe(CarroRequest carro) {
		
		RestTemplate restTemplate = new RestTemplate();
		String path1 = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
		ResponseEntity<ArrayList> resp1 = restTemplate.getForEntity(path1, ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		List<ResponseAllModels> listMarcas = mapper.convertValue(resp1.getBody(), new TypeReference<List<ResponseAllModels>>() {});
		List<ResponseAllModels> marcaFilter = listMarcas.stream().filter(m -> m.getNome().contains(carro.getMarca())).collect(Collectors.toList());
		String codigoMarca = marcaFilter.get(0).getCodigo();

		String path2 = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+ codigoMarca +"/modelos";
		ResponseEntity<ResponseAllModels> resp2 = restTemplate.getForEntity(path2, ResponseAllModels.class);
		ResponseAllModels body = resp2.getBody();
		List<ModelGeneric> modeloFilter = body.getModelos().stream().filter(m -> m.getNome().contains(carro.getModelo())).collect(Collectors.toList());
		String codigoModelo = modeloFilter.get(0).getCodigo();


		String path3 = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+codigoMarca +"/modelos/"+codigoModelo+"/anos";
		ResponseEntity<ArrayList> resp3 = restTemplate.getForEntity(path3, ArrayList.class);
		List<ResponseAllModels> listAnos = mapper.convertValue(resp3.getBody(), new TypeReference<List<ResponseAllModels>>() {});
		String codigoAno = listAnos.get(0).getCodigo();

		String path4 = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+codigoMarca+"/modelos/"+codigoModelo+"/anos/"+codigoAno+"";
		ResponseEntity<Carro> resp4 = restTemplate.getForEntity(path4, Carro.class);
		
		Carro car = resp4.getBody();
		return car;
	}
}



