package com.joange.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joange.model.Director;
import com.joange.reporitory.DirectorRepo;
import com.joange.service.DirectorService;

@Service
public class DirectorServiceImple implements DirectorService{
	
	@Autowired
	DirectorRepo directorRepositorio;

	@Override
	public List<Director> findAllDirector() {
		return directorRepositorio.findAll();
	}
	

/* Error cuando no hay resultado
	@Override
	public Director findDirectorById(Long id) {
		Director d=directorRepositorio.getById(id);
		if (d==null)
			return new Director();
		else
			return d;
	}
*/
	
	@Override
	public Optional<Director>  findDirectorById(Long id) {
		return directorRepositorio.findById(id);

		
	}
	@Override
	public Director saveDirector(Director nuevoDirector) {
		Director d;
		if (nuevoDirector!=null) {
			d=directorRepositorio.save(nuevoDirector);
		}
		else {
			d=new Director();
		}
		return d;
	}

	@Override
	public String deleteDirector(Long id) {
		Optional<Director> d=directorRepositorio.findById(id);
		if(d.isPresent()) {
			directorRepositorio.deleteById(id);
			return "Director eliminado satisfactoriamente";
		}
		return "El director no existe";
	}

	@Override
	public String updateDirector(Director directorActualizar) {		
		if( directorRepositorio.findById(directorActualizar.getIdDirector()).isPresent()) { // si existe
			// creo uno nuevo copia del que me pasan
			/*
			Director nuevo=new Director(
					directorActualizar.getIdDirector(),
					directorActualizar.getNombre(),
					directorActualizar.getAnyo_Nacimiento());
			directorRepositorio.save(nuevo);
			*/
			directorRepositorio.save(directorActualizar);
			return "Director " + directorActualizar.getIdDirector()+ " actualizado";
		}
		else{
			return "No se ha podido actualizar " + directorActualizar.getIdDirector();
		}
		
	}
	
	@Override
	public List<Director>findOldDirector(){
		return directorRepositorio.findOldDirector();
	}
	
	@Override
	public List<Director>findDirectorByYear(int year){
		return directorRepositorio.findDirectorByYear(year);
	}
		

}
