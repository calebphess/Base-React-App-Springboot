package com.zetech.thingapp.thingapp.biz;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuthRequestVO;
import com.zetech.thingapp.thingapp.model.EncryptedAuthTokenVO;
import com.zetech.thingapp.thingapp.security.UserToken;


@Service
public class AuthService implements AuthServiceInterface
{

  public EncryptedAuthTokenVO create(AuthRequestVO record,  HttpServletRequest request) throws ThingAppException 
  {
    // TODO: implement code to create JWT token and UserAuthTokenVO to write to database
    // TODO: write code to convert the UserAuthTokenVO to JSON and then sign it and return the encrypted string
    EncryptedAuthTokenVO encryptedAuthTokenVO = new EncryptedAuthTokenVO();
    encryptedAuthTokenVO.setUserAuthToken("TEST_TOKEN");
    return encryptedAuthTokenVO;
  }

  public int delete(UserToken token,  HttpServletRequest request) throws ThingAppException 
  {
    // TODO: Finish this code
    return 1;
  }

}