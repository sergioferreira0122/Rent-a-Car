package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Estatisticas;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IEstatisticas;

public final class EstatisticasService {
    private static EstatisticasService estatisticasService;
    private final IEstatisticas estatisticasDAO;

    private EstatisticasService() {
        estatisticasDAO = new Estatisticas();
    }

    public static synchronized EstatisticasService getInstance() {
        if (estatisticasService == null) {
            estatisticasService = new EstatisticasService();
        }
        return estatisticasService;
    }

    public int totalClientes() {
        return estatisticasDAO.totalClientes();
    }

    public int veiculosDisponiveis() {
        return estatisticasDAO.loadVeiculosDisponiveis();
    }

    public int veiculosEmUso() {
        return estatisticasDAO.loadTotalVeiculosEmUso();
    }

    public double receitaTotal() {
        return estatisticasDAO.loadReceitaTotal();
    }
}
