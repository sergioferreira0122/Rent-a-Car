package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.ClienteModel;
import aluguer.veiculos.aluguerveiculos.Models.FaturaModel;
import aluguer.veiculos.aluguerveiculos.Models.PedidoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements DAO<PedidoModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<PedidoModel> getAll() {
        List<PedidoModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT id_pedido, id_fatura, id_cliente, Pedido.data AS data_pedido, preco
                    FROM Pedido
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_pedido = rs.getInt("id_pedido");
                int id_fatura = rs.getInt("id_fatura");
                int id_cliente = rs.getInt("id_cliente");
                LocalDate data_pedido = LocalDate.parse(rs.getString("data_pedido"));
                double valor = rs.getDouble("preco");

                ClienteModel clienteModel = new ClienteModel(id_cliente);
                FaturaModel faturaModel = new FaturaModel(id_fatura);

                PedidoModel pedidoModel = new PedidoModel(id_pedido, faturaModel, clienteModel, data_pedido, valor);

                lista.add(pedidoModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public PedidoModel getById(int id) {
        try {
            String query = """
                    SELECT id_pedido, id_fatura, id_cliente, Pedido.data AS data_pedido, preco
                    FROM Pedido
                    WHERE id_pedido = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_pedido = rs.getInt("id_pedido");
                int id_fatura = rs.getInt("id_fatura");
                int id_cliente = rs.getInt("id_cliente");
                LocalDate data_pedido = LocalDate.parse(rs.getString("data_pedido"));
                double valor = rs.getDouble("preco");

                ClienteModel clienteModel = new ClienteModel(id_cliente);
                FaturaModel faturaModel = new FaturaModel(id_fatura);

                return new PedidoModel(id_pedido, faturaModel, clienteModel, data_pedido, valor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(PedidoModel dados) {
        try {
            String query = """
                    INSERT INTO Pedido
                    (id_fatura, id_cliente, data)
                    VALUES (?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            if (dados.getFaturaModel().isPresent()) {
                statement.setInt(1, dados.getFaturaModel().get().getId_fatura());
            } else {
                statement.setString(1, null);

            }

            statement.setInt(2, dados.getClienteModel().getId_cliente());
            statement.setString(3, String.valueOf(dados.getData()));

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
    public boolean update(PedidoModel dados) {
        try {
            String query = """
                    UPDATE Pedido
                    SET id_fatura = ?, id_cliente = ?, data = ?
                    WHERE id_pedido = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            if (dados.getFaturaModel().isPresent()) {
                statement.setInt(1, dados.getFaturaModel().get().getId_fatura());
            } else {
                statement.setString(1, null);
            }

            statement.setInt(2, dados.getClienteModel().getId_cliente());
            statement.setString(3, String.valueOf(dados.getData()));
            statement.setDouble(4, dados.getId_pedido());

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
    public boolean delete(PedidoModel dados) {
        try {
            String query = """
                    DELETE FROM Pedido
                    WHERE id_pedido = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_pedido());

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
