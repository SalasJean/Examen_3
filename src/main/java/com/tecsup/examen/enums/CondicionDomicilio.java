package com.tecsup.examen.enums;


public enum CondicionDomicilio {
    HABIDO,
    NO_HABIDO,
    PENDIENTE,
    DESCONOCIDO;

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