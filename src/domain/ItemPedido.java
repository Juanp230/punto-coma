package domain;

public class ItemPedido {

    private final Producto producto;
    private final int cantidad;
    private int precioUnitarioAplicado;
    private int subtotal;

    public ItemPedido(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        recalcular();
    }

    public void recalcular() {
        this.precioUnitarioAplicado = producto.calcularPrecioUnitario(cantidad);
        this.subtotal = precioUnitarioAplicado * cantidad;
        if (subtotal < 0) {
            throw new IllegalStateException("El subtotal no puede ser negativo");
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecioUnitarioAplicado() {
        return precioUnitarioAplicado;
    }

    public int getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return producto.getDescripcion()
                + " x" + cantidad
                + " @ " + precioUnitarioAplicado
                + " = " + subtotal;
    }
}

