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
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.Utils;
import demo.model.Product;
import net.sf.ehcache.hibernate.HibernateUtil;

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
		return "product/categories";
	}
}
