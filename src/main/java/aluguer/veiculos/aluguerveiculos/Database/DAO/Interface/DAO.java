package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

import java.util.List;

public interface DAO<T> {
    public List<T> getAll();

    public T getById(int id);

    public boolean insert(T dados);

    public boolean update(T dados);

    public boolean delete(T dados);
}
