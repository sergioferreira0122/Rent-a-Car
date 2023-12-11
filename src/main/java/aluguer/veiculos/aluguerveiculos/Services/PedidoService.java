package aluguer.veiculos.aluguerveiculos.Services;

import aluguer.veiculos.aluguerveiculos.Database.DAO.AluguerDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.ClienteDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.FaturaDAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.Interface.DAO;
import aluguer.veiculos.aluguerveiculos.Database.DAO.PedidoDAO;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.ClienteModel;
import aluguer.veiculos.aluguerveiculos.Models.FaturaModel;
import aluguer.veiculos.aluguerveiculos.Models.PedidoModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class PedidoService {
    private static PedidoService pedidoService;
    private DAO<PedidoModel> pedidoModelDAO;
    private DAO<ClienteModel> clienteModelDAO;
    private DAO<FaturaModel> faturaModelDAO;
    private DAO<AluguerModel> aluguerModelDAO;

    private PedidoService() {
        pedidoModelDAO = new PedidoDAO();
        clienteModelDAO = new ClienteDAO();
        faturaModelDAO = new FaturaDAO();
        aluguerModelDAO = new AluguerDAO();
    }

    public static synchronized PedidoService getInstance() {
        if (pedidoService == null) {
            pedidoService = new PedidoService();
        }
        return pedidoService;
    }

    public List<PedidoModel> listaPedidos() {
        return pedidoModelDAO.getAll();
    }

    public boolean atualizarPedido(int id_pedido,
                                   int id_fatura,
                                   int id_cliente,
                                   LocalDate data) {

        FaturaModel faturaModel = null;
        if (id_fatura != 0) {
            faturaModel = faturaModelDAO.getById(id_fatura);
        }

        ClienteModel clienteModel = clienteModelDAO.getById(id_cliente);

        PedidoModel pedidoModel = new PedidoModel(id_pedido, faturaModel, clienteModel, data);

        return pedidoModelDAO.update(pedidoModel);
    }

    public boolean adicionarPedido(int id_fatura,
                                   int id_cliente,
                                   LocalDate data) {


        FaturaModel faturaModel = null;
        if (id_fatura != 0) {
            faturaModel = faturaModelDAO.getById(id_fatura);
        }

        ClienteModel clienteModel = clienteModelDAO.getById(id_cliente);

        PedidoModel pedidoModel = new PedidoModel(faturaModel, clienteModel, data);

        return pedidoModelDAO.insert(pedidoModel);
    }

    public List<PedidoModel> pesquisarPedidoID(int id_pedido) {
        List<PedidoModel> lista = new ArrayList<>();
        lista.add(pedidoModelDAO.getById(id_pedido));
        return lista;
    }

    public boolean eliminarPedido(int id_pedido) {
        return pedidoModelDAO.delete(new PedidoModel(id_pedido));
    }

    public boolean estadoPagamento(int id_pedido) {
        List<PedidoModel> pedidos = new ArrayList<>(pedidoModelDAO.getAll());

        for (PedidoModel pedidoModel : pedidos) {
            if (pedidoModel.getId_pedido() == id_pedido) {
                if (pedidoModel.getFaturaModel().isEmpty() || pedidoModel.getFaturaModel().get().getId_fatura() == 0) {
                    return false;
                } else {
                    var faturaModel = faturaModelDAO.getById(pedidoModel.getFaturaModel().get().getId_fatura());

                    return faturaModel.getReciboModel().isPresent();
                }
            }
        }
        return false;
    }

    public int estadoPedido(int id_pedido) {
        List<AluguerModel> alugueres = new ArrayList<>(aluguerModelDAO.getAll());

        for (AluguerModel aluguerModel : alugueres) {
            if (aluguerModel.getPedidoModel().getId_pedido() == id_pedido) {
                if (aluguerModel.getData_entrega().isEmpty()) {
                    return 1;//por entregar
                }else {
                    return 0;//sem alugueres
                }
            }
        }
        return -1;//entregue
    }
}
