package com.zetech.thingapp.thingapp.biz;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.zetech.thingapp.thingapp.exceptions.NotAuthorizedException;
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
    if(!record.getUserLoginId().contentEquals("user") || !record.getUserPassword().contentEquals("password")) 
    {
      throw new NotAuthorizedException("Login Failed: Username or password invalid.");
    }
    
    EncryptedAuthTokenVO encryptedAuthTokenVO = new EncryptedAuthTokenVO();
    encryptedAuthTokenVO.setUserAuthToken("TEST_TOKEN");
    return encryptedAuthTokenVO;
  }

  public int delete(UserToken token,  HttpServletRequest request) throws ThingAppException 
  {
    // TODO: Finish this code
    return 1;
  }

  @Override
  public String authenticate(String token) throws ThingAppException 
  {
    // TODO properly validate the token
    
    // if there is no token, throw not authorized
    if(null == token || token.isEmpty()) 
    {
      throw new NotAuthorizedException("User is not logged in.");
    }
    // if the token is invalid, throw not authorized
    else if(!token.contentEquals("TEST_TOKEN")) 
    {
      throw new NotAuthorizedException("User auth token is invalid");
    }
    // otherwise return the userId
    else 
    {
      return "test-user@thingapp.com";
    }
  }

}