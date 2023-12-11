package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.FaturaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.FuncionarioDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ReciboDAO;
import aluguer.veiculos.aluguerveiculos.Models.FaturaModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ReciboModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class FaturaService {
    private static FaturaService faturaService;
    private DAO<FaturaModel> faturaModelDAO;
    private DAO<FuncionarioModel> funcionarioModelDAO;
    private DAO<ReciboModel> reciboModelDAO;

    private FaturaService() {
        faturaModelDAO = new FaturaDAO();
        funcionarioModelDAO = new FuncionarioDAO();
        reciboModelDAO = new ReciboDAO();
    }

    public static synchronized FaturaService getInstance() {
        if (faturaService == null) {
            faturaService = new FaturaService();
        }
        return faturaService;
    }

    public List<FaturaModel> listaFaturas() {
        return faturaModelDAO.getAll();
    }

    public boolean atualizarFatura(int id_fatura,
                                   int id_funcionario,
                                   int id_recibo,
                                   LocalDate data) {

        FuncionarioModel funcionarioModel = funcionarioModelDAO.getById(id_funcionario);

        ReciboModel reciboModel = null;
        if (id_recibo != 0) {
            reciboModel = reciboModelDAO.getById(id_recibo);
        }

        FaturaModel faturaModel = new FaturaModel(id_fatura, funcionarioModel, reciboModel, data);

        return faturaModelDAO.update(faturaModel);
    }

    public boolean adicionarFatura(int id_funcionario,
                                   int id_recibo,
                                   LocalDate data) {

        FuncionarioModel funcionarioModel = funcionarioModelDAO.getById(id_funcionario);

        ReciboModel reciboModel = null;
        if (id_recibo != 0) {
            reciboModel = reciboModelDAO.getById(id_recibo);
        }

        FaturaModel faturaModel = new FaturaModel(funcionarioModel, reciboModel, data);

        return faturaModelDAO.insert(faturaModel);
    }

    public List<FaturaModel> pesquisarFaturaID(int id_fatura) {
        List<FaturaModel> lista = new ArrayList<>();
        lista.add(faturaModelDAO.getById(id_fatura));
        return lista;
    }

    public boolean eliminarFatura(int id_fatura) {
        return faturaModelDAO.delete(new FaturaModel(id_fatura));
    }
}
