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

/**
 * La clase {@code PersonajeServiceImpl} implementa la interfaz {@code PersonajeService}
 * y proporciona métodos para administrar la entidad "Personaje" en el sistema.
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 * 
 * @version 1.0
 * @since 06 de octubre de 2023
 */
@Service
public class PersonajeServiceImpl implements PersonajeService {

	@Autowired
	PersonajeRepository personajeRepository;

	private WebClient misionWebClient;
	private WebClient enemigoWebClient;

	/**
     * Constructor de la clase {@code PersonajeServiceImpl}.
     *
     * @param misionWebClient   Cliente web para acceder a las misiones.
     * @param enemigoWebClient  Cliente web para acceder a los enemigos.
     */
	public PersonajeServiceImpl(WebClient misionWebClient, WebClient enemigoWebClient) {
		this.misionWebClient = misionWebClient;
		this.enemigoWebClient = enemigoWebClient;
	}

	/**
     * Recupera todos los personajes en el sistema.
     *
     * @return Lista de personajes si existen, o lanza una excepción {@code EntityNotFoundException} si no se encuentran.
     * @throws EntityNotFoundException Si no se encuentran personajes.
     */
	@Override
	public List<Personaje> showAll() throws EntityNotFoundException {
		List<Personaje> listaPersonajes = personajeRepository.findAll();

		if (listaPersonajes.isEmpty()) {
			throw new EntityNotFoundException();
		}
		return listaPersonajes;
	}

	 /**
     * Recupera todos los personajes activos en el sistema.
     *
     * @return Lista de personajes activos si existen, o lanza una excepción {@code EntityNotFoundException} si no se encuentran.
     * @throws EntityNotFoundException Si no se encuentran personajes activos.
     */
	@Override
	public List<Personaje> showActivos() throws EntityNotFoundException {
		List<Personaje> listaPersonajes = personajeRepository.findPersonajesActivos();

		if (listaPersonajes.isEmpty()) {
			throw new EntityNotFoundException();
		}
		return listaPersonajes;
	}

	/**
     * Recupera un personaje por su ID.
     *
     * @param idPersonaje El ID del personaje a obtener.
     * @return El personaje si existe, o lanza una excepción {@code EntityNotFoundException} si no se encuentra.
     * @throws EntityNotFoundException Si no se encuentra el personaje.
     */
	@Override
	public Personaje showPersonajeById(int idPersonaje) throws EntityNotFoundException {
		Optional<Personaje> optionalPersonaje = personajeRepository.findById(idPersonaje);

		if (optionalPersonaje.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return optionalPersonaje.get();
	}

	/**
     * Crea un nuevo personaje en el sistema.
     *
     * @param personaje El objeto {@code Personaje} a crear.
     * @return El nuevo personaje creado.
     */
	@Override
	public Personaje createPersonaje(Personaje personaje) {

		return personajeRepository.save(personaje);
	}

	/**
     * Actualiza la vida de un personaje.
     *
     * @param idPersonaje El ID del personaje a actualizar.
     * @param damage      El daño a aplicar al personaje.
     * @return El personaje actualizado.
     * @throws EntityNotFoundException Si no se encuentra el personaje.
     */
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

	/**
	 * Sube de nivel a un personaje en el sistema.
	 *
	 * @param idPersonaje El ID del personaje a subir de nivel.
	 * @return El personaje actualizado después de subir de nivel.
	 * @throws EntityNotFoundException Si no se encuentra el personaje.
	 */
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

	/**
	 * Actualiza la experiencia de un personaje.
	 *
	 * @param idPersonaje   El ID del personaje a actualizar.
	 * @param experiencia   La cantidad de experiencia a agregar al personaje.
	 * @return El personaje actualizado después de agregar experiencia.
	 * @throws EntityNotFoundException Si no se encuentra el personaje.
	 */
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

	/**
	 * Reclama recompensa de una misión y actualiza el personaje.
	 *
	 * @param idMision     El ID de la misión a reclamar.
	 * @param idPersonaje  El ID del personaje que reclama la recompensa.
	 * @return El personaje actualizado después de reclamar la recompensa de la misión.
	 * @throws EntityNotFoundException Si no se encuentra la misión o el personaje.
	 * @throws ReclamarRewardException  Si ocurre un error al reclamar la recompensa.
	 */
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

	/**
	 * Reclama recompensa de un enemigo vencido y actualiza el personaje.
	 *
	 * @param idEnemigo    El ID del enemigo vencido.
	 * @param idPersonaje  El ID del personaje que reclama la recompensa.
	 * @return El personaje actualizado después de reclamar la recompensa del enemigo.
	 * @throws EntityNotFoundException Si no se encuentra el enemigo o el personaje.
	 * @throws ReclamarRewardException  Si ocurre un error al reclamar la recompensa.
	 */
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

	/**
	 * Actualiza el estado (activo/inactivo) de un personaje.
	 *
	 * @param idPersonaje  El ID del personaje a actualizar.
	 * @param estado       El nuevo estado del personaje.
	 * @return El personaje actualizado con su nuevo estado.
	 * @throws EntityNotFoundException Si no se encuentra el personaje.
	 */
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

	/**
	 * Reinicia todos los personajes en el sistema.
	 */
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
