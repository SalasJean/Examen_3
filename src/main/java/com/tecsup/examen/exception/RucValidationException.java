package com.tecsup.examen.exception;


//aqui vamos a heredar atributos de exceptions de el clasr padre runtime recuerda que esta es chequeada
public class RucValidationException extends RuntimeException {
    public RucValidationException(String message) {
        super(message);
    }
}
//recuerda que estas clases son claes de transporte de errroes
