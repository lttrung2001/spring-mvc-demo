package demo.controller;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.model.UploadFile;
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
	
	@Autowired
	UploadFile uploadFile;

	public User getUserByUsername(String username) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(String.format("FROM User WHERE username = '%s'", username));
		List<User> list = query.list();
		return list.get(0);
	}
	
	@RequestMapping(value = "login")
	public String getLoginPage(ModelMap model, 
								HttpServletRequest request, 
								@CookieValue(value = "username", defaultValue = "") String username,
								@CookieValue(value = "password", defaultValue = "") String password) {
//		User a = (User) request.getSession().getAttribute("account");
//		if (a == null) return "user/login";
		if (username.isEmpty() && password.isEmpty()) {
			return "user/login";
		}
		User a = new User();
		a.setUsername(username);
		a.setPassword(password);
		a.setEnabled(true);
		System.out.println(a.toString());
		for (User item : getUsers()) {
			if (a.getUsername().equals("admin") && a.getUsername().equals(item.getUsername())
					&& a.getPassword().equals(item.getPassword()) && item.isEnabled()) {
				model.addAttribute("users", getUsers());
				return "user/show-all";
			} else if (a.getUsername().equals(item.getUsername()) && a.getPassword().equals(item.getPassword())
					&& item.isEnabled()) {
				model.addAttribute("message", "Login success!");
				model.addAttribute("user", item);
				return "user/login-success";
			}
		}
		model.addAttribute("message", "Login failed!");
		return "user/login";
	}

	@RequestMapping(value = "register")
	public String getRegisterPage() {
		return "user/register";
	}

	@RequestMapping(value = "login", params = "btnLogin2")
	public String getLoginPage2(ModelMap model, 
								HttpServletRequest request, 
								@CookieValue(value = "username", defaultValue = "") String username,
								@CookieValue(value = "password", defaultValue = "") String password) {
		if (username.isEmpty() && password.isEmpty()) {
			return "user/login";
		}
		User a = new User();
		a.setUsername(username);
		a.setPassword(password);
		a.setEnabled(true);
		System.out.println(a.toString());
		for (User item : getUsers()) {
			if (a.getUsername().equals("admin") && a.getUsername().equals(item.getUsername())
					&& a.getPassword().equals(item.getPassword()) && item.isEnabled()) {
				model.addAttribute("users", getUsers());
				return "user/show-all";
			} else if (a.getUsername().equals(item.getUsername()) && a.getPassword().equals(item.getPassword())
					&& item.isEnabled()) {
				model.addAttribute("message", "Login success!");
				model.addAttribute("user", item);
				return "user/login-success";
			}
		}
		model.addAttribute("message", "Login failed!");
		return "user/login";
	}

	@RequestMapping(value = "register", params = "btnRegister2")
	public String getRegisterPage2() {
		return "user/register";
	}

	@RequestMapping(value = "register", params = "btnRegister", method = RequestMethod.POST)
	public String postRegisterPage(HttpServletRequest request, 
									ModelMap model, 
									@ModelAttribute("user") User user, 
									@RequestParam("avatar") MultipartFile avatar) {
		for (User item : getUsers()) {
			if (user.getUsername().equals(item.getUsername()) && user.isEnabled()) {
				model.addAttribute("message", "Username existed!");
				return "user/register";
			}
		}
		
		if (avatar.isEmpty()) {
			
		} else {
			try {
				String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
				String photo = date + avatar.getOriginalFilename();
				String photoPath = uploadFile.getBasePath() + File.separator + photo;
				avatar.transferTo(new File(photoPath));
				user.setPhoto(photo);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Loi upload file");
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
			return "<h1>Verify success!</h1>";
		}
	}

	@RequestMapping(value = "login", params = "btnLogin", method = RequestMethod.POST)
	public String postLoginPage(ModelMap model, @ModelAttribute("user") User user, 
								HttpServletRequest request, 
								HttpServletResponse response) {
		for (User item : getUsers()) {
			if (user.getUsername().equals("admin") && user.getUsername().equals(item.getUsername())
					&& user.getPassword().equals(item.getPassword()) && item.isEnabled()) {
				model.addAttribute("users", getUsers());
//				request.getSession().setAttribute("account", user);
				Cookie username = new Cookie("username", user.getUsername());
				Cookie password = new Cookie("password", user.getPassword());
				username.setMaxAge(Integer.MAX_VALUE);
				password.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(username);
				response.addCookie(password);
				return "user/show-all";
			} else if (user.getUsername().equals(item.getUsername()) && user.getPassword().equals(item.getPassword())
					&& item.isEnabled()) {
				model.addAttribute("message", "Login success!");
				model.addAttribute("user", item);
//				request.getSession().setAttribute("account", user);
				Cookie username = new Cookie("username", user.getUsername());
				Cookie password = new Cookie("password", user.getPassword());
				username.setMaxAge(Integer.MAX_VALUE);
				password.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(username);
				response.addCookie(password);
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
	
	@RequestMapping(value = "login", params = "lnkLogout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("username") || cookie.getName().equals("password")) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		return "user/login";
	}
}