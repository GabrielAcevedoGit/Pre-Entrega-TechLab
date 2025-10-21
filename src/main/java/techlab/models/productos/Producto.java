package techlab.models.productos;

import java.util.concurrent.atomic.AtomicInteger;

public class Producto {
    private final int id;
    private String name;
    private double price;
    private int stock;
    private static final AtomicInteger sequence = new AtomicInteger(0);

    public Producto(String name, double price, int stock) {
        this.id = sequence.incrementAndGet();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public double getPrice() {return this.price;}
    public int getStock() {return this.stock;}

    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setStock(int stock) {this.stock = stock;}

}
