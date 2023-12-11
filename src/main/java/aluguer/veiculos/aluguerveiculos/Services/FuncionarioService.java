package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.FuncionarioDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.IFuncionarioDAO;
import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;

import java.util.ArrayList;
import java.util.List;

public final class FuncionarioService {
    private static FuncionarioService funcionarioService;
    private final IFuncionarioDAO funcionarioDAO;

    private FuncionarioService() {
        funcionarioDAO = new FuncionarioDAO();
    }

    public static synchronized FuncionarioService getInstance() {
        if (funcionarioService == null) {
            funcionarioService = new FuncionarioService();
        }
        return funcionarioService;
    }

    // metodos
    public FuncionarioModel login(String email, String password) {
        return funcionarioDAO.login(email, password);
    }

    public boolean updateDados(FuncionarioModel funcionarioModel) {
        return funcionarioDAO.update(funcionarioModel);
    }

    public boolean adicionarFuncionario(String email,
                                        String nome,
                                        String password,
                                        List<Permissoes> permissoes) {

        FuncionarioModel funcionarioModel = new FuncionarioModel(nome, email, password, permissoes);

        return funcionarioDAO.insert(funcionarioModel);
    }

    public boolean atualizarFuncionario(int id_funcionario,
                                        String email,
                                        String nome,
                                        String password,
                                        List<Permissoes> permissoes) {

        FuncionarioModel funcionarioModel = new FuncionarioModel(id_funcionario, nome, email, password, permissoes);

        return funcionarioDAO.update(funcionarioModel);
    }

    public List<FuncionarioModel> listaFuncionarios() {
        return funcionarioDAO.getAll();
    }

    public List<FuncionarioModel> pesquisarFuncionario(int id_funcionario) {
        List<FuncionarioModel> lista = new ArrayList<>();
        FuncionarioModel funcionarioModel = funcionarioDAO.getById(id_funcionario);
        lista.add(funcionarioModel);
        return lista;
    }

    public boolean eliminarFuncionario(int id_funcionario) {
        FuncionarioModel funcionarioModel = new FuncionarioModel(id_funcionario);
        return funcionarioDAO.delete(funcionarioModel);
    }

    public boolean atualizarFuncionarioSemPassword(int id_funcionario,
                                                   String email,
                                                   String nome,
                                                   List<Permissoes> permissoes) {

        FuncionarioModel funcionarioModel = new FuncionarioModel(id_funcionario, nome, email, permissoes);

        return funcionarioDAO.updateNoPasswordUpdate(funcionarioModel);
    }
}
