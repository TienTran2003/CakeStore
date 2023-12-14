package poly.store.service;

import poly.store.entity.OrderDetail;
import poly.store.entity.Product;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDetailService {
    Double calculateTotalRevenue();

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);

    public OrderDetail findById(Long id) ;

    List<Product> getTopPurchasedProducts(int topCount);
}
