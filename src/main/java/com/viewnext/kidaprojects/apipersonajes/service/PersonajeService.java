package com.viewnext.kidaprojects.apipersonajes.service;

import java.util.List;

import com.viewnext.kidaprojects.apipersonajes.model.Personaje;

public interface PersonajeService {

	List<Personaje> showAll();

	List<Personaje> showActivos();

	Personaje showPersonajeById(int idPersonaje);

	Personaje createPersonaje(Personaje personaje);

	Personaje updateVida(int idPersonaje, int damage);

	Personaje reclamarRecompensaMision(int idMision, int idPersonaje);

	Personaje reclamarRecompensaEnemigo(int idEnemigo, int idPersonaje);

	Personaje updateExperiencia(int idPersonaje, int experiencia);

	Personaje subirNivel(int idPersonaje);

	Personaje setActividadPersonaje(int idPersonaje, boolean estado);

	void reiniciarPersonajes();

}
