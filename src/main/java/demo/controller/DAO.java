package demo.controller;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import demo.model.Product;

public class DAO {

	public DAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	SessionFactory factory;
	
	public List<Product> getProducts () {
		Query query = factory.getCurrentSession().createQuery("from Product");
		List<Product> result = query.list();
		return result;
	}
	
}
