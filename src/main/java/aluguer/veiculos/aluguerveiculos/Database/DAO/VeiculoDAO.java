package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements DAO<VeiculoModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<VeiculoModel> getAll() {
        List<VeiculoModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT Veiculo.id_veiculo, Veiculo.matricula, Veiculo.preco, Modelo.id_modelo, Modelo.modelo AS modelo, Marca.marca AS marca, Marca.id_marca AS id_marca
                    FROM Veiculo
                    LEFT JOIN Modelo ON Modelo.id_modelo = Veiculo.id_modelo
                    LEFT JOIN Marca ON Marca.id_marca = Modelo.id_marca
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_veiculo = rs.getInt("id_veiculo");
                String matricula = rs.getString("matricula");
                double preco = rs.getDouble("preco");
                int id_modelo = rs.getInt("id_modelo");
                String modelo = rs.getString("modelo");
                int id_marca = rs.getInt("id_marca");
                String marca = rs.getString("marca");

                MarcaModel marcaModel = new MarcaModel(id_marca, marca);
                ModeloModel modeloModel = new ModeloModel(id_modelo, marcaModel, modelo);

                VeiculoModel veiculoModel = new VeiculoModel(id_veiculo, modeloModel, matricula, preco);

                lista.add(veiculoModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public VeiculoModel getById(int id) {
        try {
            String query = """
                    SELECT id_veiculo, matricula, preco, Modelo.modelo AS nome_modelo, Modelo.id_modelo AS id_modelo, Marca.id_marca AS id_marca, Marca.marca AS nome_marca
                    FROM Veiculo
                    INNER JOIN Modelo ON Modelo.id_modelo = Veiculo.id_modelo
                    INNER JOIN Marca ON Modelo.id_marca = Marca.id_marca
                    WHERE id_veiculo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_veiculo = rs.getInt("id_veiculo");
                String matricula = rs.getString("matricula");
                double preco = rs.getDouble("preco");
                int id_modelo = rs.getInt("id_modelo");
                String modelo = rs.getString("nome_modelo");
                int id_marca = rs.getInt("id_marca");
                String marca = rs.getString("nome_marca");

                MarcaModel marcaModel = new MarcaModel(id_marca, marca);
                ModeloModel modeloModel = new ModeloModel(id_modelo, marcaModel, modelo);

                return new VeiculoModel(id_veiculo, modeloModel, matricula, preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(VeiculoModel dados) {
        try {
            String query = """
                    EXEC AdicionarVeiculo @idModelo = ?, @matricula = ?, @preco = ?;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getModeloModel().getId_modelo());
            statement.setString(2, dados.getMatricula());
            statement.setDouble(3, dados.getPreco());

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
    public boolean update(VeiculoModel dados) {
        try {
            String query = """
                    UPDATE Veiculo
                    SET id_modelo = ?, matricula = ?, preco = ?
                    WHERE id_veiculo = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getModeloModel().getId_modelo());
            statement.setString(2, dados.getMatricula());
            statement.setDouble(3, dados.getPreco());
            statement.setInt(4, dados.getId_veiculo());

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
    public boolean delete(VeiculoModel dados) {
        try {
            String query = """
                    EXEC EliminarVeiculo @idVeiculo = ?;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_veiculo());

            return databaseHandler.execAction(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            databaseHandler.closeConn();
        }
    }
}
