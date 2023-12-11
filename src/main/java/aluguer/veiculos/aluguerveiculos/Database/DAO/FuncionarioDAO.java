package aluguer.veiculos.aluguerveiculos.Database.DAO;

import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IFuncionarioDAO;
import aluguer.veiculos.aluguerveiculos.Database.Handler.DatabaseHandler;
import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements IFuncionarioDAO {
    private DatabaseHandler databaseHandler;

    @Override
    public List<FuncionarioModel> getAll() {
        List<FuncionarioModel> lista = new ArrayList<>();

        try {
            String query = """
                    SELECT f.id_funcionario, f.nome, f.email, f.password, p.*
                    FROM Funcionario f
                    JOIN Permissoes p
                    ON f.id_funcionario = p.id_funcionario
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);
            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_funcionario");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");

                List<Permissoes> permissoes = new ArrayList<>();
                permissoes.add(rs.getBoolean("gestaoFuncionarios") ? Permissoes.GESTAO_FUNCIONARIOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoPedidos") ? Permissoes.GESTAO_PEDIDOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoVeiculos") ? Permissoes.GESTAO_VEICULOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoClientes") ? Permissoes.GESTAO_CLIENTES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoMarcas") ? Permissoes.GESTAO_MARCAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoModelos") ? Permissoes.GESTAO_MODELOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoServicos") ? Permissoes.GESTAO_SERVICOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoAluguers") ? Permissoes.GESTAO_ALUGUERS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoReparacoes") ? Permissoes.GESTAO_REPARACOES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoRecibos") ? Permissoes.GESTAO_RECIBOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoFaturas") ? Permissoes.GESTAO_FATURAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoCondutores") ? Permissoes.GESTAO_CONDUTORES : Permissoes.SEM_PERMISSAO);


                FuncionarioModel funcionarioModel = new FuncionarioModel(id, nome, email, password, permissoes);

                lista.add(funcionarioModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }

        return lista;
    }


    @Override
    public FuncionarioModel getById(int id) {
        try {
            String query = """
                    SELECT f.id_funcionario, f.nome, f.email, f.password, p.*
                    FROM Funcionario f
                    JOIN Permissoes p
                    ON f.id_funcionario = p.id_funcionario
                    WHERE f.id_funcionario = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");

                List<Permissoes> permissoes = new ArrayList<>();
                permissoes.add(rs.getBoolean("gestaoFuncionarios") ? Permissoes.GESTAO_FUNCIONARIOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoPedidos") ? Permissoes.GESTAO_PEDIDOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoVeiculos") ? Permissoes.GESTAO_VEICULOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoClientes") ? Permissoes.GESTAO_CLIENTES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoMarcas") ? Permissoes.GESTAO_MARCAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoModelos") ? Permissoes.GESTAO_MODELOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoServicos") ? Permissoes.GESTAO_SERVICOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoAluguers") ? Permissoes.GESTAO_ALUGUERS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoReparacoes") ? Permissoes.GESTAO_REPARACOES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoRecibos") ? Permissoes.GESTAO_RECIBOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoFaturas") ? Permissoes.GESTAO_FATURAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoCondutores") ? Permissoes.GESTAO_CONDUTORES : Permissoes.SEM_PERMISSAO);

                return new FuncionarioModel(id, nome, email, password, permissoes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean insert(FuncionarioModel dados) {
        try {
            String query = """
                    INSERT INTO Funcionario
                    (nome, email, password)
                    VALUES (?, ?, ?)
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, dados.getNome());
            statement.setString(2, dados.getEmail());
            statement.setString(3, dados.getPassword());

            if (databaseHandler.execAction(statement)) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idFuncionario = generatedKeys.getInt(1);
                    return insertPermissoes(idFuncionario, dados.getPermissoes());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public boolean update(FuncionarioModel dados) {
        try {
            String query = """
                    UPDATE Funcionario
                    SET nome = ?, email = ?, password = ?
                    WHERE id_funcionario = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getNome());
            statement.setString(2, dados.getEmail());
            statement.setString(3, dados.getPassword());
            statement.setInt(4, dados.getId_funcionario());

            boolean success = databaseHandler.execAction(statement);

            if (success) {
                return updatePermissoes(dados.getId_funcionario(), dados.getPermissoes());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public boolean delete(FuncionarioModel dados) {
        try {
            String query = """
                    DELETE FROM Funcionario
                    WHERE id_funcionario = ?
                    DELETE FROM Permissoes
                    WHERE id_funcionario = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, dados.getId_funcionario());
            statement.setInt(2, dados.getId_funcionario());

            return databaseHandler.execAction(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    //Metodos para ajudar na criação/alteração de dados
    private boolean insertPermissoes(int id, List<Permissoes> permissoes) {
        try {
            String query = "INSERT INTO Permissoes (id_funcionario, gestaoFuncionarios, gestaoPedidos, gestaoVeiculos, gestaoClientes, gestaoMarcas, gestaoModelos, gestaoServicos, gestaoAluguers, gestaoReparacoes, gestaoRecibos, gestaoFaturas, gestaoCondutores) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setInt(1, id);
            statement.setBoolean(2, permissoes.contains(Permissoes.GESTAO_FUNCIONARIOS));
            statement.setBoolean(3, permissoes.contains(Permissoes.GESTAO_PEDIDOS));
            statement.setBoolean(4, permissoes.contains(Permissoes.GESTAO_VEICULOS));
            statement.setBoolean(5, permissoes.contains(Permissoes.GESTAO_CLIENTES));
            statement.setBoolean(6, permissoes.contains(Permissoes.GESTAO_MARCAS));
            statement.setBoolean(7, permissoes.contains(Permissoes.GESTAO_MODELOS));
            statement.setBoolean(8, permissoes.contains(Permissoes.GESTAO_SERVICOS));
            statement.setBoolean(9, permissoes.contains(Permissoes.GESTAO_ALUGUERS));
            statement.setBoolean(10, permissoes.contains(Permissoes.GESTAO_REPARACOES));
            statement.setBoolean(11, permissoes.contains(Permissoes.GESTAO_RECIBOS));
            statement.setBoolean(12, permissoes.contains(Permissoes.GESTAO_FATURAS));
            statement.setBoolean(13, permissoes.contains(Permissoes.GESTAO_CONDUTORES));

            return databaseHandler.execAction(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    private boolean updatePermissoes(int id, List<Permissoes> permissoes) {
        try {
            String query = "UPDATE Permissoes SET gestaoFuncionarios = ?, gestaoPedidos = ?, gestaoVeiculos = ?, gestaoClientes = ?, gestaoMarcas = ?, gestaoModelos = ?, gestaoServicos = ?, gestaoAluguers = ?, gestaoReparacoes = ?, gestaoRecibos = ?, gestaoFaturas = ?, gestaoCondutores = ? WHERE id_funcionario = ?";

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setBoolean(1, permissoes.contains(Permissoes.GESTAO_FUNCIONARIOS));
            statement.setBoolean(2, permissoes.contains(Permissoes.GESTAO_PEDIDOS));
            statement.setBoolean(3, permissoes.contains(Permissoes.GESTAO_VEICULOS));
            statement.setBoolean(4, permissoes.contains(Permissoes.GESTAO_CLIENTES));
            statement.setBoolean(5, permissoes.contains(Permissoes.GESTAO_MARCAS));
            statement.setBoolean(6, permissoes.contains(Permissoes.GESTAO_MODELOS));
            statement.setBoolean(7, permissoes.contains(Permissoes.GESTAO_SERVICOS));
            statement.setBoolean(8, permissoes.contains(Permissoes.GESTAO_ALUGUERS));
            statement.setBoolean(9, permissoes.contains(Permissoes.GESTAO_REPARACOES));
            statement.setBoolean(10, permissoes.contains(Permissoes.GESTAO_RECIBOS));
            statement.setBoolean(11, permissoes.contains(Permissoes.GESTAO_FATURAS));
            statement.setBoolean(12, permissoes.contains(Permissoes.GESTAO_CONDUTORES));
            statement.setInt(13, id);

            return databaseHandler.execAction(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }

    @Override
    public FuncionarioModel login(String email, String password) {
        try {
            String query = """
                    SELECT f.id_funcionario, f.nome, f.email, f.password, p.*
                    FROM Funcionario f
                    JOIN Permissoes p
                    ON f.id_funcionario = p.id_funcionario
                    WHERE f.email = ? AND f.password = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = databaseHandler.execQuery(statement);

            while (rs.next()) {
                int id = rs.getInt("id_funcionario");
                String nome = rs.getString("nome");

                List<Permissoes> permissoes = new ArrayList<>();
                permissoes.add(rs.getBoolean("gestaoFuncionarios") ? Permissoes.GESTAO_FUNCIONARIOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoPedidos") ? Permissoes.GESTAO_PEDIDOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoVeiculos") ? Permissoes.GESTAO_VEICULOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoClientes") ? Permissoes.GESTAO_CLIENTES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoMarcas") ? Permissoes.GESTAO_MARCAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoModelos") ? Permissoes.GESTAO_MODELOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoServicos") ? Permissoes.GESTAO_SERVICOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoAluguers") ? Permissoes.GESTAO_ALUGUERS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoReparacoes") ? Permissoes.GESTAO_REPARACOES : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoRecibos") ? Permissoes.GESTAO_RECIBOS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoFaturas") ? Permissoes.GESTAO_FATURAS : Permissoes.SEM_PERMISSAO);
                permissoes.add(rs.getBoolean("gestaoCondutores") ? Permissoes.GESTAO_CONDUTORES : Permissoes.SEM_PERMISSAO);

                return new FuncionarioModel(id, nome, email, password, permissoes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return null;
    }

    @Override
    public boolean updateNoPasswordUpdate(FuncionarioModel dados) {
        try {
            String query = """
                    UPDATE Funcionario
                    SET nome = ?, email = ?
                    WHERE id_funcionario = ?
                    """;

            databaseHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query);

            statement.setString(1, dados.getNome());
            statement.setString(2, dados.getEmail());
            statement.setInt(3, dados.getId_funcionario());

            boolean success = databaseHandler.execAction(statement);

            if (success) {
                return updatePermissoes(dados.getId_funcionario(), dados.getPermissoes());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHandler.closeConn();
        }
        return false;
    }
}
