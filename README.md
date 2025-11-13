
# Papelería “Punto & Coma”

En Punto & Coma, estudiantes e independientes hacen encargos de impresión y anillado para recoger el mismo día. El sistema registra nombre y teléfono del cliente y permite crear un pedido con ítems de este catálogo: Impresión B/N ($200 c/u, o $150 c/u si son 100 o más), Impresión Color ($500 c/u, o $400 c/u si son 50 o más), y Anillado ($3.000 c/u, sin precio por volumen). El cálculo funciona así: primero determinar subtotales por ítem aplicando precio por volumen cuando corresponda; luego sumar el total bruto; después aplicar un solo beneficio: si el pedido incluye al menos un anillado y la suma de impresiones (B/N + Color) es ≥ 30, se aplica 10% de descuento; de lo contrario, si el total bruto > $40.000, aplicar 5% de descuento; si ninguna condición se cumple, no hay descuento. No se aceptan cantidades ≤ 0. Tras confirmar el pedido, queda bloqueado y el sistema debe mostrar un resumen con detalle (precio aplicado por ítem), total bruto, descuento y total final. No se gestiona inventario ni pagos: solo el flujo de crear → calcular → confirmar → resumir.

1.REQUERIMIENTOS FUNCIONALES
RF1. Registrar al cliente (nombre, teléfono).
RF2. agregar ITEM al pedido(producto cantidad)
RF3. Usar precio por volumen si la cantidad de Impresión B/N ≥ 100 o Impresión Color ≥ 50.
RF4. crear estado del pedido (en creacion ,confirmado ) 
RF5. Calcular total (Si incluye al menos un anillado y la suma de impresiones (B/N + Color) ≥ 30 → 10% de descuento.
- Si el total bruto > $40.000 → 5% de descuento.
- En caso contrario → sin descuento.)
RF6. CONFIRMAR pedido (cambia de en creacion a CONFIRMADO-no se puede editar )
RF7. validar cantidades ( < a 0 y totales que no sean negativos)
RF8.Listar resumen: cabecera (cliente y estado), detalle (precio normal o por volumen), subtotales, descuento y total final.



2. criterios de aceptacion 
CA1. el sistema solicita y almacena un nombre y telefono. no permite continuar si uno de los dos esta vacio.
CA2. Al crear un producto se pone en estado EN_CREACION
CA3. El sistema permite agregar 1 o varios ITEMS
CA4. Si el cliente solicita 100 o más impresiones en blanco y negro, el precio unitario cambia a $150.
- Si el cliente solicita 50 o más impresiones a color, el precio unitario cambia a $400.
- En los demás casos, se aplican los precios normales ($200 y $500 respectivamente).
CA5. Calculamos los subtotales por item pa los descuentos y eso (10% si hay al menos un anillado y ≥ 30 impresiones.
 • 5% si el total bruto supera $40.000 y no aplica el anterior.
 • Sin descuento si no cumple ninguna. el total final es totalbruto-descuento)
CA6. Al confirmar un producto el estado cambia a CONFIRMADO Y NO PUEDE MODIFICARSE
CA7.No se pueden aceptar cantidades menores a 0
CA8.Se muestra un resumen con los datos del cliente detalles del ITEMS(precio unitario y subtotal) y el total bruto


REGLAS 
RN1	Todo pedido debe estar asociado a un cliente con nombre y teléfono válidos.
RN2	Un pedido solo puede tener dos estados: EN_CREACION y CONFIRMADO.
RN3	Las cantidades de productos deben ser mayores que cero; no se permiten valores negativos o nulos.
RN4	Los precios de los productos son fijos y pertenecen al siguiente catálogo:
 • Impresión Blanco y Negro: $200 c/u (o $150 c/u si son ≥ 100).
 • Impresión Color: $500 c/u (o $400 c/u si son ≥ 50).
 • Anillado: $3.000 c/u (sin precio por volumen).
RN5	El sistema debe calcular los subtotales por cada ítem aplicando el precio por volumen solo si se cumple la cantidad mínima definida.
RN6	El total bruto es la suma de todos los subtotales antes de aplicar cualquier descuento.
RN7	Solo se puede aplicar un descuento por pedido, según estas condiciones de prioridad:
 1 Si el pedido incluye al menos un anillado y la suma de impresiones (B/N + Color) ≥ 30 → 10% de descuento.
 2 En caso contrario, si el total bruto > $40.000 → 5% de descuento.
 3 Si ninguna se cumple → sin descuento.
RN8	El total final del pedido se obtiene restando el descuento al total bruto.
RN9	Una vez que un pedido es confirmado, no puede ser editado, modificado ni eliminado.
RN10 El sistema no maneja inventario ni pagos; su alcance se limita al registro, cálculo y confirmación de pedidos.
RN11	El flujo es: Registrar cliente → Crear pedido → Agregar ítems → Calcular → Confirmar → Resumir.



arquitectura 
/punto-coma/
-src/
App.java
    /domain/
        -clientes.java
        -pedido.java
        -producto.java
        -estadoPedido.java
        -tipoProducto.java
        -itemPedido.java
    /Service/
        -ServicePapeleria.java    



ServicePapeleria