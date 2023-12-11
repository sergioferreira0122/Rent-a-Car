package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IMarcaDAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO implements IMarcaDAO {
    private DatabaseHandler databaseHandler;

    @Override
    public List<MarcaModel> getAll() {
        List<MarcaModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Marca
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_marca");
                String marca = rs.getString("marca");

                MarcaModel marcaModel = new MarcaModel(id, marca);

                lista.add(marcaModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public MarcaModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Marca
                    WHERE id_marca = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                String marca = rs.getString("marca");

                return new MarcaModel(id, marca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(MarcaModel dados) {
        try {
            String query = """
                    INSERT INTO Marca
                    (marca)
                    VALUES (?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getMarca());

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
    public boolean update(MarcaModel dados) {
        try {
            String query = """
                    UPDATE Marca
                    SET marca = ?
                    WHERE id_marca = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getMarca());
            statement.setInt(2, dados.getId_marca());

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
    public boolean delete(MarcaModel dados) {
        try {
            String query = """
                    DELETE FROM Marca
                    WHERE id_marca = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_marca());

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
    public List<MarcaModel> marcaDeUmModelo(int id_modelo) {
        List<MarcaModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT * FROM Marca
                    INNER JOIN Modelo ON Modelo.id_marca = Marca.id_marca
                    WHERE id_modelo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            statement.setInt(1, id_modelo);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_marca");
                String marca = rs.getString("marca");

                MarcaModel marcaModel = new MarcaModel(id, marca);

                lista.add(marcaModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }
}

