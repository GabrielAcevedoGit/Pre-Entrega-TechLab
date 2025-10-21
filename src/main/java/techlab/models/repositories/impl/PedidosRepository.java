package techlab.models.repositories.impl;

import techlab.models.pedidos.Pedido;
import techlab.models.repositories.IPedidosRepository;

import java.util.ArrayList;
import java.util.List;

public class PedidosRepository implements IPedidosRepository {
    List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void save(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidos;
    }
}
