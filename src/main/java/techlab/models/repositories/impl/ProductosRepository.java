package techlab.models.repositories.impl;

import techlab.models.productos.Producto;
import techlab.models.repositories.IProductosRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductosRepository implements IProductosRepository {
    // LISTA DE PRODUCTOS
    List<Producto> products = new ArrayList<>();

    @Override
    public Producto findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Producto findByName(String name) {
        return products.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<Producto> findAll() {
        return products;
    }

    @Override
    public void save(Producto product) {
        products.add(product);
    }

    @Override
    public void delete(Producto product) {
        products.remove(product);
    }
    // CHEQUEAR
    @Override
    public void update(Producto updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Producto current = products.get(i);
            if (current.getId() == updatedProduct.getId()) {
                products.set(i, updatedProduct);
                System.out.println("✅ Producto actualizado correctamente.");
                return;
            }
        }
        System.out.println("⚠️ No se encontró un producto con ID: " + updatedProduct.getId());
    }

}
