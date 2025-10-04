package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.dto.carrito.CarritoItemDto;
import com.ecommerce.libreriasaintpatrick.dto.pedido.ItemConProducto;
import com.ecommerce.libreriasaintpatrick.dto.pedido.PedidoDto;
import com.ecommerce.libreriasaintpatrick.dto.pedido.PedidoItemRequestDto;
import com.ecommerce.libreriasaintpatrick.dto.pedido.PedidoRequestDto;
import com.ecommerce.libreriasaintpatrick.mapper.PedidoMapper;
import com.ecommerce.libreriasaintpatrick.model.Pedido;
import com.ecommerce.libreriasaintpatrick.model.PedidoItem;
import com.ecommerce.libreriasaintpatrick.model.Producto;
import com.ecommerce.libreriasaintpatrick.model.Usuario;
import com.ecommerce.libreriasaintpatrick.repository.ProductoRepository;
import com.ecommerce.libreriasaintpatrick.repository.UsuarioRepository;
import com.ecommerce.libreriasaintpatrick.service.PedidoService;
import com.ecommerce.libreriasaintpatrick.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;




    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PedidoDto> crear(@RequestBody PedidoRequestDto request, Authentication auth) {

            // crear y salvar
            String email = auth.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


            Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuario.getId());
        pedido.setFecha(LocalDateTime.now());


        List<PedidoItem> items = construirItemsDesdeDto(request.getItems(), pedido);
        pedido.setItems(items);
        Pedido guardado = pedidoService.save(pedido);
            // recargar con EntityGraph para que items.producto venga cargado
            Pedido completo = pedidoService.findById(guardado.getId())
                    .orElseThrow(() -> new RuntimeException("Pedido recién creado no encontrado"));

            // mapear el pedido recargado, NO el original
            PedidoDto dto = pedidoMapper.mapToDto(completo);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PedidoDto>> listar(Authentication auth) {
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Pedido> pedidos = pedidoService.findByUsuario(usuario.getId());
        List<PedidoDto> dtos = pedidos.stream()
                .map(pedidoMapper::mapToDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.update(id, pedido);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/carrito/agregar")
    public ResponseEntity<Void> agregarAlCarrito(@RequestBody PedidoItemRequestDto itemDto, HttpSession session) {
        List<CarritoItemDto> carrito = (List<CarritoItemDto>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();

        Producto producto = productoRepository.findById(itemDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItemDto nuevoItem = new CarritoItemDto();
        nuevoItem.setProductoId(producto.getId());
        nuevoItem.setNombre(producto.getNombre());
        nuevoItem.setPrecioUnitario(producto.getPrecio());
        nuevoItem.setCantidad(itemDto.getCantidad());

        carrito.add(nuevoItem);
        session.setAttribute("carrito", carrito);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/carrito")
    public ResponseEntity<List<CarritoItemDto>> verCarrito(HttpSession session) {
        List<CarritoItemDto> carrito = (List<CarritoItemDto>) session.getAttribute("carrito");
        return ResponseEntity.ok(carrito != null ? carrito : new ArrayList<>());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/carrito/pagar")
    public ResponseEntity<PedidoDto> pagarCarrito(HttpSession session, Authentication auth) {
        List<CarritoItemDto> carrito = (List<CarritoItemDto>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuario.getId());
        pedido.setFecha(LocalDateTime.now());

        List<PedidoItem> items = construirItemsDesdeDto(carrito, pedido);
        pedido.setItems(items);

        Pedido guardado = pedidoService.save(pedido);
        Pedido completo = pedidoService.findById(guardado.getId())
                .orElseThrow(() -> new RuntimeException("Pedido recién creado no encontrado"));

        session.removeAttribute("carrito"); // 🧹 limpiar carrito

        PedidoDto dto = pedidoMapper.mapToDto(completo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    private List<PedidoItem> construirItemsDesdeDto(List<? extends ItemConProducto> itemDtos, Pedido pedido) {
        List<PedidoItem> items = new ArrayList<>();

        for (ItemConProducto itemDto : itemDtos) {
            if (itemDto.getProductoId() == null) {
                throw new IllegalArgumentException("El productoId no puede ser null");
            }

            Producto producto = productoRepository.findById(itemDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            PedidoItem item = new PedidoItem();
            item.setProducto(producto);
            item.setCantidad(itemDto.getCantidad());
            item.setPrecioUnitario(producto.getPrecio());
            item.setPedido(pedido);
            items.add(item);
        }
        return items;
    }
}