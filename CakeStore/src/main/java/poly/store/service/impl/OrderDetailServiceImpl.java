package poly.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.store.dao.OrderDetailDAO;
import poly.store.entity.OrderDetail;
import poly.store.service.OrderDetailService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private final OrderDetailDAO dao;

    public OrderDetailServiceImpl(OrderDetailDAO dao) {
        this.dao = dao;
    }


    public Double calculateTotalRevenue() {
        List<OrderDetail> orderDetails = dao.findAll();
        double totalRevenue = 0.0;

        for (OrderDetail orderDetail : orderDetails) {
            totalRevenue += orderDetail.getPrice() * orderDetail.getQuantity();
        }

        return totalRevenue;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return dao.findByOrder_Id(orderId);
    }

    @Override
    public OrderDetail findById(Long id) {
        return dao.findById(id).get();
    }



}
