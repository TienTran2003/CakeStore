package poly.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import poly.store.entity.Product;

public interface ProductService {
	public Page<Product> findAll(Pageable page) ;

	List<Product> findAll() ;
	
	public Product findById(Integer id) ;

	public List<Product> findByCategoryId(String cid) ;

	public Product create(Product product) ;

	public Product update(Product product) ;

	public void delete(Integer id) ;

	Page<Product> findByKeywords(String keywords, Pageable pageable);

	Page<Product> findByCategory(String id, Pageable pageable);
}
