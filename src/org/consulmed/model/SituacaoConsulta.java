package org.consulmed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="status")
public class SituacaoConsulta {
	
	@Id
	private int id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	public SituacaoConsulta() {
		// TODO Auto-generated constructor stub
	}
	
	public SituacaoConsulta(int id, String nome)
	{
		this.setId(id);
		this.setNome(nome);
	}
	
	public static final SituacaoConsulta CANCELADA = new SituacaoConsulta(1, "Cancelada");
	public static final SituacaoConsulta MARCADA = new SituacaoConsulta(2, "Marcada");
	public static final SituacaoConsulta REALIZADA = new SituacaoConsulta(3, "Realizada");

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
