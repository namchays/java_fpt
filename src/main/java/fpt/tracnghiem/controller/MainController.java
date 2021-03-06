package fpt.tracnghiem.controller;

import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.tracnghiem.entity.Role;
import fpt.tracnghiem.entity.TaiKhoan;
import fpt.tracnghiem.model.AjaxResponseTaiKhoan;
import fpt.tracnghiem.service.RoleService;
import fpt.tracnghiem.service.TaiKhoanService;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
@Controller
public class MainController {
	
	/** The tai khoan service. */
	@Autowired
	TaiKhoanService taiKhoanService;
	
	/** The role service. */
	@Autowired 
	RoleService roleService;
	
	/**
	 * Home.
	 *
	 * @return the string
	 */
	@GetMapping("/")
	public String home(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			return "index";
		}
		else {
			return "redirect:/user/thi/page/1";
		}
		
	}
	
	/**
	 * Login form.
	 *
	 * @return the string
	 */
	@GetMapping("/login")
	public String loginForm() {
		
		return "/login";
	}
	
	/**
	 * Login.
	 *
	 * @param username the username
	 * @param password the password
	 * @param req the req
	 * @param res the res
	 * @return the string
	 * @throws NoSuchAlgorithmException 
	 */
	@PostMapping("/login")
	public String login(@ModelAttribute("taiKhoan") TaiKhoan taiKhoan,
			HttpServletRequest req,
			HttpServletResponse res,
			Model model) throws NoSuchAlgorithmException {
		
		String password = taiKhoan.getPassword();
		String encryptedPassword =taiKhoanService.md5("freshersalt", password);
		
		
		List<TaiKhoan> listTaiKhoan = taiKhoanService
				.findByUsernameAndPassword(taiKhoan.getUsername(), encryptedPassword);
		if(listTaiKhoan.size() > 0) {
			//luu vao session
			HttpSession session = req.getSession();
			session.setAttribute("user", listTaiKhoan.get(0));
			String nameAccount=listTaiKhoan.get(0).getRole().getRoleName();
			if(nameAccount.equals("ROLE_USER")) {
				return "redirect:/user/thi/page/1";
			}
			else if(nameAccount.equals("ROLE_ADMIN")){
				return "redirect:/user/thi/page/1";
			}
			else if(nameAccount.equals("ROLE_CREATER"))
			{
				return "redirect:/user/thi/page/1";
			}
		}
		model.addAttribute("hasError", "Sai th??ng tin ????ng nh???p.");
		return "login";
	}
	
	/**
	 * Register form.
	 *
	 * @param taiKhoan the tai khoan
	 * @return the string
	 */
	@GetMapping("/register")
	public String registerForm(TaiKhoan taiKhoan) {
		return "register";
	}
	
	/**
	 * Register.
	 *
	 * @param taiKhoan the tai khoan
	 * @return the string
	 */
	@PostMapping(value = "/register")
	@ResponseBody
	public ResponseEntity<?>  register(@Valid @RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		//validation data
		AjaxResponseTaiKhoan result = new AjaxResponseTaiKhoan();
		if(bindingResult.hasErrors()) {
			System.out.println("L???i validation");
			result.setMsg(bindingResult.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
			System.out.println(result.getMsg());
			return ResponseEntity.badRequest().body(result);
		}
		
		System.out.println(taiKhoan);
		
		//when success data
		taiKhoan.setEnable(true);
		Optional<Role> o = roleService.findByRoleName("ROLE_USER");
		Role role = o.get();
		taiKhoan.setRole(role);
		
		try {
			taiKhoanService.save(taiKhoan);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("Username ???? t???n t???i");
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	/**
	 * Commingsoon.
	 *
	 * @return the string
	 */
	@GetMapping("/commingsoon")
	public String commingsoon() {
		return "commingsoon";
	}
		
	/**
	 * Logout account.
	 *
	 * @param req the req
	 * @return the response entity
	 */
	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity<?> logoutAccount(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		return ResponseEntity.ok(null);
	}
	
}
