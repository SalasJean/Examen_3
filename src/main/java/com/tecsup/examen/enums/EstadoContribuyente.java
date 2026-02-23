package com.tecsup.examen.enums;


public enum EstadoContribuyente {
    ACTIVO,
    BAJA,
    SUSPENDIDO,
    DESCONOCIDO;

    // Método útil para convertir string a enum seguro
    //vamos a barajar la opcion de agregar un metood con switch cases primero veremos el funcionamiento
    public static EstadoContribuyente fromString(String value) {
        if (value == null) return DESCONOCIDO;
        return switch (value.toUpperCase().trim()) {
            case "ACTIVO"     -> ACTIVO;
            case "BAJA"       -> BAJA;
            case "SUSPENDIDO" -> SUSPENDIDO;
            default           -> DESCONOCIDO;
        };
    }
    //recuerda investigar el metodo si es buena practica implemetar aqui si? porfavor
}