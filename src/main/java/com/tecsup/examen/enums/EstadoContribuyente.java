package com.tecsup.examen.enums;

// 📌 1. Estado del contribuyente (viene del campo "estado")
public enum EstadoContribuyente {
    ACTIVO,      // "ACTIVO" desde API
    BAJA,        // Por si viene "BAJA"
    SUSPENDIDO,  // Por si viene "SUSPENDIDO"
    DESCONOCIDO; // Fallback para valores no esperados

    // Método útil para convertir string a enum seguro
    //vamos a barajar la opcion de agregar un metood con switch cases primero veremos el funcionamiento
    public static EstadoContribuyente fromString(String value) {
        if (value == null) return DESCONOCIDO;
        return switch (value.toUpperCase().trim()) {
            case "ACTIVO"     -> ACTIVO;
            case "BAJA"       -> BAJA;
            case "SUSPENDIDO" -> SUSPENDIDO;
            default           -> DESCONOCIDO; // ✅ fallback obligatorio
        };
    }
    //recuerda investigar el metodo si es buena practica implemetar aqui si? porfavor
}