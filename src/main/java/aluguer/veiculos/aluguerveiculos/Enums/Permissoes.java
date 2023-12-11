package aluguer.veiculos.aluguerveiculos.Enums;

public enum Permissoes {
    GESTAO_FUNCIONARIOS,
    GESTAO_PEDIDOS,
    GESTAO_VEICULOS,
    GESTAO_CLIENTES,
    GESTAO_MARCAS,
    GESTAO_MODELOS,
    GESTAO_SERVICOS,
    GESTAO_ALUGUERS,
    GESTAO_REPARACOES,
    GESTAO_RECIBOS,
    GESTAO_FATURAS,
    GESTAO_CONDUTORES,
    SEM_PERMISSAO;

    public static Permissoes fromString(String permissao) {
        for (Permissoes tc : Permissoes.values()) {
            if (tc.toString().equalsIgnoreCase(permissao)) {
                return tc;
            }
        }
        throw new IllegalArgumentException("Tipo de permissão inválida: " + permissao);
    }

    @Override
    public String toString() {
        return switch (this) {
            case GESTAO_FUNCIONARIOS -> "Gestao Funcionarios";
            case GESTAO_PEDIDOS -> "Gestao Pedidos";
            case GESTAO_VEICULOS -> "Gestao Veiculos";
            case GESTAO_CLIENTES -> "Gestao Clientes";
            case GESTAO_MARCAS -> "Gestao Marcas";
            case GESTAO_MODELOS -> "Gestao de Modelos";
            case GESTAO_SERVICOS -> "Gestao de Servicos";
            case GESTAO_ALUGUERS -> "Gestao Aluguers";
            case GESTAO_REPARACOES -> "Gestao Reparacoes";
            case GESTAO_RECIBOS -> "Gestao Recibos";
            case GESTAO_FATURAS -> "Gestao Faturas";
            case GESTAO_CONDUTORES -> "Gestao Condutores";
            case SEM_PERMISSAO -> "Sem Permissao";

        };
    }
}
