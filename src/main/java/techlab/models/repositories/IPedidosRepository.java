package techlab.models.repositories;

import techlab.models.pedidos.Pedido;

import java.util.List;

public interface IPedidosRepository {
    void save(Pedido pedido);
    List<Pedido> findAll();
}
