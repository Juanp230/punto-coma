package domain;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final Cliente cliente;
    private final List<ItemPedido> items;
    private EstadoPedido estado;
    private int totalBruto;
    private int descuento;
    private int totalFinal;

    public Pedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El pedido debe tener un cliente asociado");
        }
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.EN_CREACION;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public List<ItemPedido> getItems() {
        return new ArrayList<>(items);
    }

    public int getTotalBruto() {
        return totalBruto;
    }

    public int getDescuento() {
        return descuento;
    }

    public int getTotalFinal() {
        return totalFinal;
    }

    public void agregarItem(Producto producto, int cantidad) {
        if (estado != EstadoPedido.EN_CREACION) {
            throw new IllegalStateException("No se pueden agregar ítems a un pedido confirmado");
        }
        ItemPedido item = new ItemPedido(producto, cantidad);
        items.add(item);
    }

    public void calcularTotales() {
        int suma = 0;
        int totalImpresiones = 0;
        boolean tieneAnillado = false;

        for (ItemPedido item : items) {
            item.recalcular();
            suma += item.getSubtotal();

            if (item.getProducto() == Producto.IMPRESION_BN
                    || item.getProducto() == Producto.IMPRESION_COLOR) {
                totalImpresiones += item.getCantidad();
            }
            if (item.getProducto() == Producto.ANILLADO) {
                tieneAnillado = true;
            }
        }

        if (suma < 0) {
            throw new IllegalStateException("El total bruto no puede ser negativo");
        }

        this.totalBruto = suma;

        double porcentajeDescuento = 0.0;

        // Regla 1: 10% si hay anillado y impresiones >= 30
        if (tieneAnillado && totalImpresiones >= 30) {
            porcentajeDescuento = 0.10;
        } else if (totalBruto > 40000) {
            // Regla 2: 5% si total bruto > 40.000
            porcentajeDescuento = 0.05;
        }

        this.descuento = (int) Math.round(totalBruto * porcentajeDescuento);
        this.totalFinal = totalBruto - descuento;

        if (totalFinal < 0) {
            throw new IllegalStateException("El total final no puede ser negativo");
        }
    }

    public void confirmar() {
        if (estado == EstadoPedido.CONFIRMADO) {
            return;
        }
        // Idealmente, asegurar que ya está calculado:
        calcularTotales();
        this.estado = EstadoPedido.CONFIRMADO;
    }

    public String generarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN DEL PEDIDO ===\n");
        sb.append("Cliente: ").append(cliente).append("\n");
        sb.append("Estado: ").append(estado).append("\n\n");

        sb.append("DETALLE:\n");
        for (ItemPedido item : items) {
            sb.append("- ")
              .append(item.getProducto().getDescripcion())
              .append(" | Cant: ").append(item.getCantidad())
              .append(" | Precio unit: ").append(item.getPrecioUnitarioAplicado())
              .append(" | Subtotal: ").append(item.getSubtotal())
              .append("\n");
        }

        sb.append("\nTotal bruto: ").append(totalBruto).append("\n");
        sb.append("Descuento: ").append(descuento).append("\n");
        sb.append("Total final: ").append(totalFinal).append("\n");
        sb.append("==========================\n");

        return sb.toString();
    }
}
