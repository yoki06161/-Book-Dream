package com.bookdream.sbb.admin;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import jakarta.validation.constraints.AssertFalse.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
	
	public OrderHistDto(Order order) {
		this.orderId = order.getId();
		this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd- HH:mm"));
		this.orderStatus = order.getOrderStatus();
	}
	
	private Long orderId;
	private String orderDate;
	private OrderStatus orderStatus;
	private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	public void addOrderItemDto(OrderItemDto orderItemDto) {
		orderItemDtoList.add(orderItemDto);
	}
}
