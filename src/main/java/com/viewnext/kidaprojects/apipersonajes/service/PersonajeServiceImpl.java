package com.viewnext.kidaprojects.apipersonajes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.viewnext.kidaprojects.apipersonajes.exception.ReclamarRewardException;
import com.viewnext.kidaprojects.apipersonajes.model.Personaje;
import com.viewnext.kidaprojects.apipersonajes.repository.PersonajeRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class PersonajeServiceImpl implements PersonajeService {

	@Autowired
	PersonajeRepository personajeRepository;

	private WebClient misionWebClient;
	private WebClient enemigoWebClient;

	public PersonajeServiceImpl(WebClient misionWebClient, WebClient enemigoWebClient) {
		this.misionWebClient = misionWebClient;
		this.enemigoWebClient = enemigoWebClient;
	}

	@Override
	public List<Personaje> showAll() throws EntityNotFoundException {
		List<Personaje> listaPersonajes = personajeRepository.findAll();

		if (listaPersonajes.isEmpty()) {
			throw new EntityNotFoundException();
		}
		return listaPersonajes;
	}

	@Override
	public List<Personaje> showActivos() throws EntityNotFoundException {
		List<Personaje> listaPersonajes = personajeRepository.findPersonajesActivos();

		if (listaPersonajes.isEmpty()) {
			throw new EntityNotFoundException();
		}
		return listaPersonajes;
	}

	@Override
	public Personaje showPersonajeById(int idPersonaje) throws EntityNotFoundException {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return optionalPersonaje.get();
	}

	@Override
	public Personaje createPersonaje(Personaje personaje) {

		return personajeRepository.save(personaje);
	}

	@Override
	public Personaje updateVida(int idPersonaje, int damage) throws EntityNotFoundException {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		Personaje personaje = optionalPersonaje.get();

		int vidaActual = personaje.getVidaActual();

		personaje.setVidaActual(vidaActual - damage);

		if (vidaActual <= 0) {
			personaje.setActivo(false);
		}

		return personajeRepository.save(personaje);
	}

	@Override
	public Personaje subirNivel(int idPersonaje) {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		Personaje personaje = optionalPersonaje.get();

		personaje.setNivel(personaje.getNivel() + 1);
		personaje.setFuerzaActual(personaje.getFuerzaActual() + 10);
		personaje.setDefensaActual(personaje.getDefensaActual() + 10);
		personaje.setVidaTotal(personaje.getVidaTotal() + 100);

		return personajeRepository.save(personaje);
	}

	@Override
	public Personaje updateExperiencia(int idPersonaje, int experiencia) throws EntityNotFoundException {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		Personaje personaje = optionalPersonaje.get();

		int experienciaActual = personaje.getExperiencia();

		// Sumamos la experiencia obtenida
		experienciaActual += experiencia;

		// Controlamos si al expericia acumulada es suficiente para subir de nivel. El
		// umbral es 1000
		if (experienciaActual >= 1000) {
			personaje = subirNivel(idPersonaje); // Invocamos al metodo que suma estadísticas
			experienciaActual -= 1000; // Actualizamos experiencia para el siguiente nivel
		}

		personaje.setExperiencia(experienciaActual);

		return personajeRepository.save(personaje);
	}

	@Override
	public Personaje reclamarRecompensaMision(int idMision, int idPersonaje)
			throws EntityNotFoundException, ReclamarRewardException {
		try {
			Integer recompensa = misionWebClient.get()
					.uri("mision/recompensa/{idMision}", idMision)
					.retrieve()
					.bodyToMono(Integer.class)
					.block();

			return updateExperiencia(idPersonaje, recompensa);

		} catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new EntityNotFoundException();
			}
		}

		throw new ReclamarRewardException();
	}

	@Override
	public Personaje reclamarRecompensaEnemigo(int idEnemigo, int idPersonaje)
			throws EntityNotFoundException, ReclamarRewardException {
		try {
			Integer recompensa = enemigoWebClient.get()
					.uri("enemigo/recompensa/{idEnemigo}", idEnemigo)
					.retrieve()
					.bodyToMono(Integer.class)
					.block();

			return updateExperiencia(idPersonaje, recompensa);

		} catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new EntityNotFoundException();
			}
		}

		throw new ReclamarRewardException();
	}

	@Override
	public Personaje setActividadPersonaje(int idPersonaje, boolean estado) throws EntityNotFoundException {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		Personaje personaje = optionalPersonaje.get();

		personaje.setActivo(estado);

		return personajeRepository.save(personaje);
	}

	@Override
	public void reiniciarPersonajes() {
		List<Personaje> listaPersonajes = personajeRepository.findAll();

		for (Personaje p : listaPersonajes) {
			p.setActivo(true);
			p.setExperiencia(0);
			p.setNivel(1);
			p.setVidaActual(p.getVidaOriginal());

		}

		personajeRepository.saveAll(listaPersonajes);
	}

}
