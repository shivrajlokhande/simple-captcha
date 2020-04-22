package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;
	
	/* GET Method to generate and send captcha to client */
	@RequestMapping(method = RequestMethod.GET,value = "/getCaptcha",produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getCaptcha(HttpSession session) throws IOException {

		BufferedImage captcha = captchaService.generateCaptchaImage(session);
        ByteArrayOutputStream byteOpStream = new ByteArrayOutputStream();
        ImageIO.write(captcha, "jpeg", byteOpStream);
        return byteOpStream.toByteArray();
	}

	/* POST Method to verify captcha string from client */
	@RequestMapping(method = RequestMethod.POST,value = "/verifyCaptcha")
	public @ResponseBody boolean verifyCaptcha(@RequestBody CaptchaVerifyDTO captchaDTO, HttpSession session)
	{
		return captchaService.verifyCaptcha(captchaDTO.getClientString(), session);
	}
}
