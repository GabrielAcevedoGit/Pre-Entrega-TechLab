package techlab.services;

import techlab.Exceptions.DatosInvalidosException;
import techlab.Exceptions.ProductoExistenteException;
import techlab.models.productos.Producto;
import techlab.models.repositories.IProductosRepository;

import java.util.List;

public class ProductosService {
    IProductosRepository iProductosRepository;

    public ProductosService(IProductosRepository iProductosRepository) {
        this.iProductosRepository = iProductosRepository;
    }

    public void createProduct(String name, Double price, int stock) throws Exception {

        if (iProductosRepository.findByName(name) != null) {
            throw new ProductoExistenteException(name);
        }
        if (price <= 0 || stock <= 0) {
            throw new DatosInvalidosException(price, stock);
        }
        name = formatearNombreProducto(name);
        Producto product = new Producto(name, price, stock);
        iProductosRepository.save(product);
        System.out.println("✅ Producto agregado: " + name);
    }

    public List<Producto> getAllProducts(){
        return iProductosRepository.findAll();
    }

    private static String formatearNombreProducto(String nombre) {
        nombre = nombre.trim().toLowerCase();// "mensaje de texto"
        String[] palabras = nombre.split(" "); // ["mensaje", "de", "texto"]

        StringBuilder sb = new StringBuilder(); // texto4 = texto1 + texto2 + texto3
        // sb = "Mensaje De Texto "
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) { // si el string no esta vacio
                sb.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}

