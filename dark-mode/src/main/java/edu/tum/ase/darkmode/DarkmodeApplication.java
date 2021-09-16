package edu.tum.ase.darkmode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class DarkmodeApplication {

	public static final long TOGGLE_COOLDOWN_MS = 3000;
	
	private boolean isCurrentlyDark = false;
	private long timeLastToggle = 0;
	
	public static void main(String[] args) {
		SpringApplication.run(DarkmodeApplication.class, args);
	}

	@RequestMapping(path = "/dark-mode/toggle", method = RequestMethod.GET)
	public void toggleDarkMode() {
		var currentTime = System.currentTimeMillis();
		if(currentTime - timeLastToggle > TOGGLE_COOLDOWN_MS) {			
			isCurrentlyDark = !isCurrentlyDark;
			timeLastToggle = currentTime;
		}
	}

	@RequestMapping(path = "dark-mode", method = RequestMethod.GET)
	public boolean isCurrentlyDark() {
		return isCurrentlyDark;
	}
}