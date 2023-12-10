package poly.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.store.entity.Product;
import poly.store.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	@RequestMapping({"/", "/home", "/home/index"})
	public String index(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> page = productService.findAll(pageable);
		model.addAttribute("page", page);
		return "product/list";
	}
	@RequestMapping("/about")
	public String about() {
		return "layout/about";
	}
	
	@RequestMapping("/admin/home/index")
	public String admin() {
		return "redirect:/admin/index.html";
	}
}
