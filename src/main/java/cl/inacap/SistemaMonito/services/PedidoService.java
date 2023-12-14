package cl.inacap.SistemaMonito.services;

import cl.inacap.SistemaMonito.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.inacap.SistemaMonito.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    public List<Pedido> allPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Pedido createPedido(String rutCliente, String tipoDocumento, float totalNeto, float totalFinal, float iva, Date fecha) {
        Pedido pedido = new Pedido();
        pedido.setRutCliente(rutCliente);
        pedido.setTipoDocumento(tipoDocumento);
        pedido.setTotalNeto(totalNeto);
        pedido.setTotalFinal(totalFinal);
        pedido.setIva(iva);
        pedido.setFecha(fecha);
        return pedidoRepository.save(pedido);
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deletePedido(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }

    public void updatePedido(Pedido newPedido) {
        Pedido pedido = pedidoRepository.findById(newPedido.getId()).orElse(null);
        pedido.setRutCliente(newPedido.getRutCliente());
        pedido.setTipoDocumento(newPedido.getTipoDocumento());
        pedido.setTotalNeto(newPedido.getTotalNeto());
        pedido.setTotalFinal(newPedido.getTotalFinal());
        pedido.setIva(newPedido.getIva());
        pedido.setFecha(newPedido.getFecha());
        pedidoRepository.save(pedido);
    }

    public Pedido save(Pedido newPedido) {
        return pedidoRepository.save(newPedido);
    }
}
