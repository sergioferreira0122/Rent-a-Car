package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

public interface IEstatisticas {
    int totalClientes();

    int loadVeiculosDisponiveis();

    double loadReceitaTotal();

    int loadTotalVeiculosEmUso();
}
