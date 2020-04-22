# simple-captcha
Implementation of Simple Captcha Assignment in Springboot

Assignment:

  An image with distorted text is shown to the user. 
  The user has to input the text value in a textbox and submit. 
  If the text input by the user is correct the service returns a pass else a fail.


I have created 2 REST APIs for this
first to generate and get captcha image
second to verify captcha string
** I have used session to map captcha image to captcha string

1.To generate and get Captcha

GET Request: http://localhost:8080/getCaptcha
this will return captcha image to client

2.To verify captcha string

POST Request: http://localhost:8080/verifyCaptcha
this will verify captcha string and return the result

Request Body for POST Request:
{
  "clientString":""
}
