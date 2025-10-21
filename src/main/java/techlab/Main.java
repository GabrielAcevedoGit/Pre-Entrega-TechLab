package techlab;

import techlab.Exceptions.DatosInvalidosException;
import techlab.Exceptions.ProductoExistenteException;
import techlab.models.pedidos.LineaPedido;
import techlab.models.pedidos.Pedido;
import techlab.models.productos.Producto;
import techlab.models.repositories.IPedidosRepository;
import techlab.models.repositories.IProductosRepository;
import techlab.models.repositories.impl.PedidosRepository;
import techlab.models.repositories.impl.ProductosRepository;
import techlab.services.PedidosService;
import techlab.services.ProductosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        IProductosRepository productosRepository = new ProductosRepository();
        ProductosService productosService = new ProductosService(productosRepository);
        IPedidosRepository pedidosRepository = new PedidosRepository();
        PedidosService pedidosService = new PedidosService(productosRepository,pedidosRepository);
        Boolean estaActivo = true;
        while(estaActivo){
            Scanner sc = new Scanner(System.in);
            System.out.println("========================================================");
            System.out.println("           BIENVENIDO AL SISTEMA DE PRODUCTOS");
            System.out.println("========================================================");
            System.out.println("PARA COMENZAR SELECCIONE QUE OPERACION DESEA REALIZAR:");
            System.out.println("1 - AGREGAR PRODUCTO");
            System.out.println("2 - LISTAR PRODUCTO");
            System.out.println("3 - BUSCAR/ACTUALIZAR PRODUCTO");
            System.out.println("4 - ELIMINAR PRODUCTO");
            System.out.println("5 - CREAR UN PEDIDO");
            System.out.println("6 - LISTAR PEDIDOS");
            System.out.println("7 - SALIR");
            int opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    System.out.println("========================================================");
                    System.out.println("                      AGREGAR PRODUCTO");
                    System.out.println("========================================================");
                    try {
                        System.out.print("INGRESE EL NOMBRE DEL PRODUCTO: ");
                        String nombre = sc.nextLine();

                        System.out.print("INGRESE EL PRECIO DEL PRODUCTO: ");
                        Double precio = Double.parseDouble(sc.nextLine());

                        System.out.print("INGRESE EL STOCK DEL PRODUCTO: ");
                        int stock = Integer.parseInt(sc.nextLine());

                        productosService.createProduct(nombre, precio, stock);
                        System.out.println("✅ Producto agregado correctamente.");
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error: Debe ingresar un número válido para precio y stock.");
                    } catch (ProductoExistenteException | DatosInvalidosException e) {
                        System.out.println("⚠️ Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Error inesperado: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("========================================================");
                    System.out.println("                      LISTAR PRODUCTO");
                    System.out.println("========================================================");
                    System.out.printf("%-5s %-25s %-10s %-10s%n", "ID", "PRODUCTO", "PRECIO", "STOCK");
                    System.out.println("---------------------------------------------------------------");

                    for (Producto producto : productosService.getAllProducts()) {
                        System.out.printf("%-5d %-25s $%-9.2f %-10d%n",
                                producto.getId(),
                                producto.getName(),
                                producto.getPrice(),
                                producto.getStock());
                    }
                    break;
                case 3:
                    System.out.println("========================================================");
                    System.out.println("               BUSCAR/ACTUALIZAR PRODUCTO");
                    System.out.println("========================================================");
                    try {
                        System.out.print("Ingrese el ID del producto a actualizar: ");
                        int idToUpdate = sc.nextInt();
                        sc.nextLine();

                        Producto producto = productosRepository.findById(idToUpdate);

                        if (producto == null) {
                            System.out.println("❌ No se encontró un producto con ese ID.");
                            break;
                        }

                        System.out.println("Producto encontrado:");
                        System.out.printf("ID: %d | NOMBRE: %s | PRECIO: %.2f | STOCK: %d%n",
                                producto.getId(), producto.getName(), producto.getPrice(), producto.getStock());

                        System.out.print("Nuevo nombre (ENTER para mantener): ");
                        String nuevoNombre = sc.nextLine();
                        if (!nuevoNombre.isEmpty()) producto.setName(nuevoNombre);

                        System.out.print("Nuevo precio (ENTER para mantener): ");
                        String nuevoPrecioStr = sc.nextLine();
                        if (!nuevoPrecioStr.isEmpty()) producto.setPrice(Double.parseDouble(nuevoPrecioStr));

                        System.out.print("Nuevo stock (ENTER para mantener): ");
                        String nuevoStockStr = sc.nextLine();
                        if (!nuevoStockStr.isEmpty()) producto.setStock(Integer.parseInt(nuevoStockStr));

                        productosRepository.update(producto);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error: Ingrese un número válido para el ID.");
                    }
                    break;

                case 4:
                    System.out.println("========================================================");
                    System.out.println("                  ELIMINAR PRODUCTO");
                    System.out.println("========================================================");
                    try {
                        System.out.print("Ingrese el ID del producto a eliminar: ");
                        int idAEliminar = Integer.parseInt(sc.nextLine());

                        Producto productoAEliminar = productosRepository.findById(idAEliminar);

                        if (productoAEliminar == null) {
                            System.out.println("❌ No se encontró un producto con ese ID.");
                        } else {
                            System.out.println("⚠️ Está a punto de eliminar el producto: " + productoAEliminar.getName());
                            System.out.print("¿Desea continuar? (S/N): ");
                            String confirmacion = sc.nextLine().trim().toUpperCase();

                            if (confirmacion.equals("S")) {
                                productosRepository.delete(productoAEliminar);
                                System.out.println("✅ Producto eliminado correctamente.");
                            } else {
                                System.out.println("❎ Operación cancelada. El producto no fue eliminado.");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error: Ingrese un número válido para el ID.");
                    }
                    break;
                case 5:
                    System.out.println("========================================================");
                    System.out.println("                      CREAR PEDIDO");
                    System.out.println("========================================================");
                    List<LineaPedido> lineas = new ArrayList<>();

                    while (true) {
                        System.out.print("Ingrese el ID del producto (o 0 para finalizar): ");
                        int idProducto = Integer.parseInt(sc.nextLine());
                        if (idProducto == 0) break;

                        Producto producto = productosRepository.findById(idProducto);
                        if (producto == null) {
                            System.out.println("❌ Producto no encontrado.");
                            continue;
                        }

                        System.out.print("Ingrese la cantidad deseada: ");
                        int cantidad = Integer.parseInt(sc.nextLine());

                        if (cantidad <= 0) {
                            System.out.println("⚠️ Cantidad inválida.");
                            continue;
                        }

                        lineas.add(new LineaPedido(producto, cantidad));
                    }

                    try {
                        pedidosService.crearPedido(lineas);
                    } catch (Exception e) {
                        System.out.println("❌ Error al crear el pedido: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("========================================================");
                    System.out.println("                    LISTAR PEDIDOS");
                    System.out.println("========================================================");
                    for (Pedido pedido : pedidosRepository.findAll()) {
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Pedido #" + pedido.getId() + " | Total: $" + pedido.getTotal());
                        System.out.println("Productos:");
                        for (LineaPedido linea : pedido.getLineas()) {
                            System.out.printf(" - %s x%d ($%.2f)%n",
                                    linea.getProducto().getName(),
                                    linea.getCantidad(),
                                    linea.getSubtotal());
                        }
                    }
                    break;
                case 7:
                    System.out.println("========================================================");
                    System.out.println("                    FIN DEL PROGRAMA");
                    System.out.println("========================================================");
                    estaActivo = false;
                    break;
                default:
                    System.out.println("Opcion incorrecta, intente nuevamente");
            }
        }



    }
}