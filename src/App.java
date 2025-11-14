import domain.Cliente;
import domain.Pedido;
import domain.Producto;
import service.PapeleriaService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PapeleriaService service = new PapeleriaService();

        System.out.println("=== Punto & Coma - Sistema de Pedidos ===");

        // 1. Registrar cliente
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese teléfono del cliente: ");
        String telefono = sc.nextLine();

        Cliente cliente;
        try {
            cliente = service.registrarCliente(nombre, telefono);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            sc.close();
            return;
        }

        // 2. Crear pedido
        Pedido pedido = service.crearPedido(cliente);

        boolean pedidoConfirmado = false;
        while (!pedidoConfirmado) {
            boolean seguir = true;
            while (seguir) {
                System.out.println("\nSeleccione producto:");
                System.out.println("1. Impresión B/N");
                System.out.println("2. Impresión Color");
                System.out.println("3. Anillado");
                System.out.print("Opción: ");
                int opcion = Integer.parseInt(sc.nextLine());

                Producto productoSeleccionado;
                switch (opcion) {
                    case 1 -> productoSeleccionado = Producto.IMPRESION_BN;
                    case 2 -> productoSeleccionado = Producto.IMPRESION_COLOR;
                    case 3 -> productoSeleccionado = Producto.ANILLADO;
                    default -> {
                        System.out.println("Opción inválida");
                        continue;
                    }
                }

                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());

                try {
                    service.agregarItem(pedido, productoSeleccionado, cantidad);
                } catch (Exception e) {
                    System.out.println("No se pudo agregar el ítem: " + e.getMessage());
                }

                System.out.print("¿Desea agregar otro ítem? (s/n): ");
                String r = sc.nextLine().trim().toLowerCase();
                seguir = r.equals("s");
            }

            // 3. Calcular y mostrar resumen
            service.calcularPedido(pedido);

            // 4. Confirmar
            System.out.print("¿Confirmar pedido? (s/n): ");
            String conf = sc.nextLine().trim().toLowerCase();
            if (conf.equals("s")) {
                service.confirmarPedido(pedido);
                System.out.println("Pedido confirmado.");
                pedidoConfirmado = true;
            } else {
                System.out.println("Pedido NO confirmado. Puede agregar más ítems.");
            }
        }

        // 5. Mostrar resumen final confirmado
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMEN FINAL DEL PEDIDO");
        System.out.println("=".repeat(50));
        System.out.println(service.generarResumen(pedido));
        System.out.println("=".repeat(50));

        // 6. Probar bloqueo post-confirmación
        System.out.println("\nIntentando agregar ítem después de confirmar...");
        try {
            service.agregarItem(pedido, Producto.IMPRESION_BN, 1);
            System.out.println("ERROR: Se permitió agregar ítem tras confirmar (no debería pasar).");
        } catch (Exception e) {
            System.out.println("Correcto: no se puede editar pedido confirmado: " + e.getMessage());
        }

        System.out.println("\nFin del flujo.");
        sc.close();
    }
}
