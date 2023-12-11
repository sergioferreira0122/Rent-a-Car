package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ReparacaoDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.VeiculoDAO;
import aluguer.veiculos.aluguerveiculos.Models.ReparacaoModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ReparacaoService {
    private static ReparacaoService reparacaoService;
    private DAO<ReparacaoModel> reparacaoModelDAO;
    private DAO<VeiculoModel> veiculoModelDAO;

    private ReparacaoService() {
        reparacaoModelDAO = new ReparacaoDAO();
        veiculoModelDAO = new VeiculoDAO();
    }

    public static synchronized ReparacaoService getInstance() {
        if (reparacaoService == null) {
            reparacaoService = new ReparacaoService();
        }
        return reparacaoService;
    }

    public List<ReparacaoModel> listaReparacoes() {
        return reparacaoModelDAO.getAll();
    }

    public boolean atualizarReparacao(int id_reparacao,
                                      int id_veiculo,
                                      double preco,
                                      String descricao,
                                      LocalDate data) {

        VeiculoModel veiculoModel = veiculoModelDAO.getById(id_veiculo);

        ReparacaoModel reparacaoModel = new ReparacaoModel(id_reparacao, veiculoModel, preco, descricao, data);

        return reparacaoModelDAO.update(reparacaoModel);
    }

    public boolean adicionarReparacao(int id_veiculo,
                                      double preco,
                                      String descricao,
                                      LocalDate data) {

        VeiculoModel veiculoModel = veiculoModelDAO.getById(id_veiculo);

        ReparacaoModel reparacaoModel = new ReparacaoModel(veiculoModel, preco, descricao, data);

        return reparacaoModelDAO.insert(reparacaoModel);
    }

    public List<ReparacaoModel> pesquisarReparacaoID(int id_reparacao) {
        List<ReparacaoModel> lista = new ArrayList<>();
        lista.add(reparacaoModelDAO.getById(id_reparacao));
        return lista;
    }

    public boolean eliminarModelo(int id_reparacao) {
        return reparacaoModelDAO.delete(new ReparacaoModel(id_reparacao));
    }

}
