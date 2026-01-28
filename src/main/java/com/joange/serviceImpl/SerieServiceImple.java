package com.joange.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joange.model.Serie;
import com.joange.reporitory.SerieRepo;
import com.joange.service.SerieService;

@Service
public class SerieServiceImple implements SerieService{

	@Autowired SerieRepo serieRepo;

	@Override
	public List<Serie> findAllSerie() {
		return serieRepo.findAll();
	}

	@Override
	public Optional<Serie> findSerieById(Long id) {
		return serieRepo.findById(id);
	}

	@Override
	public List<Serie> findSerieByPlataforma(String plataforma) {
		return serieRepo.findByPlataforma(plataforma);
	}

	@Override
	public List<Serie> findSerieByDirectorId(Long idDirector) {
		return serieRepo.findByDirectorId(idDirector);
	}

	@Override
	public Serie saveSerie(Serie nuevaSerie) {
		return serieRepo.save(nuevaSerie);
	}

	@Override
	public boolean deleteSerie(Long id) {
		Optional<Serie> s=serieRepo.findById(id);
		if(s.isPresent()) {
			serieRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Serie updateSerie(Serie nuevaSerie) {
		if (serieRepo.findById(nuevaSerie.getIdSerie()).isPresent()) {
			return serieRepo.save(nuevaSerie);
		}
		return null;
	}
}
