package aluguer.veiculos.aluguerveiculos.Transport;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FuncionarioModelTransportTest {
    @Test
    void checkInstance() {
        FuncionarioModelTransport funcionarioModelTransport = FuncionarioModelTransport.getInstance();

        assertNotNull(funcionarioModelTransport);
    }

    @Test
    void getFuncionarioModel() {
        FuncionarioModelTransport funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        FuncionarioModel funcionarioModel = new FuncionarioModel(1, "admin", "admin", "admin", new ArrayList<>(List.of(Permissoes.GESTAO_FUNCIONARIOS)));

        funcionarioModelTransport.saveFuncionarioModel(funcionarioModel);

        assertNotNull(funcionarioModelTransport.getFuncionarioModel());
    }

}