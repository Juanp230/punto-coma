package service;

import domain.Cliente;
import domain.Pedido;
import domain.Producto;

public class PapeleriaService {

    public Cliente registrarCliente(String nombre, String telefono) {
        return new Cliente(nombre, telefono);
    }

    public Pedido crearPedido(Cliente cliente) {
        return new Pedido(cliente);
    }

    public void agregarItem(Pedido pedido, Producto producto, int cantidad) {
        pedido.agregarItem(producto, cantidad);
    }

    public void calcularPedido(Pedido pedido) {
        pedido.calcularTotales();
    }

    public void confirmarPedido(Pedido pedido) {
        pedido.confirmar();
    }

    public String generarResumen(Pedido pedido) {
        return pedido.generarResumen();
    }
}
