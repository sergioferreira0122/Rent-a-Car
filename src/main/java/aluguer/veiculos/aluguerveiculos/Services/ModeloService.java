package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IMarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IModeloDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.MarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ModeloDAO;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;

import java.util.ArrayList;
import java.util.List;

public final class ModeloService {
    private static ModeloService modeloService;
    private IModeloDAO modeloModelDAO;
    private IMarcaDAO marcaModelDAO;

    private ModeloService() {
        modeloModelDAO = new ModeloDAO();
        marcaModelDAO = new MarcaDAO();
    }

    public static synchronized ModeloService getInstance() {
        if (modeloService == null) {
            modeloService = new ModeloService();
        }
        return modeloService;
    }

    public List<ModeloModel> listaModelos() {
        return modeloModelDAO.getAll();
    }

    public boolean atualizarModelo(int id_modelo,
                                   int id_marca,
                                   String modelo) {

        MarcaModel marcaModel = marcaModelDAO.getById(id_marca);

        ModeloModel modeloModel = new ModeloModel(id_modelo, marcaModel, modelo);

        return modeloModelDAO.update(modeloModel);
    }

    public boolean adicionarModelo(int id_marca,
                                   String modelo) {

        MarcaModel marcaModel = marcaModelDAO.getById(id_marca);

        ModeloModel modeloModel = new ModeloModel(marcaModel, modelo);

        return modeloModelDAO.insert(modeloModel);
    }

    public List<ModeloModel> pesquisarModeloID(int id_modelo) {
        List<ModeloModel> lista = new ArrayList<>();
        lista.add(modeloModelDAO.getById(id_modelo));
        return lista;
    }

    public boolean eliminarModelo(int id_modelo) {
        return modeloModelDAO.delete(new ModeloModel(id_modelo));
    }
}
