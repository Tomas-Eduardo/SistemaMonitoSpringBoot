package cl.inacap.SistemaMonito.services;

import cl.inacap.SistemaMonito.models.DetallePedido;
import cl.inacap.SistemaMonito.models.Pedido;
import cl.inacap.SistemaMonito.models.Producto;
import cl.inacap.SistemaMonito.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> allDetallePedido() {
        return (List<DetallePedido>) detallePedidoRepository.findAll();
    }

    public void createDetallePedido(Pedido pedido, List<Producto> productosSeleccionados) {
        for (Producto producto : productosSeleccionados) {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(producto);
            detallePedidoRepository.save(detallePedido);
        }
    }

    public void saveAll(List<DetallePedido> detallesPedido) {
        detallePedidoRepository.saveAll(detallesPedido);
    }
}
