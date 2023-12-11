package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IServico_AluguerDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ServicoDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Servico_AluguerDAO;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.ServicoModel;
import aluguer.veiculos.aluguerveiculos.Models.Servico_AluguerModel;

import java.util.ArrayList;
import java.util.List;

public final class ServicoService {
    private static ServicoService servicoService;
    private final DAO<ServicoModel> servicoModelDAO;
    private final IServico_AluguerDAO servico_aluguerModelDAO;

    private ServicoService() {
        servicoModelDAO = new ServicoDAO();
        servico_aluguerModelDAO = new Servico_AluguerDAO();
    }

    public static synchronized ServicoService getInstance() {
        if (servicoService == null) {
            servicoService = new ServicoService();
        }
        return servicoService;
    }

    public List<ServicoModel> listaServicos() {
        return servicoModelDAO.getAll();
    }

    public boolean atualizarServico(int id_servico,
                                    String servico,
                                    double preco,
                                    String descricao) {

        ServicoModel servicoModel = new ServicoModel(id_servico, servico, preco, descricao);

        return servicoModelDAO.update(servicoModel);
    }

    public boolean adicionarServico(String servico,
                                    double preco,
                                    String descricao) {

        ServicoModel servicoModel = new ServicoModel(servico, preco, descricao);

        return servicoModelDAO.insert(servicoModel);
    }

    public List<ServicoModel> pesquisarServicoID(int id_servico) {
        List<ServicoModel> lista = new ArrayList<>();
        lista.add(servicoModelDAO.getById(id_servico));
        return lista;
    }

    public boolean eliminarServico(int id_servico) {
        return servicoModelDAO.delete(new ServicoModel(id_servico));
    }

    // servico_aluguer

    public List<Servico_AluguerModel> listaServicos_Aluguers() {
        return servico_aluguerModelDAO.getAll();
    }

    public List<Servico_AluguerModel> listaServicosDeUmAluguer(int id_aluguer) {
        return servico_aluguerModelDAO.getById(id_aluguer);
    }

    public boolean adicionarServico_Aluguer(int id_aluguer,
                                            int id_servico,
                                            double desconto) {

        AluguerModel aluguerModel = new AluguerModel(id_aluguer);
        ServicoModel servicoModel = new ServicoModel(id_servico);

        Servico_AluguerModel servicoAluguerModel = new Servico_AluguerModel(aluguerModel, servicoModel, desconto);

        return servico_aluguerModelDAO.insert(servicoAluguerModel);
    }

    public boolean atualizarServico_Aluguer(int id_aluguer,
                                            int id_servico,
                                            double desconto) {

        AluguerModel aluguerModel = new AluguerModel(id_aluguer);
        ServicoModel servicoModel = new ServicoModel(id_servico);

        Servico_AluguerModel servicoAluguerModel = new Servico_AluguerModel(aluguerModel, servicoModel, desconto);

        return servico_aluguerModelDAO.update(servicoAluguerModel);
    }

    public boolean eliminarServico_Aluguer(int id_aluguer,
                                           int id_servico) {

        AluguerModel aluguerModel = new AluguerModel(id_aluguer);
        ServicoModel servicoModel = new ServicoModel(id_servico);

        return servico_aluguerModelDAO.delete(new Servico_AluguerModel(aluguerModel, servicoModel));
    }
}
