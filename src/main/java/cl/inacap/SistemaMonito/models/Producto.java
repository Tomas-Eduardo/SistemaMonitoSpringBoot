package cl.inacap.SistemaMonito.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.*;

// Producto.java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    @NotEmpty
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 50)
    @NotEmpty
    private String descripcion;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "stock", nullable = false)
    private int stock;
}