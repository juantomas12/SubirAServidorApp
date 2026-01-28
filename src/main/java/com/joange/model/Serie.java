package com.joange.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="Serie")
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long idSerie;

	@Column
	String titulo;

	@Column
	Integer anyoEstreno;

	@Column
	Integer temporadas;

	@Column
	String plataforma;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idDirector")
	Director director=null;

	public Long getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Long idSerie) {
		this.idSerie = idSerie;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAnyoEstreno() {
		return anyoEstreno;
	}

	public void setAnyoEstreno(Integer anyoEstreno) {
		this.anyoEstreno = anyoEstreno;
	}

	public Integer getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(Integer temporadas) {
		this.temporadas = temporadas;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}
}
