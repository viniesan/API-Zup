package br.com.api.zupapi.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Carro não encontrado")
public class CarroNotFoundException extends RuntimeException{

}
