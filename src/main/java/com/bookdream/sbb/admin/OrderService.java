//package com.bookdream.sbb.admin;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.bookdream.sbb.item.ItemImg;
//import com.bookdream.sbb.item.ItemImgRepository;
//import com.bookdream.sbb.order.Order; // Order 클래스가 도메인 모델임을 가정합니다.
//import com.bookdream.sbb.order.OrderRepository;
//import com.bookdream.sbb.order.dto.OrderHistDto;
//import com.bookdream.sbb.order.dto.OrderItemDto;
//import com.bookdream.sbb.user.MemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class OrderService {
//	
//	private final OrderRepository orderRepository;
//	private final ItemImgRepository itemImgRepository;
//
//	@Transactional(readOnly = true)
//	public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
//		List<Order> orders = orderRepository.findOrders(email, pageable);
//		Long totalCount = orderRepository.countOrder(email);
//
//		List<OrderHistDto> orderHistDtos = new ArrayList<>();
//		for (Order order : orders) {
//			OrderHistDto orderHistDto = new OrderHistDto(order);
//			order.getOrderItems().forEach(orderItem -> {
//				ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
//				OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
//				orderHistDto.addOrderItemDto(orderItemDto);
//			});
//			orderHistDtos.add(orderHistDto);
//		}
//		return new PageImpl<>(orderHistDtos, pageable, totalCount);
//	}
//}
//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
