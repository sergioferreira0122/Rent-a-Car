package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.ClienteDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Enums.TipoCliente;
import aluguer.veiculos.aluguerveiculos.Enums.TipoConta;
import aluguer.veiculos.aluguerveiculos.Models.ClienteModel;

import java.util.ArrayList;
import java.util.List;

public final class ClienteService {
    private static ClienteService clienteService;
    private DAO<ClienteModel> clienteDAO;

    private ClienteService() {
        clienteDAO = new ClienteDAO();
    }

    public static synchronized ClienteService getInstance() {
        if (clienteService == null) {
            clienteService = new ClienteService();
        }
        return clienteService;
    }

    public List<ClienteModel> listaClientes() {
        return clienteDAO.getAll();
    }

    public boolean atualizarCliente(int id_cliente,
                                    String nome,
                                    TipoCliente tipoCliente,
                                    TipoConta tipoConta) {

        ClienteModel clienteModel = new ClienteModel(id_cliente, tipoCliente, tipoConta, nome);

        return clienteDAO.update(clienteModel);
    }

    public boolean adicionarCliente(String nome,
                                    TipoCliente tipoCliente,
                                    TipoConta tipoConta) {

        ClienteModel clienteModel = new ClienteModel(tipoCliente, tipoConta, nome);

        return clienteDAO.insert(clienteModel);
    }

    public List<ClienteModel> pesquisarClienteID(int id_cliente) {
        List<ClienteModel> lista = new ArrayList<>();
        lista.add(clienteDAO.getById(id_cliente));
        return lista;
    }

    public boolean eliminarCliente(int id_cliente) {
        return clienteDAO.delete(new ClienteModel(id_cliente));
    }
}
