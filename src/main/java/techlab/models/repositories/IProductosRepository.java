package techlab.models.repositories;

import techlab.models.productos.Producto;

import java.util.List;

public interface IProductosRepository {
    Producto findById(int id);
    Producto findByName(String name);
    List<Producto> findAll();
    void save(Producto product);
    void delete(Producto product);
    void update(Producto product);
}
