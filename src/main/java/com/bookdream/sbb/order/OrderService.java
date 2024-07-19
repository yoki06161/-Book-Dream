package com.bookdream.sbb.order;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.user.MemberRepository;

import jakarta.persistence.criteria.Order;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	
	@Transactional(readOnly = true)
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
		
		List<Order> orders = orderRepository.findOrders(email, pageable);
		Long totalCount = orderRepository.countOrder(email);
		
		List<OrderHisDto> orderHistDtos = new ArrayList<>();
		
		for (Order order : orders) {
			orderHistDtos orderHisDto = new OrderHistDto(order);
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem = ItemImgRepository.findbyItemIdAndRepimgYn
					ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
						(orderItem.getItem().getId(), "Y");
					OrderItemDto orderItemDto = 
							new OrderItemDto(orderItem, itemImg.getImgUrl());
					orderHistDto.addOrderItemDto(orderItemDto);
			}
		orderHistDtos.add(orderHistDtos);
		}
	return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	}
}
