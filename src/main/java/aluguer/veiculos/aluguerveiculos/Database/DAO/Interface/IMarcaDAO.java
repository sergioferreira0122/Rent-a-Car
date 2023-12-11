package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;

import java.util.List;

public interface IMarcaDAO extends DAO<MarcaModel> {
    public List<MarcaModel> marcaDeUmModelo(int id_modelo);
}
