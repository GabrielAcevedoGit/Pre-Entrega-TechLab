package techlab.Exceptions;

public class ProductoExistenteException extends Exception {
    public ProductoExistenteException(String nombre) {
        super("El producto '" + nombre + "' ya existe en la lista.");
    }
}
