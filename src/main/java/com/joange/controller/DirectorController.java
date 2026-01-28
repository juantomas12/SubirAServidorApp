package com.joange.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joange.controller.DirectorController;
import com.joange.model.Director;
import com.joange.model.Pelicula;
import com.joange.service.DirectorService;
import com.joange.service.PeliculaService;

@Controller
public class DirectorController {
	
	@Autowired
	private DirectorService directorService;
	
	@Autowired
	private PeliculaService peliculaService;
	
	

	

	@GetMapping(value = "/director")
	public  String getDirector(Model model) {
		List<Director> losDirectores= directorService.findAllDirector();
		model.addAttribute("directores", losDirectores);
		return "directores";
	}
	
	@GetMapping(value = "/directorAnyo")
	public String getDirectorByYear(@RequestParam("anyo") int year, Model model) {
		List<Director>losDirectores= directorService.findDirectorByYear(year);
		if (losDirectores.size()>0) {
			model.addAttribute("director", losDirectores.get(0));
			model.addAttribute("vacio", false);
		}
		else {
			model.addAttribute("vacio", true);
			model.addAttribute("msg", "Sin directores del anyo "+ year);
		}
		return "director";
	}
	
	
	@GetMapping(value = "/director/{id}")
	public  String getDirectorById(@PathVariable Long id, Model model) {
		Optional<Director>elDirector= directorService.findDirectorById(id);
		if (elDirector.isPresent()) {
			model.addAttribute("director", elDirector.get());
			model.addAttribute("vacio", false);
		}
		else {
			model.addAttribute("msg", "Sin directores con id "+ id);
			model.addAttribute("vacio", true);
		}
		return "director";
	}
	
	
	@RequestMapping(value = "/director/add")
	public String addDirector(Model model) {
		Director d=new Director();
		model.addAttribute("director", d);
		model.addAttribute("nuevo", true);
		return "directorForm";
	}
	
	@GetMapping(value = "/director/delete/{id}")
	public String deleteDirector(@PathVariable Long id) {
		 String respuesta=directorService.deleteDirector(id);
		 System.out.println(respuesta);	
		 return "redirect:/director"; 
	}
	
	
	@RequestMapping(value = "/director/update/{id}")
	public String updateDirector(@PathVariable Long id, Model model) {
		Optional<Director> directorOpt=directorService.findDirectorById(id);
		if (directorOpt.isPresent()) {
			model.addAttribute("director",directorOpt.get());
			model.addAttribute("nuevo",false);
			return "directorForm";
		}
		else {
			return "redirect:/director";
		}
	}
	
	@PostMapping(value = "/director/save")
	public String updateDirector(
			@ModelAttribute("director") Director director) {
		directorService.saveDirector(director);
		return "redirect:/director";
	}
	
	@GetMapping(value = "/directorOld")
	public List<Director> getDirectorOld() {
		return directorService.findOldDirector();
	}
	
	
}
