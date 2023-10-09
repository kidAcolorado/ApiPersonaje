package com.viewnext.kidaprojects.apipersonajes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viewnext.kidaprojects.apipersonajes.model.Personaje;

/**
 * La interfaz {@code PersonajeRepository} proporciona métodos para acceder y gestionar
 * objetos de la entidad {@code Personaje} en la base de datos.
 *
 * <p>
 * El autor de esta interfaz es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 06 de octubre de 2023
 */
public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {

    /**
     * Recupera una lista de personajes activos en el sistema.
     *
     * @return Lista de personajes activos.
     */
    @Query("SELECT p FROM Personaje p WHERE p.activo = true")
    List<Personaje> findPersonajesActivos();
}
