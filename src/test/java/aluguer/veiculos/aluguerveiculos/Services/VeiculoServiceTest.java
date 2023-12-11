package aluguer.veiculos.aluguerveiculos.Services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VeiculoServiceTest {

    @Test
    void verificarModeloDeMarca() {
        assertTrue(VeiculoService.getInstance().verificarModeloDeMarca(1, 1));
    }
}