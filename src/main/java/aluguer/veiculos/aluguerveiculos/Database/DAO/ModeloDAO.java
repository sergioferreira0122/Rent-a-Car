package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IModeloDAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO implements IModeloDAO {
    private DatabaseHandler databaseHandler;

    @Override
    public List<ModeloModel> getAll() {
        List<ModeloModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Modelo
                    INNER JOIN Marca ON Modelo.id_marca = Marca.id_marca
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_modelo = rs.getInt("id_modelo");
                int id_marca = rs.getInt("id_marca");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");

                MarcaModel marcaModel = new MarcaModel(id_marca, marca);

                ModeloModel modeloModel = new ModeloModel(id_modelo, marcaModel, modelo);

                lista.add(modeloModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public ModeloModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Modelo
                    INNER JOIN Marca ON Modelo.id_marca = Marca.id_marca
                    WHERE id_modelo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_modelo = rs.getInt("id_modelo");
                int id_marca = rs.getInt("id_marca");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");

                MarcaModel marcaModel = new MarcaModel(id_marca, marca);

                return new ModeloModel(id_modelo, marcaModel, modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(ModeloModel dados) {
        try {
            String query = """
                    INSERT INTO Modelo
                    (modelo, id_marca)
                    VALUES (?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getModelo());
            statement.setInt(2, dados.getMarcaModel().getId_marca());

            if (databaseHandler.execAction(statement)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public boolean update(ModeloModel dados) {
        try {
            String query = """
                    UPDATE Model
                    SET modelo = ?, id_marca = ?
                    WHERE id_modelo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getModelo());
            statement.setInt(2, dados.getMarcaModel().getId_marca());
            statement.setInt(3, dados.getId_modelo());

            if (databaseHandler.execAction(statement)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public boolean delete(ModeloModel dados) {
        try {
            String query = """
                    DELETE FROM Modelo
                    WHERE id_modelo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_modelo());

            if (databaseHandler.execAction(statement)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public List<ModeloModel> listaModelosDeUmaMarca(int id_marca) {
        List<ModeloModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT * FROM Modelo WHERE Modelo.id_marca = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            statement.setInt(1, id_marca);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_modelo");
                String modelo = rs.getString("modelo");

                ModeloModel modeloModel = new ModeloModel(id, modelo);

                lista.add(modeloModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }
}
