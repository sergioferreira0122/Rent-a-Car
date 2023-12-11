package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IVerificacoesDAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VerificacoesDAO implements IVerificacoesDAO {
    private DatabaseHandler databaseHandler;

    @Override
    public boolean verificarModeloDeUmaMarca(int id_modelo,
                                             int id_marca) {
        try {
            String query = """
                    EXEC VerificarModeloDeMarca @idMarca = ?, @idModelo = ?;
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id_marca);
            statement.setInt(2, id_modelo);

            return databaseHandler.execAction(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            databaseHandler.closeConn();
        }
    }
}
