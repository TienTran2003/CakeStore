package poly.store.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.store.entity.OrderDetail;
import poly.store.entity.Product;
import poly.store.service.OrderDetailService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/order_detail")
public class OrderDetailRestController {
    @Autowired
    private OrderDetailService order;

    @GetMapping("/total-revenue")
    public Double getTotalRevenue() {
        return order.calculateTotalRevenue();
    }

    @GetMapping("/{orderId}")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        return order.getOrderDetailsByOrderId(orderId);
    }

    @GetMapping("/top")
    public List<Product> getProductTop() {
        return order.getTopPurchasedProducts(10);
    }
}
