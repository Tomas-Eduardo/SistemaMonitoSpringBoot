package cl.inacap.SistemaMonito.controllers;

import cl.inacap.SistemaMonito.models.*;
import cl.inacap.SistemaMonito.services.DetallePedidoService;
import cl.inacap.SistemaMonito.services.PedidoService;
import cl.inacap.SistemaMonito.services.ProductoService;
import cl.inacap.SistemaMonito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/jefe")
public class JefeController {
    @Autowired
    ProductoService productoService;
    @Autowired
    DetallePedidoService detallePedidoService;
    @Autowired
    UserService userService;
    @Autowired
    PedidoService pedidoService;

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    // Empleados

    @GetMapping("/allEmpleados")
    public String listEmpleados(Model model) {
        List<UserEntity> empleados = userService.findAll();
        model.addAttribute("empleados", empleados);
        return "jefe/empleados"; // Corregido el path de retorno
    }

    @GetMapping("/editEmp/{id}")
    public String editEmpleado(@PathVariable Long id, Model model) {
        UserEntity empleado = userService.findById(id);
        model.addAttribute("empleado", empleado);
        return "/jefe/editarEmpleado";
    }

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmpleado(@PathVariable Long id, Model model) {
        UserEntity empleado = userService.findById(id);
        userService.deleteEmpleado(empleado);
        return "redirect:/jefe/allEmpleados";
    }


    @GetMapping("/createEmpleado")
    public String crearEmpleado() {
        return "jefe/empleadoAdd";
    }

    @GetMapping("/createEmp")
    public String createEmpleado(@ModelAttribute UserEntity empleado,
                                @RequestParam String nombre,
                                @RequestParam String apellido,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String rut,
                                 @RequestParam String perfil,
                                Model model) {

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Missing data");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        if (userService.findByRut(rut) != null) {
            model.addAttribute("error", "Rut already in use");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email already in use");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        RolEnum rol = RolEnum.valueOf(perfil);

        UserEntity newEmpleado = userService.createUser(nombre, apellido, email, password, rut, rol);

        if (newEmpleado != null) {
            return "redirect:/jefe/allEmpleados"; // Redirige a una página de éxito
        } else {
            model.addAttribute("error", "Failed to create user");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }
    }



    @GetMapping("/editEmpleado")
    public String editEmpleado(@RequestParam Long id,
                               @RequestParam String nombre,
                               @RequestParam String apellido,
                               @RequestParam String email,
                               Model model) {

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            model.addAttribute("error", "Missing data");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        UserEntity existingEmpleado = userService.findById(id);

        if (existingEmpleado != null) {
            UserEntity editedEmpleado = userService.editEmpleado(id, nombre, apellido, email);

            if (editedEmpleado != null) {
                return "redirect:/jefe/allEmpleados"; // Redirige a una página de éxito
            } else {
                model.addAttribute("error", "Failed to edit user");
                return "error"; // Retorna a la página de edición con un mensaje de error
            }
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Retorna a la página de edición con un mensaje de error
        }
    }










    // Productos


    @GetMapping("/crear")
    public String crearProducto() {
        return "jefe/productosAdd";
    }

    @GetMapping("/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id);
        model.addAttribute("producto", producto);
        return "/jefe/editarProducto";
    }

    @GetMapping("/delete/{id}")
    public String deleteProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id);
        productoService.deleteProducto(producto);
        return "redirect:/jefe/allProductos";
    }


    @GetMapping("/allProductos")
    public String listProductos(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "jefe/productos"; // Corregido el path de retorno
    }


    @GetMapping("/create")
    public String createProducto(@ModelAttribute Producto producto,
                                @RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam float valor,
                                @RequestParam int stock,
                                Model model) {

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            model.addAttribute("error", "Missing data");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        if (productoService.validarProducto(nombre)) {
            model.addAttribute("error", "The name is already in use");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }


        Producto newProducto = productoService.createProducto(nombre, descripcion, valor, stock);

        if (newProducto != null) {
            return "redirect:/jefe/allProductos"; // Redirige a una página de éxito
        } else {
            model.addAttribute("error", "Failed to create product");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }
    }

    @GetMapping("/edit")
    public String editProducto(@RequestParam Long id,
                               @RequestParam String nombre,
                               @RequestParam String descripcion,
                               @RequestParam int stock,
                               @RequestParam float valor,
                               Model model) {

        if (stock < 0 || valor < 0) {
            model.addAttribute("error", "El stock o valor debe ser superior a 0");
            return "error"; // Retorna a la página de creación con un mensaje de error
        }

        Producto existingProducto = productoService.findById(id);

        if (existingProducto != null) {
            Producto editedProducto = productoService.editProducto(id, nombre, descripcion, valor, stock);

            if (editedProducto != null) {
                return "redirect:/jefe/allProductos"; // Redirige a una página de éxito
            } else {
                model.addAttribute("error", "Failed to edit product");
                return "jefe/editarProducto"; // Retorna a la página de edición con un mensaje de error
            }
        } else {
            model.addAttribute("error", "Product not found");
            return "jefe/editarProducto"; // Retorna a la página de edición con un mensaje de error
        }
    }

    // Ventas

    @GetMapping("/deleteV/{id}")
    public String deleteVenta(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        pedidoService.deletePedido(pedido);
        return "redirect:/jefe/allVentas";
    }

    @GetMapping("/allVentas")
    public String listVentas(Model model) {
        List<Pedido> pedidos = pedidoService.allPedidos();
        model.addAttribute("pedidos", pedidos);
        return "jefe/ventas"; // Corregido el path de retorno
    }

    @GetMapping("/createPedido")
    public String showCreatePedidoForm(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "jefe/ventaAdd";
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

        return "redirect:/jefe/allVentas"; // Redirige a una página de éxito
    }


}
