package br.com.api.zupapi.model;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAllModels {
	
	
	private String nome;
	private String codigo;
	private String ano;
	@JsonProperty("Valor")
	private String valor;
	private List<ModelGeneric> modelos;

	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Collection<ModelGeneric> getModelos() {
		return modelos;
	}
	public void setModelos(List<ModelGeneric> modelos) {
		this.modelos = modelos;
	}
	

}
