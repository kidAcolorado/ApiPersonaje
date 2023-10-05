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

@RestController
public class PersonajeRestController {

	@Autowired
	private PersonajeService service;

	private static final String PERSONAJE_NOT_FOUND = "Personaje/s no encontrado";
	

	@GetMapping(value = "personaje", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showAllPersonajes() {
		try {
			List<Personaje> listaPersonajes = service.showAll();
			return ResponseEntity.ok(listaPersonajes);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}

	}

	@GetMapping(value = "personaje/activo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showPersonajesActivos() {
		try {
			List<Personaje> listaPersonajes = service.showActivos();
			return ResponseEntity.ok(listaPersonajes);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	@GetMapping(value = "personaje/{idPersonaje}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showPersonajeById(@PathVariable("idPersonaje") int idPersonaje) {
		try {
			Personaje personaje = service.showPersonajeById(idPersonaje);
			return ResponseEntity.ok(personaje);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSONAJE_NOT_FOUND);
		}
	}

	@PostMapping(value = "personaje", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {
		Personaje personajeCreado = service.createPersonaje(personaje);

		// Utiliza la ruta relativa al recurso
		URI location = URI.create("/personaje/" + personajeCreado.getIdPersonaje());

		// Devuelve una respuesta con el código 201 Created y la URI del nuevo recursos
		return ResponseEntity.created(location).body(personajeCreado);
	}

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

	@PostMapping(value = "personaje/reinicio")
	public ResponseEntity<String> reiniciarPersonajes() {
		service.reiniciarPersonajes();
		return ResponseEntity.ok("Personajes reiniciados");
	}

}
