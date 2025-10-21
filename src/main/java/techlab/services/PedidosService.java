package techlab.services;

import techlab.models.pedidos.LineaPedido;
import techlab.models.pedidos.Pedido;
import techlab.models.productos.Producto;
import techlab.models.repositories.IPedidosRepository;
import techlab.models.repositories.IProductosRepository;
import techlab.models.repositories.impl.PedidosRepository;
import techlab.models.repositories.impl.ProductosRepository;

import java.util.List;

public class PedidosService {
    private final IProductosRepository productosRepository;
    private final IPedidosRepository pedidosRepository;

    public PedidosService(IProductosRepository productosRepository, IPedidosRepository pedidosRepository) {
        this.productosRepository = productosRepository;
        this.pedidosRepository = pedidosRepository;
    }

    public void crearPedido(List<LineaPedido> lineas) throws Exception {
        Pedido pedido = new Pedido();

        for (LineaPedido linea : lineas) {
            Producto producto = linea.getProducto();
            int cantidad = linea.getCantidad();

            if (producto.getStock() < cantidad) {
                throw new Exception("No hay suficiente stock para el producto: " + producto.getName());
            }

            producto.setStock(producto.getStock() - cantidad);
            pedido.agregarLinea(producto, cantidad);
        }

        pedidosRepository.save(pedido);
        System.out.println("✅ Pedido creado con éxito. Total: $" + pedido.getTotal());
    }

}
