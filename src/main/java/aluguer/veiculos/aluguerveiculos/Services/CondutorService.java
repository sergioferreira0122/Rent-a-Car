package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.CondutorDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Models.CondutorModel;

import java.util.ArrayList;
import java.util.List;

public final class CondutorService {
    private static CondutorService clienteService;
    private DAO<CondutorModel> condutorModelDAO;

    private CondutorService() {
        condutorModelDAO = new CondutorDAO();
    }

    public static synchronized CondutorService getInstance() {
        if (clienteService == null) {
            clienteService = new CondutorService();
        }
        return clienteService;
    }

    public List<CondutorModel> listaCondutores() {
        return condutorModelDAO.getAll();
    }

    public boolean atualizarCondutor(int id_condutor,
                                     String nome,
                                     String morada,
                                     String cartao_cidadao,
                                     String carta_conducao) {

        CondutorModel condutorModel = new CondutorModel(id_condutor, nome, morada, cartao_cidadao, carta_conducao);

        return condutorModelDAO.update(condutorModel);
    }

    public boolean adicionarCondutor(String nome,
                                     String morada,
                                     String cartao_cidadao,
                                     String carta_conducao) {

        CondutorModel condutorModel = new CondutorModel(nome, morada, cartao_cidadao, carta_conducao);

        return condutorModelDAO.insert(condutorModel);
    }

    public List<CondutorModel> pesquisarCondutorID(int id_condutor) {
        List<CondutorModel> lista = new ArrayList<>();
        lista.add(condutorModelDAO.getById(id_condutor));
        return lista;
    }

    public boolean eliminarCondutor(int id_condutor) {
        return condutorModelDAO.delete(new CondutorModel(id_condutor));
    }
}
