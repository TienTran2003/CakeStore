package poly.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import poly.store.entity.OrderDetail;

import java.awt.print.Pageable;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
    List<OrderDetail> findByOrder_Id(Long orderId);

}