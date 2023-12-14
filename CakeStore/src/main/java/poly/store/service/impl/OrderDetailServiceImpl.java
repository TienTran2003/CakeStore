package poly.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.store.dao.OrderDetailDAO;
import poly.store.entity.OrderDetail;
import poly.store.entity.Product;
import poly.store.service.OrderDetailService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<Product> getTopPurchasedProducts(int topCount) {
        List<OrderDetail> orderDetails = dao.findAll();

        Map<Product, Long> productPurchaseCountMap = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getProduct, Collectors.summingLong(OrderDetail::getQuantity)));

        List<Product> topProducts = productPurchaseCountMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .limit(topCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topProducts;
    }

}
