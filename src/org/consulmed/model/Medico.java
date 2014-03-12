package org.consulmed.model;

import javax.persistence.*;

@Entity
@Table(name="medico")
public class Medico {
	  @Id
	  @GeneratedValue
	  private int id;
	  
	  @Column(name = "nome", nullable = false)
	  private String nome;

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
