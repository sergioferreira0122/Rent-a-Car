package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.ServicoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO implements DAO<ServicoModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<ServicoModel> getAll() {
        List<ServicoModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Servico
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_servico = rs.getInt("id_servico");
                String servico = rs.getString("servico");
                double preco = rs.getDouble("preco");
                String descricao = rs.getString("descricao");

                ServicoModel servicoModel = new ServicoModel(id_servico, servico, preco, descricao);

                lista.add(servicoModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public ServicoModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Servico
                    WHERE id_servico = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                String servico = rs.getString("servico");
                double preco = rs.getDouble("preco");
                String descricao = rs.getString("descricao");

                return new ServicoModel(id, servico, preco, descricao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(ServicoModel dados) {
        try {
            String query = """
                    INSERT INTO Servico
                    (servico, preco, descricao)
                    VALUES (?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getServico());
            statement.setDouble(2, dados.getPreco());
            statement.setString(3, dados.getDescricao());

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
    public boolean update(ServicoModel dados) {
        try {
            String query = """
                    UPDATE Servico
                    SET servico = ?, preco = ?, descricao = ?
                    WHERE id_servico = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getServico());
            statement.setDouble(2, dados.getPreco());
            statement.setString(3, dados.getDescricao());
            statement.setInt(4, dados.getId_servico());

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
    public boolean delete(ServicoModel dados) {
        try {
            String query = """
                    DELETE FROM Servico
                    WHERE id_servico = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_servico());

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
