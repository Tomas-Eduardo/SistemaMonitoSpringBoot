package cl.inacap.SistemaMonito.controllers;

import cl.inacap.SistemaMonito.models.DetallePedido;
import cl.inacap.SistemaMonito.models.Pedido;
import cl.inacap.SistemaMonito.models.Producto;
import cl.inacap.SistemaMonito.services.DetallePedidoService;
import cl.inacap.SistemaMonito.services.PedidoService;
import cl.inacap.SistemaMonito.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/delete/{id}")
    public String deleteVenta(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        pedidoService.deletePedido(pedido);
        return "redirect:/vendedor/allVentas";
    }

    @GetMapping("/detail/{id}")
    public String detailVenta(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        model.addAttribute("pedido", pedido);
        return "vendedor/ventaDetail";
    }

    @GetMapping("/createPedido")
    public String showCreatePedidoForm(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "vendedor/ventaAdd";
    }

    @GetMapping("/allProductos")
    public String listProductos(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "vendedor/productos"; // Corregido el path de retorno
    }


    // Listar ventas

    @GetMapping("/allVentas")
    public String listVentas(Model model) {
        List<Pedido> pedidos = pedidoService.allPedidos();
        model.addAttribute("pedidos", pedidos);
        return "vendedor/ventas"; // Corregido el path de retorno
    }

    @GetMapping("/createPed")
    @Transactional
    public String createPedido(@RequestParam String rutCliente,
                               @RequestParam String tipoDocumento,
                               @RequestParam List<String> productosIds,
                               @RequestParam List<Integer> cantidades,
                               Model model) {

        // Validaciones de entrada
        if (rutCliente.isEmpty() || tipoDocumento.isEmpty() || productosIds.isEmpty()) {
            model.addAttribute("error", "Faltan datos");
            return "error";
        }

        if (rutCliente.length() != 12) {
            model.addAttribute("error", "Rut inválido");
            return "error";
        }

        // Obtener la lista de productos seleccionados por sus IDs
        List<Producto> productosSeleccionados = productoService.findProductosByIds(productosIds);

        // Verificar el stock antes de crear el pedido
        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto producto = productosSeleccionados.get(i);
            int cantidad = cantidades.get(i);

            if (producto.getStock() < cantidad) {
                model.addAttribute("error", "Stock insuficiente para el producto: " + producto.getNombre());
                return "error";
            }
        }

        // Crear el pedido
        Pedido newPedido = new Pedido();
        newPedido.setRutCliente(rutCliente);
        newPedido.setTipoDocumento(tipoDocumento);
        newPedido.setFecha(new Date());

        // Guardar el pedido primero para obtener el ID
        Pedido savedPedido = pedidoService.save(newPedido);

        // Crear los detalles del pedido asociados al pedido guardado
        List<DetallePedido> detallesPedido = new ArrayList<>();
        double totalNeto = 0.0;

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto producto = productosSeleccionados.get(i);
            int cantidad = cantidades.get(i);

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(savedPedido); // Asociar al pedido guardado
            detallePedido.setProducto(producto);
            detallePedido.setCantidad(cantidad);
            detallesPedido.add(detallePedido);

            // Calcular el totalNeto en función de cada detalle
            totalNeto += producto.getValor() * cantidad;

            // Actualizar el stock del producto en la base de datos
            producto.setStock(producto.getStock() - cantidad);
            productoService.save(producto);
        }

        if (totalNeto > 0) {
            double iva = totalNeto * 0.19;
            double totalFinal = totalNeto + iva;

            newPedido.setTotalNeto((float) totalNeto);
            newPedido.setTotalFinal((float) totalFinal);
            newPedido.setIva((float) iva);
            System.out.println("Iva es: " + iva);
        } else {
            // En caso de que totalNeto sea 0 o negativo, asignar valores predeterminados
            newPedido.setTotalNeto(0.0f);
            newPedido.setTotalFinal(0.0f);
            newPedido.setIva(0.0f);
        }

        // Guardar los detalles del pedido asociados al pedido guardado
        savedPedido.setDetalles(detallesPedido);
        pedidoService.save(savedPedido);

        return "redirect:/vendedor/allVentas"; // Redirige a una página de éxito
    }
}
