package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.CondutorModel;
import aluguer.veiculos.aluguerveiculos.Models.PedidoModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AluguerDAO implements DAO<AluguerModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<AluguerModel> getAll() {
        List<AluguerModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Aluguer
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_aluguer = rs.getInt("id_aluguer");
                int id_veiculo = rs.getInt("id_veiculo");
                int id_condutor = rs.getInt("id_condutor");
                int id_pedido = rs.getInt("id_pedido");
                LocalDate data_inicio = LocalDate.parse(rs.getString("data_inicio"));
                LocalDate data_fim = LocalDate.parse(rs.getString("data_fim"));
                String data_entrega = rs.getString("data_entrega");
                LocalDate data_entrega_localdate = null;
                if (!(data_entrega == null)) {
                    data_entrega_localdate = LocalDate.parse(data_entrega);
                }

                int desconto = rs.getInt("desconto");

                CondutorModel condutorModel = new CondutorModel(id_condutor);
                VeiculoModel veiculoModel = new VeiculoModel(id_veiculo);
                PedidoModel pedidoModel = new PedidoModel(id_pedido);

                AluguerModel aluguerModel = new AluguerModel(id_aluguer, veiculoModel, condutorModel, pedidoModel, data_inicio, data_fim, data_entrega_localdate, desconto);

                lista.add(aluguerModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public AluguerModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Aluguer
                    WHERE id_aluguer = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_veiculo = rs.getInt("id_veiculo");
                int id_condutor = rs.getInt("id_condutor");
                int id_pedido = rs.getInt("id_pedido");
                LocalDate data_inicio = LocalDate.parse(rs.getString("data_inicio"));
                LocalDate data_fim = LocalDate.parse(rs.getString("data_fim"));
                String data_entrega = rs.getString("data_entrega");
                LocalDate data_entrega_localdate = null;
                if (!(data_entrega == null)) {
                    data_entrega_localdate = LocalDate.parse(data_entrega);
                }
                int desconto = rs.getInt("desconto");

                CondutorModel condutorModel = new CondutorModel(id_condutor);
                VeiculoModel veiculoModel = new VeiculoModel(id_veiculo);
                PedidoModel pedidoModel = new PedidoModel(id_pedido);

                return new AluguerModel(id, veiculoModel, condutorModel, pedidoModel, data_inicio, data_fim, data_entrega_localdate, desconto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(AluguerModel dados) {
        try {
            String query = """
                    EXEC AlugarVeiculo @idVeiculo = ?, @idCondutor = ?, @idPedido = ?, @data_inicio = ?, @data_fim = ?, @desconto = ?;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getVeiculoModel().getId_veiculo());
            statement.setInt(2, dados.getCondutorModel().getId_condutor());
            statement.setInt(3, dados.getPedidoModel().getId_pedido());
            statement.setString(4, dados.getData_inicio().toString());
            statement.setString(5, dados.getData_fim().toString());
            statement.setInt(6, dados.getDesconto());

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
    public boolean update(AluguerModel dados) {
        try {
            String query = """
                    UPDATE Aluguer
                    SET id_veiculo = ?, id_condutor = ?, id_pedido = ?, data_inicio = ?, data_fim = ?, data_entrega = ?, desconto = ?
                    WHERE id_aluguer = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getVeiculoModel().getId_veiculo());
            statement.setInt(2, dados.getCondutorModel().getId_condutor());
            statement.setInt(3, dados.getPedidoModel().getId_pedido());
            statement.setString(4, dados.getData_inicio().toString());
            statement.setString(5, dados.getData_fim().toString());
            if (dados.getData_entrega().isPresent()) {
                statement.setString(6, dados.getData_entrega().get().toString());
            } else {
                statement.setString(6, null);
            }
            statement.setInt(7, dados.getDesconto());
            statement.setInt(8, dados.getId_aluguer());

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
    public boolean delete(AluguerModel dados) {
        try {
            String query = """
                    DELETE FROM Aluguer
                    WHERE id_aluguer = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_aluguer());

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
