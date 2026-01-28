package com.joange.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joange.model.Serie;

public interface SerieRepo extends JpaRepository<Serie,Long>{

	@Query("SELECT s FROM Serie s WHERE s.director.idDirector=?1")
	List<Serie> findByDirectorId(Long idDirector);

	List<Serie> findByPlataforma(String plataforma);
}
