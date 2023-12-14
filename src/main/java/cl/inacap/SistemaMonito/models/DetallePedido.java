package cl.inacap.SistemaMonito.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table(name = "DetallePedido")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @NotNull(message = "La cantidad no puede estar vacia")
    private int cantidad;


    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", pedido=" + (pedido != null ? pedido.getId() : null) +
                ", producto=" + (producto != null ? producto.getId() : null) +
                ", cantidad=" + cantidad +
                '}';
    }


}
