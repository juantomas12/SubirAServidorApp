package com.joange.service;

import java.util.List;
import java.util.Optional;

import com.joange.model.Serie;

public interface SerieService {

	List<Serie> findAllSerie();

	Optional<Serie> findSerieById(Long id);

	List<Serie> findSerieByPlataforma(String plataforma);

	List<Serie> findSerieByDirectorId(Long idDirector);

	Serie saveSerie(Serie nuevaSerie);

	boolean deleteSerie(Long id);

	Serie updateSerie(Serie nuevaSerie);
}
