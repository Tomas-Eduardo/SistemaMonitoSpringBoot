package cl.inacap.SistemaMonito.services;

import cl.inacap.SistemaMonito.models.Producto;
import cl.inacap.SistemaMonito.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public boolean validarProducto(String nombre) {
        if (productoRepository.findByNombre(nombre) != null) {
            return true;
        }
        return false;
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto createProducto(String nombre, String descripcion, double valor, int stock) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setValor(valor);
        producto.setStock(stock);
        return productoRepository.save(producto);
    }

    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    public Producto editProducto(Long id, String nombre, String descripcion, float valor, int stock) {
        Producto producto = findById(id);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setValor(valor);
        producto.setStock(stock);
        return productoRepository.save(producto);
    }

    public void deleteProducto(Producto producto) {
        productoRepository.delete(producto);
    }

    public List<Producto> findProductosByIds(List<String> productosIds) {
        // Convert the list of String to List of Long
        List<Long> productoIdsAsLong = productosIds.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        return productoRepository.findByIdIn(productoIdsAsLong);
    }


    public void save(Producto producto) {
        productoRepository.save(producto);
    }
}
