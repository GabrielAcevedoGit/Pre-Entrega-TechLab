package techlab.models.pedidos;

import techlab.models.productos.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger sequence = new AtomicInteger(0);

    private final int id;
    private List<LineaPedido> lineas;
    private double total;

    public Pedido() {
        this.id = sequence.incrementAndGet();
        this.lineas = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarLinea(Producto producto, int cantidad) {
        LineaPedido linea = new LineaPedido(producto, cantidad);
        lineas.add(linea);
        total += linea.getSubtotal();
    }

    public int getId() { return id; }
    public List<LineaPedido> getLineas() { return lineas; }
    public double getTotal() { return total; }
}