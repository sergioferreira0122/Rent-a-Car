package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IEstatisticas;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Estatisticas implements IEstatisticas {
    private DatabaseHandler databaseHandler;

    @Override
    public int totalClientes() {
        int total;
        try {
            String query = """
                    SELECT COUNT(*) AS total_clientes
                    FROM Cliente
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                total = rs.getInt("total_clientes");

                return total;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return 0;
    }

    @Override
    public int loadVeiculosDisponiveis() {
        int total;
        try {
            String query = """
                    SELECT COUNT(*) AS total_carros
                    FROM Veiculo
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                total = rs.getInt("total_carros");

                return total - loadTotalVeiculosEmUso();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return 0;
    }

    @Override
    public double loadReceitaTotal() {
        double total;
        try {
            String query = """
                    SELECT dbo.CalcularReceitaTotal() AS receita_total;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                total = rs.getDouble("receita_total");

                return total;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return 0;
    }

    @Override
    public int loadTotalVeiculosEmUso() {
        int total;
        try {
            String query = """
                    SELECT COUNT(*) AS total_carros_em_uso
                    FROM Aluguer
                    WHERE data_entrega IS NULL
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                total = rs.getInt("total_carros_em_uso");

                return total;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return 0;
    }
}
