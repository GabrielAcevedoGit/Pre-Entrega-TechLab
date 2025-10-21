package techlab.Exceptions;

public class DatosInvalidosException extends Exception{
    public DatosInvalidosException(Double precio, int stock){
        super("El precio o el stock no pueden ser negativos: precio = " + precio + ", stock = " + stock );
    }
}
