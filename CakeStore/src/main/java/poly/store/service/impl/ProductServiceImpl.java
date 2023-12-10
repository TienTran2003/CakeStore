package poly.store.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import poly.store.dao.ProductDAO;
import poly.store.entity.Product;
import poly.store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO dao;

	public Page<Product> findAll(Pageable page) {
		return dao.findAll((org.springframework.data.domain.Pageable) page);
	}

	public List<Product> findAll() {
		return dao.findAll();
	}
	
	public Product findById(Integer id) {
		return dao.findById(id).get();
	}

	public List<Product> findByCategoryId(String cid) {
		return dao.findByCategoryId(cid);
	}

	public Product create(Product product) {
		return dao.save(product);
	}

	public Product update(Product product) {
		return dao.save(product);
	}

	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Page<Product> findByKeywords(String keywords, Pageable pageable) {
		return dao.findByKeywords(keywords, pageable);
	}

	@Override
	public Page<Product> findByCategory(String id, Pageable pageable) {
		return dao.findByCategory(id, pageable);
	}
}
