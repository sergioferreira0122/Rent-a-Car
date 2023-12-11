package aluguer.veiculos.aluguerveiculos.Enums;

public enum TipoCliente {
    INDIVIDUAL,
    EMPRESA;

    public static TipoCliente fromString(String tipoCliente) {
        for (TipoCliente tc : TipoCliente.values()) {
            if (tc.toString().equalsIgnoreCase(tipoCliente)) {
                return tc;
            }
        }
        throw new IllegalArgumentException("Tipo de cliente inválido: " + tipoCliente);
    }

    @Override
    public String toString() {
        return switch (this) {
            case INDIVIDUAL -> "Individual";
            case EMPRESA -> "Empresa";
        };
    }
}
