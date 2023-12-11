package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;

import java.util.List;

public interface IModeloDAO extends DAO<ModeloModel> {

    public List<ModeloModel> listaModelosDeUmaMarca(int id_marca);
}
