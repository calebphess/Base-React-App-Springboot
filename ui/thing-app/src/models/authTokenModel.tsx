export default class AuthTokenModel 
{
  accessToken: string;
  tokenType: string;
  expires: Date;

  constructor() 
  {
    this.accessToken = '';
    this.tokenType = '';
    this.expires = new Date();
  }
}