package br.com.api.zupapi.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import br.com.api.zupapi.model.CarroResponse;
import br.com.api.zupapi.model.ModelGeneric;
import br.com.api.zupapi.model.Pessoa;
import br.com.api.zupapi.model.ResponseAllModels;
import br.com.api.zupapi.repository.CarroRepository;
import br.com.api.zupapi.repository.PessoaRepository;

@Service
public class CarroService {


	@Autowired
	private CarroRepository carroRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Carro post(CarroRequest carroRequest) {
		Carro carroResponse = this.callApiFipe(carroRequest);
		carroResponse.setDiaRodizio(this.diaRodizio(carroResponse));
		return carroRepository.save(carroResponse);
	}

	public List<CarroResponse> get(CarroRequest carro){
		List<CarroResponse> carroResponse = new ArrayList<CarroResponse>();
		List<Carro> allCars = carroRepository.findAll();


		for(int i = 0; i < allCars.size() ; ++i){
			CarroResponse car = this.rodizioAtivo(allCars.get(i));
			carroResponse.add(car);
		}
		
		return carroResponse;
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
		String pathMarca = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
		ResponseEntity<ArrayList> respMarca = restTemplate.getForEntity(pathMarca, ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		List<ResponseAllModels> listMarcas = mapper.convertValue(respMarca.getBody(), new TypeReference<List<ResponseAllModels>>() {});
		List<ResponseAllModels> marcaFilter = listMarcas.stream().filter(m -> m.getNome().contains(carro.getMarca())).collect(Collectors.toList());
		String codigoMarca = marcaFilter.get(0).getCodigo();

		String pathModelo = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+ codigoMarca +"/modelos";
		ResponseEntity<ResponseAllModels> respModelo = restTemplate.getForEntity(pathModelo, ResponseAllModels.class);
		ResponseAllModels body = respModelo.getBody();
		List<ModelGeneric> modeloFilter = body.getModelos().stream().filter(m -> m.getNome().contains(carro.getModelo())).collect(Collectors.toList());
		String codigoModelo = modeloFilter.get(0).getCodigo();


		String pathAno = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+codigoMarca +"/modelos/"+codigoModelo+"/anos";
		ResponseEntity<ArrayList> respAno = restTemplate.getForEntity(pathAno, ArrayList.class);
		List<ResponseAllModels> listAnos = mapper.convertValue(respAno.getBody(), new TypeReference<List<ResponseAllModels>>() {});
		List<ResponseAllModels> anoFilter = listAnos.stream().filter(m -> m.getNome().contains(carro.getAno())).collect(Collectors.toList());
		String codigoAno = anoFilter.get(0).getCodigo();

		String pathResponse = "https://parallelum.com.br/fipe/api/v1/carros/marcas/"+codigoMarca+"/modelos/"+codigoModelo+"/anos/"+codigoAno+"";
		ResponseEntity<Carro> resp4 = restTemplate.getForEntity(pathResponse, Carro.class);

		Carro car = resp4.getBody();

		return car;
	}



	private String diaRodizio(Carro carro) {

		if (carro.getAnoModelo().substring(3).equals("0") || carro.getAnoModelo().substring(3).equals("1")) {
			return "segunda-feira";
		}
		if (carro.getAnoModelo().substring(3).equals("2")  || carro.getAnoModelo().substring(3).equals("3") ) {
			return "terça-feira";
		}
		if (carro.getAnoModelo().substring(3).equals("4") || carro.getAnoModelo().substring(3).equals("5")) {
			return "quarta-feira";
		}
		if (carro.getAnoModelo().substring(3).equals("6")  || carro.getAnoModelo().substring(3).equals("7") ) {
			return "quinta-feira";
		}
		else {
			return"sexta-feira";
		}
	}

	private CarroResponse rodizioAtivo(Carro carro) {
		Date data  = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("EEEE",new Locale("pt","BR"));
		String dataFormatada = formatter.format(data);

		CarroResponse car = new CarroResponse();
		car.setAnoModelo(carro.getAnoModelo());
		car.setCodigoFipe(carro.getCodigoFipe());
		car.setCombustivel(carro.getCombustivel());
		car.setDiaRodizio(carro.getDiaRodizio());
		car.setMarca(carro.getMarca());
		car.setMesReferencia(carro.getMesReferencia());
		car.setModelo(carro.getModelo());
		car.setSiglaCombustivel(carro.getSiglaCombustivel());
		car.setTipoVeiculo(carro.getTipoVeiculo());
		car.setValor(carro.getValor());
		
		if (carro.getDiaRodizio().equals(dataFormatada)) {
			car.setRodizioAtivo(true);
		}		else {
			car.setRodizioAtivo(false);
		}
		return car ;
	}

}





