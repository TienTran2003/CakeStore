package poly.store.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;

import poly.store.entity.Order;
import poly.store.entity.Product;
import poly.store.service.OrderService;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;

	@GetMapping
	public List<Order> getAll(){
		return orderService.findAll();
	}

	@GetMapping("/status")
	public List<Order> getByStatus(){
		return orderService.findByStatus();
	}
	
	@PostMapping
	public Order purchase(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}

	@GetMapping("/total")
	public int getTotal() {
		List<Order> orders = orderService.findAll();
		int total = orders.size();
		return total;
	}

	@GetMapping("/{id}")
	public Order findById(@PathVariable("id") long id){
		return orderService.findById(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id){
		orderService.delete(id);
	}

	@PutMapping("{id}")
	public Order update(@PathVariable("id") Long id, @RequestBody Order order) {
		return orderService.update(order);
	}

//	@GetMapping("/revenue-by-month")
//	public ResponseEntity<Map<YearMonth, Double>> calculateRevenueByMonth() {
//		Map<YearMonth, Double> revenueByMonth = orderService.calculateRevenueByMonth();
//		return ResponseEntity.ok(revenueByMonth);
//	}

}
