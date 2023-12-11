package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.CondutorModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CondutorDAO implements DAO<CondutorModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<CondutorModel> getAll() {
        List<CondutorModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Condutor
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_condutor = rs.getInt("id_condutor");
                String nome = rs.getString("nome");
                String morada = rs.getString("morada");
                String cc = rs.getString("cc");
                String nr_carta = rs.getString("nr_carta");

                CondutorModel condutorModel = new CondutorModel(id_condutor, nome, morada, cc, nr_carta);

                lista.add(condutorModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public CondutorModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Condutor
                    WHERE id_condutor = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String morada = rs.getString("morada");
                String cc = rs.getString("cc");
                String nr_carta = rs.getString("nr_carta");

                return new CondutorModel(id, nome, morada, cc, nr_carta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(CondutorModel dados) {
        try {
            String query = """
                    INSERT INTO Condutor
                    (nome, morada, cc, nr_carta)
                    VALUES (?, ?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getNome());
            statement.setString(2, dados.getMorada());
            statement.setString(3, dados.getCc());
            statement.setString(4, dados.getNr_carta());

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
    public boolean update(CondutorModel dados) {
        try {
            String query = """
                    UPDATE Condutor
                    SET nome = ?, morada = ?, cc = ?, nr_carta = ?
                    WHERE id_condutor = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getNome());
            statement.setString(2, dados.getMorada());
            statement.setString(3, dados.getCc());
            statement.setString(4, dados.getNr_carta());
            statement.setInt(5, dados.getId_condutor());

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
    public boolean delete(CondutorModel dados) {
        try {
            String query = """
                    DELETE FROM Condutor
                    WHERE id_condutor = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_condutor());

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
}
