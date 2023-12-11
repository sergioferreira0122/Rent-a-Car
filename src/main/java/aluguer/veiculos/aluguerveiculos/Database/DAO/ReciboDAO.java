package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.ReciboModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReciboDAO implements DAO<ReciboModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<ReciboModel> getAll() {
        List<ReciboModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Recibo
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_recibo = rs.getInt("id_recibo");
                LocalDate data = LocalDate.parse(rs.getString("data"));
                double valor = rs.getDouble("preco");

                ReciboModel reciboModel = new ReciboModel(id_recibo, data, valor);

                lista.add(reciboModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public ReciboModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Recibo
                    WHERE id_recibo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_recibo = rs.getInt("id_recibo");
                LocalDate data = LocalDate.parse(rs.getString("data"));
                double valor = rs.getDouble("preco");

                return new ReciboModel(id_recibo, data, valor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(ReciboModel dados) {
        try {
            String query = """
                    INSERT INTO Recibo
                    (data, preco)
                    VALUES (?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, String.valueOf(dados.getData()));
            statement.setDouble(2, dados.getValor());

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
    public boolean update(ReciboModel dados) {
        try {
            String query = """
                    UPDATE Recibo
                    SET data = ?, preco = ?
                    WHERE id_recibo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, String.valueOf(dados.getData()));
            statement.setDouble(2, dados.getValor());
            statement.setInt(3, dados.getId_recibo());

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
    public boolean delete(ReciboModel dados) {
        try {
            String query = """
                    DELETE FROM Recibo
                    WHERE id_recibo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_recibo());

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
