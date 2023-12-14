package cl.inacap.SistemaMonito.repository;

import cl.inacap.SistemaMonito.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findByRut(String rut);
}
