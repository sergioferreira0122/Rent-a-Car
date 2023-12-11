package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Enums.TipoCliente;
import aluguer.veiculos.aluguerveiculos.Enums.TipoConta;
import aluguer.veiculos.aluguerveiculos.Models.ClienteModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements DAO<ClienteModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<ClienteModel> getAll() {
        List<ClienteModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT * FROM Cliente
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String tipo_cliente = rs.getString("tipo_cliente");
                String tipo_conta = rs.getString("tipo_conta");
                String nome = rs.getString("nome");

                ClienteModel clienteModel = new ClienteModel(id, TipoCliente.fromString(tipo_cliente), TipoConta.fromString(tipo_conta), nome);

                lista.add(clienteModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public ClienteModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Cliente
                    WHERE id_cliente = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                String tipo_cliente = rs.getString("tipo_cliente");
                String tipo_conta = rs.getString("tipo_conta");
                String nome = rs.getString("nome");

                return new ClienteModel(id, TipoCliente.fromString(tipo_cliente), TipoConta.fromString(tipo_conta), nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(ClienteModel dados) {
        try {
            String query = """
                    INSERT INTO Cliente
                    (tipo_cliente, tipo_conta, nome)
                    VALUES (?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getTipoCliente().toString());
            statement.setString(2, dados.getTipoConta().toString());
            statement.setString(3, dados.getNome());

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
    public boolean update(ClienteModel dados) {
        try {
            String query = """
                    UPDATE Cliente
                    SET tipo_cliente = ?, tipo_conta = ?, nome = ?
                    WHERE id_cliente = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getTipoCliente().toString());
            statement.setString(2, dados.getTipoConta().toString());
            statement.setString(3, dados.getNome());
            statement.setInt(4, dados.getId_cliente());

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
    public boolean delete(ClienteModel dados) {
        try {
            String query = """
                    DELETE FROM Cliente
                    WHERE id_cliente = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_cliente());

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
