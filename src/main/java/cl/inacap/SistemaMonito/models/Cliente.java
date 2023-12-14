package cl.inacap.SistemaMonito.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Cliente")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rut", nullable = false, length = 50)
    @NotEmpty
    private String rut;

    @Column(name = "razonSocial", nullable = false, length = 50)
    @NotEmpty
    private String razonSocial;

    @Column(name = "giro", nullable = false, length = 50)
    @NotEmpty
    private String giro;

    @Column(name = "direccion", nullable = false, length = 50)
    @NotEmpty
    private String direccion;
}
