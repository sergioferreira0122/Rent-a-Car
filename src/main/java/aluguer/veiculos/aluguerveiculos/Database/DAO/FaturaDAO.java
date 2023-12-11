package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Models.FaturaModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ReciboModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaturaDAO implements DAO<FaturaModel> {
    private DatabaseHandler databaseHandler;

    @Override
    public List<FaturaModel> getAll() {
        List<FaturaModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Fatura
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_fatura = rs.getInt("id_fatura");
                int id_funcionario = rs.getInt("id_funcionario");
                int id_recibo = rs.getInt("id_recibo");
                LocalDate data_fatura = LocalDate.parse(rs.getString("data"));

                FuncionarioModel funcionarioModel = new FuncionarioModel(id_funcionario);
                ReciboModel reciboModel = new ReciboModel(id_recibo);

                FaturaModel faturaModel = new FaturaModel(id_fatura, funcionarioModel, reciboModel, data_fatura);

                lista.add(faturaModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }

    @Override
    public FaturaModel getById(int id) {
        try {
            String query = """
                    SELECT *
                    FROM Fatura
                    WHERE id_fatura = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id_fatura = rs.getInt("id_fatura");
                int id_funcionario = rs.getInt("id_funcionario");
                int id_recibo = rs.getInt("id_recibo");
                LocalDate data_fatura = LocalDate.parse(rs.getString("data"));

                FuncionarioModel funcionarioModel = new FuncionarioModel(id_funcionario);
                ReciboModel reciboModel = new ReciboModel(id_recibo);

                return new FaturaModel(id, funcionarioModel, reciboModel, data_fatura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(FaturaModel dados) {
        try {
            String query = """
                    INSERT INTO Fatura
                    (id_funcionario, data)
                    VALUES (?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getFuncionarioModel().getId_funcionario());
            statement.setString(2, String.valueOf(dados.getData()));

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
    public boolean update(FaturaModel dados) {
        try {
            String query = """
                    EXEC PagarFatura @idRecibo = ?, @idFatura = ?, @idFuncionario = ?, @data = ?;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            if (dados.getReciboModel().isPresent()) {
                statement.setInt(1, dados.getReciboModel().get().getId_recibo());
            } else {
                statement.setString(1, null);
            }

            statement.setInt(2, dados.getId_fatura());
            statement.setInt(3, dados.getFuncionarioModel().getId_funcionario());
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
    public boolean delete(FaturaModel dados) {
        try {
            String query = """
                    DELETE FROM Fatura
                    WHERE id_fatura = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_fatura());

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
