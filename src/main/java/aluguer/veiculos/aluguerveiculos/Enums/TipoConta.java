package aluguer.veiculos.aluguerveiculos.Enums;

public enum TipoConta {
    CONTA_CORRENTE,
    ESPORADICO;

    public static TipoConta fromString(String tipoConta) {
        for (TipoConta tc : TipoConta.values()) {
            if (tc.toString().equalsIgnoreCase(tipoConta)) {
                return tc;
            }
        }
        throw new IllegalArgumentException("Tipo de conta inválida: " + tipoConta);
    }

    @Override
    public String toString() {
        return switch (this) {
            case CONTA_CORRENTE -> "Conta corrente";
            case ESPORADICO -> "Esporadico";
        };
    }
}
