package datos;

/**
 * Enum que representa las familias válidas de producto.
 */
public enum FamiliaTipo {
    DEPORTIVOS(1),
    MUSICALES(2),
    ESCENICOS(3);

    private final int codigo;

    FamiliaTipo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static FamiliaTipo fromInt(int codigo) {
        for (FamiliaTipo f : values()) {
            if (f.codigo == codigo) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case DEPORTIVOS:
                return "Deportivos";
            case MUSICALES:
                return "Musicales";
            case ESCENICOS:
                return "Escénicos";
            default:
                return super.toString();
        }
    }
}
