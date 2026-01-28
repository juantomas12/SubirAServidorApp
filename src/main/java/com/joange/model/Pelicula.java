package com.joange.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="Pelicula")
public class Pelicula {

	//utilizado para serializar y deserializar objetos
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)	
		Long idPelicula;
		
		@Column
		String titulo;
		
		@Column
		Integer anyo;
		
		@ManyToOne(fetch = FetchType.EAGER)		// al obtener Pelicula obtengo director
		@Cascade({CascadeType.ALL})
		@JoinColumn(name = "idDirector")
		//@JsonIgnore
		Director director=null;

		public Long getIdPelicula() {
			return idPelicula;
		}

		public void setIdPelicula(Long idPelicula) {
			this.idPelicula = idPelicula;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Integer getAnyo() {
			return anyo;
		}

		public void setAnyo(Integer anyo) {
			this.anyo = anyo;
		}

		public Director getDirector() {
			return director;
		}

		public void setDirector(Director director) {
			this.director = director;
		}
		

}
