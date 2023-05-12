package com.developer.orders.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.orders.entity.Orders;

//@Transactional
public interface OrdersRepository extends JpaRepository<Orders, String> {
	
//	[JH] 튜터별 결제 목록 출력
	@Query(value ="select l.lesson_name, o.odate, o.order_id, o.order_seq"
			+ " from lesson l, orders o, tutor t"
			+ " where t.tutor_id = o.order_id"
			+ " and l.lesson_seq = o.or_lesson_seq"
			+ " and o.order_id = :orderId"
			+ " order by o.odate desc",
			nativeQuery = true)
	 public List<Object[]> getOrdersByOrderId(@Param("orderId") String orderId); 
	 
//	 [JH] 전체 결제 목록 출력
	 @Query(value = "Select *"
	 		+ " FROM ORDERS"
	 		+ " ORDER BY odate desc"
	 		, nativeQuery = true)
	 public List<Orders> selectAll();
	 
}