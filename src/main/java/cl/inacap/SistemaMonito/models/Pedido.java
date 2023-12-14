package cl.inacap.SistemaMonito.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rutCliente", nullable = false, length = 12)
    @NotEmpty
    private String rutCliente;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "tipoDocumento", nullable = false, length = 50)
    @NotEmpty
    private String tipoDocumento;

    private Float totalNeto;

    private Float iva;

    private float totalFinal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;


}
