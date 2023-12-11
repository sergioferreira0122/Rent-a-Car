package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.ReparacaoModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReparacaoDAO implements DAO<ReparacaoModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<ReparacaoModel> getAll() {
        List<ReparacaoModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM CustosReparacaoVeiculo
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_reparacao = rs.getInt("id_reparacao");
                int id_veiculo = rs.getInt("id_veiculo");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                LocalDate data = LocalDate.parse(rs.getString("data"));

                VeiculoModel veiculoModel = new VeiculoModel(id_veiculo);

                ReparacaoModel reparacaoModel = new ReparacaoModel(id_reparacao, veiculoModel, preco, descricao, data);

                lista.add(reparacaoModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public ReparacaoModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM CustosReparacaoVeiculo
                    WHERE id_reparacao = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_reparacao = rs.getInt("id_reparacao");
                int id_veiculo = rs.getInt("id_veiculo");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                LocalDate data = LocalDate.parse(rs.getString("data"));

                VeiculoModel veiculoModel = new VeiculoModel(id_veiculo);

                return new ReparacaoModel(id_reparacao, veiculoModel, preco, descricao, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(ReparacaoModel dados) {
        try {
            String query = """
                    INSERT INTO CustosReparacaoVeiculo
                    (id_veiculo, preco, descricao, data)
                    VALUES (?, ?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getVeiculoModel().getId_veiculo());
            statement.setDouble(2, dados.getPreco());
            statement.setString(3, dados.getDescricao());
            statement.setString(4, String.valueOf(dados.getData()));

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
    public boolean update(ReparacaoModel dados) {
        try {
            String query = """
                    UPDATE CustosReparacaoVeiculo
                    SET id_veiculo = ?, preco = ?, descricao = ?, data = ?
                    WHERE id_reparacao = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getVeiculoModel().getId_veiculo());
            statement.setDouble(2, dados.getPreco());
            statement.setString(3, dados.getDescricao());
            statement.setString(4, String.valueOf(dados.getData()));
            statement.setInt(5, dados.getId_reparacao());

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
    public boolean delete(ReparacaoModel dados) {
        try {
            String query = """
                    DELETE FROM CustosReparacaoVeiculo
                    WHERE id_reparacao = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_reparacao());

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
