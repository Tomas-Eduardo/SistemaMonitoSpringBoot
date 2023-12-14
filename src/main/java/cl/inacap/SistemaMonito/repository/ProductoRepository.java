package cl.inacap.SistemaMonito.repository;

import cl.inacap.SistemaMonito.models.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

    Producto findByNombre(String nombre);

    List<Producto> findByIdIn(List<Long> productosIds);
}
