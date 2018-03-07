package com.pg.springb.front.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pg.springb.front.documents.UserDocument;
import com.pg.springb.front.service.DBFacadeService;

@Controller
@RequestMapping("/frontier")
public class LoginController {
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session=request.getSession();
		if(session==null) return new ModelAndView("loginForm");
		
		Object loggedinUserId=session.getAttribute("userId");
		if(loggedinUserId !=null){
			return new ModelAndView("uploadForm","userId","Signed in as:"+loggedinUserId.toString());
		}

		return new ModelAndView("loginForm");
	}
	
	@PostMapping("/login")
	public ModelAndView doLogin(HttpServletRequest request, @RequestParam("username") String userId, @RequestParam("password") String password) throws IOException {
		
		List<UserDocument> userList=dbFacadeService.findAllUsersByUserId(userId);		
		if(userList==null || userList.size()<=0)
				return new ModelAndView("loginForm","message", "Invalid UserId:"+userId);
		UserDocument user=userList.get(0);
		if(userId.equals(user.getUserId()) && password.equals(user.getUserPassword())){
			request.getSession().setAttribute("userId", userId);
			return new ModelAndView("uploadForm");
			}
		return new ModelAndView("loginForm","message", "Incorrect UserId or Password.");
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session=request.getSession();
		if(session!=null) 
		{
			Object userId=session.getAttribute("userId");
			if(userId !=null){
				session.removeAttribute(userId.toString());
				session.invalidate();
			}
		}
		return new ModelAndView("loginForm");
	}
}
