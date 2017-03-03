package fr.robot.twitterClient.web.common;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
	public void globalAttributes(Model model) {
	try {
			List<GrantedAuthority> list = (List<GrantedAuthority>) SecurityContextHolder.getContext()
					.getAuthentication().getAuthorities();

			for (GrantedAuthority grantedAuthority : list) {
				if(grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")){
					model.addAttribute("admin", "true");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
