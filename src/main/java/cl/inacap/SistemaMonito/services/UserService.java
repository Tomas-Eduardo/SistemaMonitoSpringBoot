package cl.inacap.SistemaMonito.services;

import cl.inacap.SistemaMonito.models.Producto;
import cl.inacap.SistemaMonito.models.RolEnum;
import cl.inacap.SistemaMonito.models.UserEntity;
import cl.inacap.SistemaMonito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserEntity> allUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    // API para registrar un usuario
    public void registrarUsuarioApi(UserEntity user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        // Guardar el usuario en la base de datos
        userRepository.save(user);
    }

    // Metodo registrar usuarios
    public UserEntity createUser(String nombre, String apellido, String email, String password, String rut, RolEnum rol) {
        UserEntity user = new UserEntity();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRut(rut);
        user.setRol(rol);

        return userRepository.save(user);
    }


    public List<UserEntity> findAll() {
        return (List<UserEntity>) userRepository.findAll();
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByRut(String rut) {
        return userRepository.findByRut(rut);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity editEmpleado(Long id, String nombre, String apellido, String email) {
        UserEntity empleado = userRepository.findById(id).orElse(null);
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setEmail(email);
        return userRepository.save(empleado);
    }

    public void deleteEmpleado(UserEntity empleado) {
        userRepository.delete(empleado);
    }
}
