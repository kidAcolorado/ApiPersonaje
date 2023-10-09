package com.viewnext.kidaprojects.apipersonajes.service;

import java.util.List;

import com.viewnext.kidaprojects.apipersonajes.model.Personaje;

/**
 * La interfaz {@code PersonajeService} define los métodos que deben implementarse
 * para gestionar las operaciones relacionadas con la entidad "Personaje".
 *
 * @version 1.0
 * @since 06 de octubre de 2023
 */
public interface PersonajeService {

    /**
     * Obtiene todos los personajes.
     *
     * @return Una lista de todos los personajes en el sistema.
     */
    List<Personaje> showAll();

    /**
     * Obtiene todos los personajes activos.
     *
     * @return Una lista de todos los personajes activos en el sistema.
     */
    List<Personaje> showActivos();

    /**
     * Obtiene un personaje por su ID.
     *
     * @param idPersonaje El ID del personaje a obtener.
     * @return El personaje con el ID especificado.
     */
    Personaje showPersonajeById(int idPersonaje);

    /**
     * Crea un nuevo personaje en el sistema.
     *
     * @param personaje El personaje a crear.
     * @return El personaje recién creado.
     */
    Personaje createPersonaje(Personaje personaje);

    /**
     * Actualiza la vida de un personaje en el sistema.
     *
     * @param idPersonaje El ID del personaje a actualizar.
     * @param damage      El daño a aplicar al personaje.
     * @return El personaje actualizado después de aplicar el daño.
     */
    Personaje updateVida(int idPersonaje, int damage);

    /**
     * Reclama recompensa de una misión y actualiza el personaje.
     *
     * @param idMision    El ID de la misión a reclamar.
     * @param idPersonaje El ID del personaje que reclama la recompensa.
     * @return El personaje actualizado después de reclamar la recompensa de la misión.
     */
    Personaje reclamarRecompensaMision(int idMision, int idPersonaje);

    /**
     * Reclama recompensa de un enemigo vencido y actualiza el personaje.
     *
     * @param idEnemigo   El ID del enemigo vencido.
     * @param idPersonaje El ID del personaje que reclama la recompensa.
     * @return El personaje actualizado después de reclamar la recompensa del enemigo.
     */
    Personaje reclamarRecompensaEnemigo(int idEnemigo, int idPersonaje);

    /**
     * Actualiza la experiencia de un personaje en el sistema.
     *
     * @param idPersonaje El ID del personaje a actualizar.
     * @param experiencia La cantidad de experiencia a agregar al personaje.
     * @return El personaje actualizado después de agregar experiencia.
     */
    Personaje updateExperiencia(int idPersonaje, int experiencia);

    /**
     * Sube de nivel a un personaje en el sistema.
     *
     * @param idPersonaje El ID del personaje a subir de nivel.
     * @return El personaje actualizado después de subir de nivel.
     */
    Personaje subirNivel(int idPersonaje);

    /**
     * Actualiza el estado (activo/inactivo) de un personaje en el sistema.
     *
     * @param idPersonaje El ID del personaje a actualizar.
     * @param estado      El nuevo estado del personaje.
     * @return El personaje actualizado con su nuevo estado.
     */
    Personaje setActividadPersonaje(int idPersonaje, boolean estado);

    /**
     * Reinicia todos los personajes en el sistema.
     */
    void reiniciarPersonajes();
}

