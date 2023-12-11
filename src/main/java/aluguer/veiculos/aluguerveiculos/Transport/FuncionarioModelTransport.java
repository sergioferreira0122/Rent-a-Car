package aluguer.veiculos.aluguerveiculos.Transport;

import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;

public class FuncionarioModelTransport {
    private static FuncionarioModelTransport funcionarioModelTransport;
    private FuncionarioModel funcionarioModel;

    private FuncionarioModelTransport() {

    }

    public static synchronized FuncionarioModelTransport getInstance() {
        if (funcionarioModelTransport == null) {
            funcionarioModelTransport = new FuncionarioModelTransport();
        }
        return funcionarioModelTransport;
    }

    public void saveFuncionarioModel(FuncionarioModel funcionarioModel) {
        this.funcionarioModel = funcionarioModel;
    }

    public FuncionarioModel getFuncionarioModel() {
        return funcionarioModel;
    }
}
