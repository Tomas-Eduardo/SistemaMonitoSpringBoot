package cl.inacap.SistemaMonito.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "empleados")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    @NotEmpty
    private String email;

    @NotEmpty
    private String rut;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RolEnum rol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(nombre, user.nombre) &&
                Objects.equals(apellido, user.apellido) &&
                Objects.equals(email, user.email) &&
                Objects.equals(rut, user.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, email);
    }
}
