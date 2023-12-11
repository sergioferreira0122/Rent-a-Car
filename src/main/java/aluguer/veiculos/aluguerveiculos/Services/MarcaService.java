package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IMarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.MarcaDAO;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;

import java.util.ArrayList;
import java.util.List;

public final class MarcaService {
    private static MarcaService marcaService;
    private IMarcaDAO marcaModelDAO;

    private MarcaService() {
        marcaModelDAO = new MarcaDAO();
    }

    public static synchronized MarcaService getInstance() {
        if (marcaService == null) {
            marcaService = new MarcaService();
        }
        return marcaService;
    }

    public List<MarcaModel> listaMarcas() {
        return marcaModelDAO.getAll();
    }

    public boolean atualizarMarca(int id_marca,
                                  String marca) {

        MarcaModel marcaModel = new MarcaModel(id_marca, marca);

        return marcaModelDAO.update(marcaModel);
    }

    public boolean adicionarMarca(String marca) {

        MarcaModel marcaModel = new MarcaModel(marca);

        return marcaModelDAO.insert(marcaModel);
    }

    public List<MarcaModel> pesquisarMarcaID(int id_marca) {
        List<MarcaModel> lista = new ArrayList<>();
        lista.add(marcaModelDAO.getById(id_marca));
        return lista;
    }

    public boolean eliminarMarca(int id_marca) {
        return marcaModelDAO.delete(new MarcaModel(id_marca));
    }
}
