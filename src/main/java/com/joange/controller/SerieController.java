package com.joange.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joange.model.Director;
import com.joange.model.Serie;
import com.joange.service.DirectorService;
import com.joange.service.SerieService;

@Controller
public class SerieController {

	@Autowired
	private SerieService serieService;

	@Autowired
	private DirectorService directorService;

	@GetMapping(value = "/serie")
	public String getSerie(Model model) {
		List<Serie> series = serieService.findAllSerie();
		model.addAttribute("series", series);
		model.addAttribute("all", true);
		return "series";
	}

	@GetMapping(value = "/serie/{id}")
	public String getSerieById(@PathVariable Long id, Model model) {
		Optional<Serie> serie = serieService.findSerieById(id);
		if (serie.isPresent()) {
			model.addAttribute("vacio", false);
			model.addAttribute("serie", serie.get());
		} else {
			model.addAttribute("vacio", true);
			model.addAttribute("msg", "Sin serie con Id " + id);
		}
		return "serie";
	}

	@RequestMapping(value = "/serie/add")
	public String addSerie(Model model) {
		Serie s = new Serie();
		Director d = new Director();
		s.setDirector(d);
		List<Director> directores = directorService.findAllDirector();
		model.addAttribute("serie", s);
		model.addAttribute("directores", directores);
		model.addAttribute("idNewDire", 0);
		model.addAttribute("nuevo", true);
		return "serieForm";
	}

	@PostMapping(value = "/serie/save")
	public String saveSerie(@ModelAttribute("serie") Serie serie, @ModelAttribute(name = "idNewDire") Long idNewDire) {

		if (idNewDire > 0) {
			Optional<Director> director = directorService.findDirectorById(idNewDire);
			if (director.isPresent()) {
				serie.setDirector(director.get());
			}
		}
		serieService.saveSerie(serie);
		return "redirect:/serie";
	}

	@RequestMapping(value = "/serie/update/{id}")
	public String updateSerie(@PathVariable Long id, Model model) {
		Optional<Serie> serie = serieService.findSerieById(id);
		if (serie.isPresent()) {
			List<Director> directores = directorService.findAllDirector();
			model.addAttribute("serie", serie.get());
			model.addAttribute("directores", directores);
			model.addAttribute("idNewDire", 0);
			model.addAttribute("nuevo", false);
			return "serieForm";
		} else {
			return "redirect:/serie";
		}
	}

	@RequestMapping(value = "/serie/delete/{id}")
	public String deleteSerie(@PathVariable Long id, Model model) {
		serieService.deleteSerie(id);
		return "redirect:/serie";
	}

	@RequestMapping(value="/director/{idDirector}/serie")
	public String saveSerieToDire(Model model,
			@PathVariable String idDirector) {
		Optional<Director> director=directorService.findDirectorById(Long.parseLong(idDirector));
		if (director.isPresent()) {
			Serie serie=new Serie();
			serie.setDirector(director.get());
			model.addAttribute("serie", serie);
			return "serieDirectorForm";
		}
		return "redirect:/director";
	}
	
	@RequestMapping(value = "/serie/director/{id}")
	public String serieByIdDirector(@PathVariable Long id, Model model) {
		List<Serie> series = serieService.findSerieByDirectorId(id);
		model.addAttribute("series", series);
		model.addAttribute("all", false);
		model.addAttribute("idDirector", id);
		return "series";
	}
}
