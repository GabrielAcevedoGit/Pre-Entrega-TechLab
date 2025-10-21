package techlab.models.pedidos;

import techlab.models.productos.Producto;

public class LineaPedido {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrice() * cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}
