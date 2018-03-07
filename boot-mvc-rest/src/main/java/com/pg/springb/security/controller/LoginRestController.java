package com.pg.springb.security.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pg.springb.front.documents.UserDocument;
import com.pg.springb.front.rest.helper.ApiCommonResponse;
import com.pg.springb.front.rest.helper.Error;
import com.pg.springb.front.rest.helper.LoginCred;
import com.pg.springb.front.rest.jwtsecured.JwtTokenProcessor;
import com.pg.springb.front.rest.jwtsecured.JwtUser;
import com.pg.springb.front.service.DBFacadeService;

@RestController
@RequestMapping("/api")
public class LoginRestController {
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
	JwtTokenProcessor jwtPros;

	@PostMapping("/login")
	public @ResponseBody ApiCommonResponse<JwtUser> doLogin(@RequestBody LoginCred lcred) throws IOException {
		
			String userId = lcred.getUsername();
			String password = lcred.getPassword();
			
			List<UserDocument> userList=dbFacadeService.findAllUsersByUserId(userId);		
			if(userList==null || userList.size()==0){
					return new ApiCommonResponse<JwtUser>(null,Error.NUM.set("-100"),Error.MSG.set("Invalid UserId"));
				}
			UserDocument loggedUser = null;
			for(UserDocument user:userList){
				if( (userId.equals(user.getUserId())) && (password.equals(user.getUserPassword())) ){
					loggedUser = user;
				} 
			}

			if(null != loggedUser) {
				JwtUser jwtUser = JwtUser.copyOf(loggedUser);
				
				String authToken = jwtPros.generateToken(jwtUser);
			
				jwtUser.setAuthToken(authToken);
				
				return new ApiCommonResponse<JwtUser>(jwtUser);
			}
		return new ApiCommonResponse<JwtUser>(null,Error.NUM.set("-100"),Error.MSG.set("Invalid user credential"));
	}
	
}
