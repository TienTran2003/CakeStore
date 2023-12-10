package poly.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.store.entity.Product;
import poly.store.service.ProductService;
import poly.store.service.SessionService;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	SessionService sessionService;

	@RequestMapping("/product/list")
	public String list(Model model, @RequestParam("p") Optional<Integer> p, @RequestParam("cid") Optional<String> cid) {
		if (cid.orElse("").isEmpty()) {
			// Adjust page size as needed
			Pageable pageable = PageRequest.of(p.orElse(0), 8);
			Page<Product> page = productService.findAll(pageable);

			int currentPage =1;
			int totalItems = page.getNumberOfElements();
			int totalPages = page.getTotalPages();

			model.addAttribute("totalItems", totalItems);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("page", page);
		} else {
			List<Product> list = productService.findByCategoryId(cid.get());
			model.addAttribute("items", list);
		}

		return "product/shop";
	}
		
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		return "product/detail";
	}

	@RequestMapping("/search")
	public String searchAndPage(Model model, @RequestParam("keywords") Optional<String> kw,
								@RequestParam("p") Optional<Integer> p) {
		String kwords = kw.orElse(sessionService.get("keywords"));
		sessionService.set("keywords", kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> page = productService.findByKeywords("%" + kwords + "%", pageable);
		int currentPage =1;
		int totalItems = page.getNumberOfElements();
		int totalPages = page.getTotalPages();

		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("page", page);
		return "product/shop";
	}
}