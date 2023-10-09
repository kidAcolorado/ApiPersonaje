package com.viewnext.kidaprojects.apipersonajes.restcontroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kidaprojects.apipersonajes.exception.ReclamarRewardException;
import com.viewnext.kidaprojects.apipersonajes.model.Personaje;
import com.viewnext.kidaprojects.apipersonajes.service.PersonajeService;

import jakarta.persistence.EntityNotFoundException;

/**
 * El controlador {@code PersonajeRestController} maneja las solicitudes
 * relacionadas con la entidad "Personaje" en el sistema. Proporciona endpoints
 * para recuperar, crear, actualizar y gestionar personajes, así como para
 * reiniciar su estado.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 06 de octubre de 2023
 */
@RestController
public class PersonajeRestController {

	@Autowired
	private PersonajeService service;

	private static final String PERSONAJE_NOT_FOUND = "Personaje/s no encontrado";
	

	/**
     * Obtiene todos los personajes.
     *
     * @return ResponseEntity con la lista de personajes si existen, o un mensaje de error si no se encuentran.
     */
	@GetMapping(value = "personaje", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showAllPersonajes() {
		try {
			List<Personaje> listaPersonajes = service.showAll();
			return ResponseEntity.ok(listaPersonajes);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}

	}

	/**
     * Obtiene todos los personajes activos.
     *
     * @return ResponseEntity con la lista de personajes activos si existen, o un mensaje de error si no se encuentran.
     */
	@GetMapping(value = "personaje/activo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showPersonajesActivos() {
		try {
			List<Personaje> listaPersonajes = service.showActivos();
			return ResponseEntity.ok(listaPersonajes);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	/**
     * Obtiene un personaje por su ID.
     *
     * @param idPersonaje El ID del personaje a obtener.
     * @return ResponseEntity con el personaje si existe, o un mensaje de error si no se encuentra.
     */
	@GetMapping(value = "personaje/{idPersonaje}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showPersonajeById(@PathVariable("idPersonaje") int idPersonaje) {
		try {
			Personaje personaje = service.showPersonajeById(idPersonaje);
			return ResponseEntity.ok(personaje);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	/**
	 * Crea un nuevo personaje.
	 *
	 * @param personaje El objeto Personaje a crear.
	 * @return ResponseEntity con el nuevo personaje creado y la URI del recurso.
	 */
	@PostMapping(value = "personaje", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {
		Personaje personajeCreado = service.createPersonaje(personaje);

		// Utiliza la ruta relativa al recurso
		URI location = URI.create("/personaje/" + personajeCreado.getIdPersonaje());

		// Devuelve una respuesta con el código 201 Created y la URI del nuevo recursos
		return ResponseEntity.created(location).body(personajeCreado);
	}

	/**
	 * Actualiza la vida de un personaje.
	 *
	 * @param idPersonaje El ID del personaje a actualizar.
	 * @param damage El daño a aplicar al personaje.
	 * @return ResponseEntity con el personaje actualizado.
	 */
	@PutMapping(value = "personaje/{idPersonaje}/damage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateVidaPersonaje(@PathVariable("idPersonaje") int idPersonaje,
			@RequestParam("damage") int damage) {
		try {
			Personaje personajeActualizado = service.updateVida(idPersonaje, damage);
			return ResponseEntity.ok(personajeActualizado);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	/**
	 * Reclama recompensa de una misión y actualiza el personaje.
	 *
	 * @param idMision El ID de la misión a reclamar.
	 * @param idPersonaje El ID del personaje que reclama la recompensa.
	 * @return ResponseEntity con el personaje actualizado.
	 */
	@PutMapping(value = "personaje/mision/{idMision}/{idPersonaje}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reclamarRecompensaMisionAndUpdate(@PathVariable("idMision") int idMision,
			@PathVariable("idPersonaje") int idPersonaje) {
		try {
			Personaje personajeActualizado = service.reclamarRecompensaMision(idMision, idPersonaje);
			return ResponseEntity.ok(personajeActualizado);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ReclamarRewardException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	/**
	 * Reclama recompensa de un enemigo vencido y actualiza el personaje.
	 *
	 * @param idEnemigo El ID del enemigo vencido.
	 * @param idPersonaje El ID del personaje que reclama la recompensa.
	 * @return ResponseEntity con el personaje actualizado.
	 */
	@PutMapping(value = "personaje/enemigo/{idEnemigo}/{idPersonaje}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reclamarRecompensaEnemigoAndUpdate(@PathVariable("idEnemigo") int idEnemigo,
			@PathVariable("idPersonaje") int idPersonaje) {
		try {
			Personaje personajeActualizado = service.reclamarRecompensaEnemigo(idEnemigo, idPersonaje);
			return ResponseEntity.ok(personajeActualizado);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ReclamarRewardException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}
	
	/**
	 * Actualiza el estado (activo/inactivo) de un personaje.
	 *
	 * @param idPersonaje El ID del personaje a actualizar.
	 * @param estado El nuevo estado del personaje.
	 * @return ResponseEntity con el personaje actualizado.
	 */
	@PutMapping(value = "personaje/{idPersonaje}/estado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setEstadoPersonaje(@PathVariable("idPersonaje") int idPersonaje,
			@RequestParam("estado") boolean estado) {
		try {
			Personaje personajeActualizado = service.setActividadPersonaje(idPersonaje, estado);
			return ResponseEntity.ok(personajeActualizado);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	/**
	 * Reinicia todos los personajes.
	 *
	 * @return ResponseEntity con un mensaje de éxito.
	 */
	@PostMapping(value = "personaje/reinicio")
	public ResponseEntity<String> reiniciarPersonajes() {
		service.reiniciarPersonajes();
		return ResponseEntity.ok("Personajes reiniciados");
	}

}
