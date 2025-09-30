package com.ecommerce.libreriasaintpatrick.mapper;

import com.ecommerce.libreriasaintpatrick.dto.pedido.PedidoDto;
import com.ecommerce.libreriasaintpatrick.dto.pedido.PedidoItemDto;
import com.ecommerce.libreriasaintpatrick.dto.producto.ProductoDto;
import com.ecommerce.libreriasaintpatrick.model.Pedido;
import com.ecommerce.libreriasaintpatrick.model.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class PedidoMapper {
    public PedidoDto mapToDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());

        List<PedidoItemDto> itemDtos = pedido.getItems().stream().map(item -> {
            PedidoItemDto itemDto = new PedidoItemDto();

            Producto producto = item.getProducto();
            if (producto != null) {
                ProductoDto pDto = new ProductoDto();
                pDto.setId(producto.getId());
                pDto.setNombre(producto.getNombre());
                itemDto.setProducto(pDto);
            } else {
                itemDto.setProducto(null);
            }

            BigDecimal precio = Optional.ofNullable(item.getPrecioUnitario()).orElse(BigDecimal.ZERO);
            itemDto.setPrecioUnitario(precio);

            int cantidad = Optional.ofNullable(item.getCantidad()).orElse(0);
            itemDto.setCantidad(cantidad);

            itemDto.setSubtotal(precio.multiply(BigDecimal.valueOf(cantidad)));

            return itemDto;
        }).toList();

        dto.setItems(itemDtos);
        dto.setTotal(itemDtos.stream()
                .map(PedidoItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }
}

