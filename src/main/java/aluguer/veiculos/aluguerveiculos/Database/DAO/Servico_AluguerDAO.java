package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IServico_AluguerDAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.ServicoModel;
import aluguer.veiculos.aluguerveiculos.Models.Servico_AluguerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Servico_AluguerDAO implements IServico_AluguerDAO {
    private DatabaseHandler databaseHandler;

    @Override
    public List<Servico_AluguerModel> getAll() {
        List<Servico_AluguerModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT id_aluguer, Servico.id_servico AS id_servico, desconto, servico, preco, descricao
                    FROM Servico_Aluguer
                    INNER JOIN Servico ON Servico.id_servico = Servico_Aluguer.id_servico
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_servico = rs.getInt("id_servico");
                int id_aluguer = rs.getInt("id_aluguer");
                String servico = rs.getString("servico");
                double preco = rs.getDouble("preco");
                double desconto = rs.getDouble("desconto");
                String descricao = rs.getString("descricao");

                AluguerModel aluguerModel = new AluguerModel(id_aluguer);
                ServicoModel servicoModel = new ServicoModel(id_servico);

                Servico_AluguerModel servicoAluguerModel = new Servico_AluguerModel(aluguerModel, servicoModel, desconto, servico, preco, descricao);

                lista.add(servicoAluguerModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public List<Servico_AluguerModel> getById(int id) {
        List<Servico_AluguerModel> lista = new ArrayList<>();
        try {
            String query = """
                    SELECT id_aluguer, Servico.id_servico AS id_servico, desconto, servico, preco, descricao\s
                    FROM Servico_Aluguer
                    INNER JOIN Servico ON Servico.id_servico = Servico_Aluguer.id_servico
                    WHERE id_aluguer = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_servico = rs.getInt("id_servico");
                int id_aluguer = rs.getInt("id_aluguer");
                String servico = rs.getString("servico");
                double preco = rs.getDouble("preco");
                double desconto = rs.getDouble("desconto");
                String descricao = rs.getString("descricao");

                AluguerModel aluguerModel = new AluguerModel(id_aluguer);
                ServicoModel servicoModel = new ServicoModel(id_servico);

                Servico_AluguerModel servicoAluguerModel = new Servico_AluguerModel(aluguerModel, servicoModel, desconto, servico, preco, descricao);

                lista.add(servicoAluguerModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return lista;
    }

    @Override
    public boolean insert(Servico_AluguerModel dados) {
        try {
            String query = """
                    INSERT INTO Servico_Aluguer
                    (id_servico, id_aluguer, desconto)
                    VALUES (?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getServicoModel().getId_servico());
            statement.setInt(2, dados.getAluguerModel().getId_aluguer());
            statement.setDouble(3, dados.getDesconto());

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
    public boolean update(Servico_AluguerModel dados) {
        try {
            String query = """
                    UPDATE Servico_Aluguer
                    SET desconto = ?
                    WHERE id_aluguer = ? AND id_servico = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setDouble(1, dados.getDesconto());
            statement.setInt(2, dados.getAluguerModel().getId_aluguer());
            statement.setInt(3, dados.getServicoModel().getId_servico());

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
    public boolean delete(Servico_AluguerModel dados) {
        try {
            String query = """
                    DELETE FROM Servico_Aluguer
                    WHERE id_aluguer = ? AND id_servico = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getAluguerModel().getId_aluguer());
            statement.setInt(2, dados.getServicoModel().getId_servico());

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
