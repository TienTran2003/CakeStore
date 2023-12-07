package poly.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.store.entity.Product;
import poly.store.service.ProductService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	@RequestMapping({"/", "/home", "/home/index"})
	public String index(Model model) {
		List<Product> list = productService.findAll();
		model.addAttribute("items", list);
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
