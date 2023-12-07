package poly.store.service;

import poly.store.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    Double calculateTotalRevenue();

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);

    public OrderDetail findById(Long id) ;

}
