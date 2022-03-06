package demo.controller;

import java.util.List;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.model.Product;

@Transactional
@Controller
public class ProductController {
	@Autowired
	SessionFactory factory; 
	
	@RequestMapping(value = "product/categories")
	public String getCategories(ModelMap model) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("from Product");
		model.addAttribute("products", (List<Product>) query.list());
		return "product/categories";
	}
}
