package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.AluguerDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.CondutorDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.PedidoDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.VeiculoDAO;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.CondutorModel;
import aluguer.veiculos.aluguerveiculos.Models.PedidoModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class AluguerService {
    private static AluguerService aluguerService;
    private DAO<AluguerModel> aluguerModelDAO;
    private DAO<VeiculoModel> veiculoModelDAO;
    private DAO<CondutorModel> condutorModelDAO;
    private DAO<PedidoModel> pedidoModelDAO;

    private AluguerService() {
        aluguerModelDAO = new AluguerDAO();
        veiculoModelDAO = new VeiculoDAO();
        condutorModelDAO = new CondutorDAO();
        pedidoModelDAO = new PedidoDAO();
    }

    public static synchronized AluguerService getInstance() {
        if (aluguerService == null) {
            aluguerService = new AluguerService();
        }
        return aluguerService;
    }

    public List<AluguerModel> listaAluguers() {
        return aluguerModelDAO.getAll();
    }

    public boolean atualizarAluguer(int id_aluguer,
                                    int id_veiculo,
                                    int id_condutor,
                                    int id_pedido,
                                    LocalDate data_inicio,
                                    LocalDate data_fim,
                                    LocalDate data_entrega,
                                    int desconto) {

        VeiculoModel veiculoModel = veiculoModelDAO.getById(id_veiculo);
        CondutorModel condutorModel = condutorModelDAO.getById(id_condutor);
        PedidoModel pedidoModel = pedidoModelDAO.getById(id_pedido);

        if (veiculoModel == null) {
            AlertMaker.errorAlert(null, "Erro", "Veiculo não encontrado");
            return false;
        }
        if (condutorModel == null) {
            AlertMaker.errorAlert(null, "Erro", "Condutor não encontrado");
            return false;
        }

        AluguerModel aluguerModel = new AluguerModel(id_aluguer, veiculoModel, condutorModel, pedidoModel, data_inicio, data_fim, data_entrega, desconto);

        return aluguerModelDAO.update(aluguerModel);
    }

    public boolean adicionarAluguer(int id_veiculo,
                                    int id_condutor,
                                    int id_pedido,
                                    LocalDate data_inicio,
                                    LocalDate data_fim,
                                    int desconto) {

        VeiculoModel veiculoModel = veiculoModelDAO.getById(id_veiculo);
        CondutorModel condutorModel = condutorModelDAO.getById(id_condutor);
        PedidoModel pedidoModel = pedidoModelDAO.getById(id_pedido);

        if (veiculoModel == null) {
            AlertMaker.errorAlert(null, "Erro", "Veiculo não encontrado");
            return false;
        }
        if (condutorModel == null) {
            AlertMaker.errorAlert(null, "Erro", "Condutor não encontrado");
            return false;
        }

        AluguerModel aluguerModel = new AluguerModel(veiculoModel, condutorModel, pedidoModel, data_inicio, data_fim, null, desconto);

        return aluguerModelDAO.insert(aluguerModel);
    }

    public List<AluguerModel> pesquisarAluguerID(int id_aluguer) {
        List<AluguerModel> lista = new ArrayList<>();
        lista.add(aluguerModelDAO.getById(id_aluguer));
        return lista;
    }

    public boolean eliminarAluguer(int id_aluguer) {
        return aluguerModelDAO.delete(new AluguerModel(id_aluguer));
    }
}
