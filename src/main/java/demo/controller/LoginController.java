package demo.controller;

import java.util.List;


import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.model.Product;
import demo.model.User;

@Transactional
@Controller
public class LoginController {
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailSender;

	public User getUserByUsername(String username) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(String.format("FROM User WHERE username = '%s'", username));
		List<User> list = query.list();
		return list.get(0);
	}
	
	@RequestMapping(value = "login")
	public String getLoginPage() {
		return "user/login";
	}

	@RequestMapping(value = "register")
	public String getRegisterPage() {
		return "user/register";
	}

	@RequestMapping(value = "login", params = "btnLogin2")
	public String getLoginPage2() {
		return "user/login";
	}

	@RequestMapping(value = "register", params = "btnRegister2")
	public String getRegisterPage2() {
		return "user/register";
	}

	@RequestMapping(value = "register", params = "btnRegister", method = RequestMethod.POST)
	public String postRegisterPage(HttpServletRequest request, ModelMap model, @ModelAttribute("user") User user) {
		for (User item : getUsers()) {
			if (user.getUsername().equals(item.getUsername()) && user.isEnabled()) {
				model.addAttribute("message", "Username existed!");
				return "user/register";
			}
		}
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath("demo").build()
					.toUriString();

			// Random number function - BEGIN
			String randomCode = "";
			int random_int;
			int min = 'a';
			int max = 'z';
			for (int i = 0; i < 64; i++) {
				random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
				randomCode += (char) random_int;
			}
			// Random number function - END

			user.setToken(randomCode);
			user.setEnabled(false);
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("no-reply-email");
			helper.setTo(user.getEmail());
			helper.setSubject("Test");
			helper.setText(baseUrl + "/verify.htm?code=" + user.getToken());
			mailSender.send(message);

			session.delete(user);
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message", "Register success!");
		model.addAttribute("user", user);
		return "user/register-success";
	}

	@RequestMapping(value = "verify.htm")
	@ResponseBody
	public String verify(@RequestParam(value = "code") String token) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM User AS u WHERE u.token = :token").setParameter("token", token);
		User userVerify = (User) query.list().get(0);
		if (userVerify == null || userVerify.isEnabled()) {
			return "Error!";
		} else {
			userVerify.setEnabled(true);
			userVerify.setToken("");
			session.update(userVerify);
			return "Verify success!";
		}
	}

	@RequestMapping(value = "login", params = "btnLogin", method = RequestMethod.POST)
	public String postLoginPage(ModelMap model, @ModelAttribute("user") User user) {
		for (User item : getUsers()) {
			if (user.getUsername().equals("admin") && user.getUsername().equals(item.getUsername())
					&& user.getPassword().equals(item.getPassword()) && item.isEnabled()) {
				model.addAttribute("users", getUsers());
				return "user/show-all";
			} else if (user.getUsername().equals(item.getUsername()) && user.getPassword().equals(item.getPassword())
					&& item.isEnabled()) {
				model.addAttribute("message", "Login success!");
				model.addAttribute("user", item);
				return "user/login-success";
			}
		}
		model.addAttribute("message", "Login failed!");
		return "user/login";
	}

	public List<User> getUsers() {
		Session session = factory.getCurrentSession();
		// Using HQL
		String hql = "FROM User";
		Query query = session.createQuery(hql);
		List<User> myList = query.list();
		return myList;
	}

	@RequestMapping(value = "{username}.htm", params = "linkEdit")
	public String getEditUser(ModelMap model, @PathVariable("username") String username) {
		model.addAttribute("user", getUserByUsername(username));
		return "user/edit";
	}

	@RequestMapping(value = "show-all", params = "btnEdit", method = RequestMethod.POST)
	public String postEditUser(ModelMap model, @ModelAttribute("user") User user) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			User userFromData = (User) session.createQuery("FROM User WHERE username = :username")
					.setParameter("username", user.getUsername()).list().get(0);
			userFromData.setPassword(user.getPassword());
			userFromData.setEmail(user.getEmail());
			session.update(userFromData);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			model.addAttribute("message", "Error while editing!");
		} finally {
			session.close();
		}
		return "user/login";
	}

	@RequestMapping(value = "{username}.htm", params = "linkDelete")
	public String deleteUser(ModelMap model, @ModelAttribute("user") User user) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(user);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("users", getUsers());
		return "user/show-all";
	}
}