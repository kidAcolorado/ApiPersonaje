package com.viewnext.kidaprojects.apipersonajes.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * La clase {@code Personaje} representa a un personaje en el sistema.
 * Contiene propiedades como nombre, fuerza, defensa, nivel, vida, experiencia
 * y estado de actividad.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 06 de octubre de 2023
 */
@Entity
@Table(name = "personajes")
public class Personaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPersonaje;
	private String nombre;
	private int fuerzaOriginal;
	private int fuerzaActual;
	private int defensaOriginal;
	private int defensaActual;
	private int nivel;
	private int vidaOriginal;
	private int vidaTotal;
	private int vidaActual;
	private int experiencia;
	private boolean activo;
	
	public Personaje(String nombre, int fuerzaOriginal, int defensaOriginal, int vidaOriginal) {
		super();
		this.nombre = nombre;
		this.fuerzaOriginal = fuerzaOriginal;
		this.fuerzaActual = fuerzaOriginal;
		this.defensaOriginal = defensaOriginal;
		this.defensaActual = defensaOriginal;
		this.nivel = 1;
		this.vidaOriginal = vidaOriginal;
		this.vidaTotal = vidaOriginal;
		this.vidaActual = vidaOriginal;
		this.experiencia = 0;
		this.activo = true;
	}
	
	public Personaje() {
		
	}

	public int getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(int idPersonaje) {
		this.idPersonaje = idPersonaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getFuerzaOriginal() {
		return fuerzaOriginal;
	}

	public void setFuerzaOriginal(int fuerzaOriginal) {
		this.fuerzaOriginal = fuerzaOriginal;
	}

	public int getFuerzaActual() {
		return fuerzaActual;
	}

	public void setFuerzaActual(int fuerzaActual) {
		this.fuerzaActual = fuerzaActual;
	}

	public int getDefensaOriginal() {
		return defensaOriginal;
	}

	public void setDefensaOriginal(int defensaOriginal) {
		this.defensaOriginal = defensaOriginal;
	}

	
	public int getVidaTotal() {
		return vidaTotal;
	}

	public void setVidaTotal(int vidaTotal) {
		this.vidaTotal = vidaTotal;
	}

	public int getDefensaActual() {
		return defensaActual;
	}

	public void setDefensaActual(int defensaActual) {
		this.defensaActual = defensaActual;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getVidaOriginal() {
		return vidaOriginal;
	}

	public void setVidaOriginal(int vidaOriginal) {
		this.vidaOriginal = vidaOriginal;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public void setVidaActual(int vidaActual) {
		this.vidaActual = vidaActual;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPersonaje);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personaje other = (Personaje) obj;
		return idPersonaje == other.idPersonaje;
	}

	@Override
	public String toString() {
		return "Personaje [idPersonaje=" + idPersonaje + ", nombre=" + nombre + ", fuerzaOriginal=" + fuerzaOriginal
				+ ", fuerzaActual=" + fuerzaActual + ", defensaOriginal=" + defensaOriginal + ", defensaActual="
				+ defensaActual + ", nivel=" + nivel + ", vidaOriginal=" + vidaOriginal + ", vidaActual=" + vidaActual
				+ ", experiencia=" + experiencia + ", activo=" + activo + "]";
	}
	
	
	

}
