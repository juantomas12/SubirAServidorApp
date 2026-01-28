package com.joange.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.joange.model.Director;
import com.joange.model.Pelicula;
import com.joange.service.DirectorService;
import com.joange.service.PeliculaService;

@Controller
public class PeliculaController {

	@Autowired
	private PeliculaService peliculaService;
	
	@Autowired
	private DirectorService directorService;
	
	// Pantalla principal, muestra todas las películas
	@GetMapping(value = "/pelicula")
	public String getPelicula(Model model) {
		List<Pelicula> peliculas=peliculaService.findAllPelicula();
		model.addAttribute("peliculas", peliculas);
		model.addAttribute("all",true);
		return "peliculas";
	}
	
	// accedemos desde /pelicula. Muestra información de una sóla
	@GetMapping(value = "/pelicula/{id}")
	public String getPeliculaById(@PathVariable Long id, Model model) {
		Optional<Pelicula> pelicula=peliculaService.findPeliculaById(id);
		if (pelicula.isPresent()) {
			model.addAttribute("vacio", false);
			model.addAttribute("pelicula",pelicula.get());
		}
		else {
			model.addAttribute("vacio", true);
			model.addAttribute("msg","Sin película con Id "+ id);
		}
		return "pelicula";
	}
	
	//Formulario de añadir película sin ningun director
	@RequestMapping(value = "/pelicula/add")
	public String addPelicula(Model model) {
		// creamos película en blanco
		Pelicula p=new Pelicula();
		Director d= new Director();
		p.setDirector(d);
		List<Director> directores=directorService.findAllDirector();
		model.addAttribute("pelicula", p);
		model.addAttribute("directores", directores);
		model.addAttribute("idNewDire",0);
		model.addAttribute("nuevo", true);
		return "peliculaForm";
	}
	
	// guardamos la película nueva. Recibimos el número del director
	@PostMapping(value = "/pelicula/save")
	public String savePelicula(
			@ModelAttribute("pelicula") Pelicula pelicula,
			@ModelAttribute(name="idNewDire") Long idNewDire) {
		
		if (idNewDire>0) {
			Optional<Director> director=directorService.findDirectorById(idNewDire);
			if (director.isPresent()) {
				pelicula.setDirector(director.get());
			}
		}
		peliculaService.savePelicula(pelicula);
		return "redirect:/pelicula";
	}
	
	// creamos un película de un director concreto
	@RequestMapping(value="/director/{idDirector}/pelicula")
	public String savePeliculaToDire(Model model,
			@PathVariable String idDirector) {
		Optional<Director> director=directorService.findDirectorById(Long.parseLong(idDirector));
		if (director.isPresent()) {
			Pelicula pelicula=new Pelicula();
			pelicula.setDirector(director.get());
			model.addAttribute("pelicula", pelicula);
			return "peliculaDirectorForm";
		}
		return "redirect:/director";
	}
	
	// modifica una película
	@RequestMapping(value="/pelicula/update/{id}")
	public String updatePelicula(@PathVariable Long id, Model model) {
		 Optional<Pelicula> pelicula=peliculaService.findPeliculaById(id);
		 if (pelicula.isPresent()) {
			 model.addAttribute("pelicula",pelicula.get());
			 model.addAttribute("nuevo",false);
			 return "peliculaForm";
		 }
		 else {
				return "redirect:/director";
			}	
	  }
	 
	
	// elimina la película
	@RequestMapping(value="/pelicula/delete/{id}")
	public String deletePelicula(@PathVariable Long id, Model model) {
		boolean res=peliculaService.deletePelicula(id);
		return "redirect:/pelicula";		
	  }
	
	
	// mostrar películas de un director dado
	@RequestMapping(value="/pelicula/director/{id}")
	public String peliculaByIdDirector(@PathVariable Long id, Model model) {
		 List<Pelicula> peliculas=peliculaService.findPeliculaByDirectorId(id);
		 model.addAttribute("peliculas",peliculas);
		 model.addAttribute("all",false);
		 model.addAttribute("idDirector",id);
		 return "peliculas";
		 }
	


}
