package com.ecommerce.libreriasaintpatrick.dto.pedido;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDto {
    private List<PedidoItemRequestDto> items;
}

