package com.ecommerce.libreriasaintpatrick.dto.pedido;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDto {
    private Long id;
    private LocalDateTime fecha;
    private List<PedidoItemDto> items;
    private BigDecimal total;
}

