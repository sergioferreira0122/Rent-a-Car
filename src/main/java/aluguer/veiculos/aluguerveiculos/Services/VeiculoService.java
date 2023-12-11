package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IMarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IModeloDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IVerificacoesDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.MarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ModeloDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.VeiculoDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.VerificacoesDAO;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;

import java.util.ArrayList;
import java.util.List;

public final class VeiculoService {
    private static VeiculoService veiculoService;
    private DAO<VeiculoModel> veiculoModelDAO;
    private IVerificacoesDAO verificacoesDAO;
    private IModeloDAO modeloDAO;
    private IMarcaDAO marcaDAO;

    private VeiculoService() {
        veiculoModelDAO = new VeiculoDAO();
        verificacoesDAO = new VerificacoesDAO();
        modeloDAO = new ModeloDAO();
        marcaDAO = new MarcaDAO();
    }

    public static synchronized VeiculoService getInstance() {
        if (veiculoService == null) {
            veiculoService = new VeiculoService();
        }
        return veiculoService;
    }

    public List<VeiculoModel> listaVeiculos() {
        return veiculoModelDAO.getAll();
    }

    public boolean atualizarVeiculo(int id_veiculo,
                                    int id_modelo,
                                    String matricula,
                                    double preco) {

        ModeloModel modeloModel = new ModeloModel(id_modelo);

        VeiculoModel veiculoModel = new VeiculoModel(id_veiculo, modeloModel, matricula, preco);

        return veiculoModelDAO.update(veiculoModel);
    }

    public boolean adicionarVeiculo(int id_modelo,
                                    String matricula,
                                    double preco) {

        ModeloModel modeloModel = new ModeloModel(id_modelo);

        VeiculoModel veiculoModel = new VeiculoModel(modeloModel, matricula, preco);

        return veiculoModelDAO.insert(veiculoModel);
    }

    public List<VeiculoModel> pesquisarVeiculoID(int id_veiculo) {
        List<VeiculoModel> lista = new ArrayList<>();
        lista.add(veiculoModelDAO.getById(id_veiculo));
        return lista;
    }

    public boolean eliminarVeiculo(int id_veiculo) {
        return veiculoModelDAO.delete(new VeiculoModel(id_veiculo));
    }

    public boolean verificarModeloDeMarca(int id_modelo,
                                          int id_marca) {

        return verificacoesDAO.verificarModeloDeUmaMarca(id_modelo, id_marca);
    }

    public List<ModeloModel> listaModelosDeUmaMarca(int id_marca) {
        return modeloDAO.listaModelosDeUmaMarca(id_marca);
    }

    public MarcaModel marcaDeUmModelo(int id_modelo) {
        return marcaDAO.marcaDeUmModelo(id_modelo).get(0);
    }
}
