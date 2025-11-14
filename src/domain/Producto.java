package domain;

public enum Producto {

    IMPRESION_BN("Impresión B/N", 200, 150, 100),
    IMPRESION_COLOR("Impresión Color", 500, 400, 50),
    ANILLADO("Anillado", 3000, null, null);

    private final String descripcion;
    private final int precioNormal;
    private final Integer precioPorVolumen;
    private final Integer cantidadMinimaVolumen;

    Producto(String descripcion, int precioNormal, Integer precioPorVolumen, Integer cantidadMinimaVolumen) {
        this.descripcion = descripcion;
        this.precioNormal = precioNormal;
        this.precioPorVolumen = precioPorVolumen;
        this.cantidadMinimaVolumen = cantidadMinimaVolumen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecioNormal() {
        return precioNormal;
    }

    /**
     * Devuelve el precio unitario aplicable según la cantidad.
     * Usa precio por volumen solo si está definido y se cumple la cantidad mínima.
     */
    public int calcularPrecioUnitario(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (precioPorVolumen != null && cantidadMinimaVolumen != null && cantidad >= cantidadMinimaVolumen) {
            return precioPorVolumen;
        }
        return precioNormal;
    }
}

