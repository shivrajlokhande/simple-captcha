package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

	/* Method to generate Random Captcha String of length 4 */
	public String generateRandomCaptchaString() {
		SecureRandom random = new SecureRandom();
		int length = 4;

		StringBuffer captchaString = new StringBuffer();
		
		for (int i = 0; i < length; i++) {
			int baseCharNumber = Math.abs(random.nextInt()) % 62;
			int charNumber = 0;
			if (baseCharNumber < 26) {
				charNumber = 65 + baseCharNumber;
			}
			else if (baseCharNumber < 52){
				charNumber = 97 + (baseCharNumber - 26);
			}
			else {
				charNumber = 48 + (baseCharNumber - 52);
			}
			captchaString.append((char)charNumber);
		}
		return captchaString.toString();
	}

	/* Method to generate Captcha Image from String */
	public BufferedImage generateCaptchaImage(HttpSession session) throws IOException
	{
		int totalChars = 4;
		int height = 40;
		int weidth = 150;
		Font fontStyle = new Font("MONOSPACED", Font.BOLD, 36);
		Random randChars = new Random();
		String captchaString = this.generateRandomCaptchaString();
		session.setAttribute("captcha", captchaString);
		BufferedImage buffImage = new BufferedImage(weidth, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dImage = (Graphics2D) buffImage.getGraphics();
		g2dImage.setFont(fontStyle);
		
		for (int i = 0; i < totalChars; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			if (i % 2 == 0) {
				g2dImage.drawString(captchaString.substring(i, i + 1), 25 * i, 24);
			} else {
				g2dImage.drawString(captchaString.substring(i, i + 1), 25 * i, 35);
			}
		}
		
		return buffImage;
	}

	/* Method to Verify captcha String from client */
	public boolean verifyCaptcha(String clientString, HttpSession session)
	{
		String captchaString = (String)session.getAttribute("captcha");
		if(captchaString.equals(clientString))
			return true;
		return false;
	}
}
