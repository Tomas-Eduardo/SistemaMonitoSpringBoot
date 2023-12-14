package cl.inacap.SistemaMonito.repository;

import cl.inacap.SistemaMonito.models.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
