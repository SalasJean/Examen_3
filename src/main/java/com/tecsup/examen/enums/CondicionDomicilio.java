package com.tecsup.examen.enums;

// 📌 2. Condición de domicilio (viene del campo "condicion")
public enum CondicionDomicilio {
    HABIDO,       // "HABIDO" desde API
    NO_HABIDO,    // Por si viene "NO HABIDO"
    PENDIENTE,    // Por si viene "PENDIENTE"
    DESCONOCIDO;  // Fallback

    public static CondicionDomicilio fromString(String value) {
        if (value == null) return DESCONOCIDO;
        return switch (value.toUpperCase().trim()) {
            case "HABIDO"              -> HABIDO;
            case "NO HABIDO",
                 "NO_HABIDO"          -> NO_HABIDO;
            case "PENDIENTE"           -> PENDIENTE;
            default                    -> DESCONOCIDO; // ✅ fallback obligatorio
        };
    }
}