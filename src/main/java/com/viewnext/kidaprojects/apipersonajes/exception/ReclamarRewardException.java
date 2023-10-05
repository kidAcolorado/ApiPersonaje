package com.viewnext.kidaprojects.apipersonajes.exception;

public class ReclamarRewardException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nueva instancia de la excepción {@code UnknownErrorException} con un mensaje
     * predeterminado.
     */
    public ReclamarRewardException() {
    	super("Error al reclamar la recompensa");
	}
        
    
}
