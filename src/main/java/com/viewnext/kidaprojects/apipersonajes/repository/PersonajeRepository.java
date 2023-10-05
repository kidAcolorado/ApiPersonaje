package com.viewnext.kidaprojects.apipersonajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viewnext.kidaprojects.apipersonajes.model.Personaje;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {

	@Query("SELECT p FROM Personaje p WHERE p.activo = true")
	List<Personaje> findPersonajesActivos();
}
