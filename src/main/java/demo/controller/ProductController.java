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
import demo.model.User;

@Transactional
@Controller	
@RequestMapping(value = "product")
public class ProductController {
	@Autowired
	private SessionFactory factory;
	
	@RequestMapping(value = "categories")
	public String getCategories(ModelMap model) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("from Product").setMaxResults(4);
		model.addAttribute("products", (List<Product>) query.list());
		
		// Test here
		User user = this.findUser("admin");
		user.getProducts().forEach(product -> System.out.println(product.toString()));
		
		return "product/categories";
	}
	
	@RequestMapping(value = "test")
	public String test() {
		return "product/test";
	}
	
	public User findUser(String id) {
		Query query = factory.getCurrentSession().createQuery(String.format("from User where username = '%s'", id));
		System.out.println(query.list().get(0).toString());
		return (User) query.list().get(0);
	}
}
