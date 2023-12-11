package aluguer.veiculos.aluguerveiculos.Database.Handler;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.sql.*;

public final class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlserver://ctespbd.dei.isep.ipp.pt;encryption=true;trustServerCertificate=true";
    private static final String DB_USER = "2023_DIAS_G3_FEIRA";
    private static final String DB_PASSWORD = "KhbAa6s";
    private static DatabaseHandler handler = null;
    private static Connection conn = null;

    private DatabaseHandler() {
        createConnection();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public Connection getConnection() {
        if (isConnClosed()) {
            createConnection();
        }
        return conn;
    }

    private void createConnection() {
        try {
            conn = DriverManager
                    .getConnection(
                            DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar ligacao na base de dados");
            System.out.println("Desligando progama...");
            System.exit(0);
        }
    }

    public ResultSet execQuery(PreparedStatement preparedStatement) {
        ResultSet result;
        try {
            result = preparedStatement
                    .executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro em execQuery:dataHandler, " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(PreparedStatement preparedStatement) {
        try {
            preparedStatement
                    .execute();
            return true;
        } catch (SQLException e) {
            // Error dialog
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Erro");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Erro: " + e.getMessage());
            dialog.getDialogPane()
                    .getButtonTypes()
                    .add(type);
            dialog.showAndWait();
            // Error dialog

            System.out.println("Erro em execAction:dataHandler, " + e.getLocalizedMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void closeConn() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desligar conexao");
        }
    }

    public boolean isConnClosed() {
        return conn == null;
    }
}