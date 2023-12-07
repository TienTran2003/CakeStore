package poly.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.store.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
    List<OrderDetail> findByOrder_Id(Long orderId);
}