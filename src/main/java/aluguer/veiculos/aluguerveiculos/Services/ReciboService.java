package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ReciboDAO;
import aluguer.veiculos.aluguerveiculos.Models.ReciboModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ReciboService {
    private static ReciboService reciboService;
    private DAO<ReciboModel> reciboModelDAO;

    private ReciboService() {
        reciboModelDAO = new ReciboDAO();
    }

    public static synchronized ReciboService getInstance() {
        if (reciboService == null) {
            reciboService = new ReciboService();
        }
        return reciboService;
    }

    public List<ReciboModel> listaRecibos() {
        return reciboModelDAO.getAll();
    }

    public boolean atualizarRecibo(int id_recibo,
                                   LocalDate data,
                                   double valor) {

        ReciboModel reciboModel = new ReciboModel(id_recibo, data, valor);

        return reciboModelDAO.update(reciboModel);
    }

    public boolean adicionarRecibo(LocalDate data,
                                   double valor) {

        ReciboModel reciboModel = new ReciboModel(data, valor);

        return reciboModelDAO.insert(reciboModel);
    }

    public List<ReciboModel> pesquisarReciboID(int id_recibo) {
        List<ReciboModel> lista = new ArrayList<>();
        lista.add(reciboModelDAO.getById(id_recibo));
        return lista;
    }

    public boolean eliminarRecibo(int id_recibo) {
        return reciboModelDAO.delete(new ReciboModel(id_recibo));
    }
}
