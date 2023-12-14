package poly.store.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import poly.store.entity.Order;

public interface OrderService {
	List<Order> findAll();

	public Order create(JsonNode orderData) ;
	
	public Order findById(Long id) ;
	
	public List<Order> findByUsername(String username) ;

	Object delete(Long id);

	Order update(Order order);

	public List<Order> findByStatus();

}
