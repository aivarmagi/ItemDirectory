package pl.tomaszdziurko.itemdirectory.web.view;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.tomaszdziurko.itemdirectory.domain.dao.users.UserDao;
import pl.tomaszdziurko.itemdirectory.service.users.UserService;
import pl.tomaszdziurko.itemdirectory.web.BasePage;

public class HomePage extends BasePage {
	
	@SpringBean
	private UserService userService;

	public HomePage() {
		initGui();
	}

	private void initGui() {
		add(new Label("helloLabel", "Wicket is saying 'Hello' to you! :)"));
		add(new Label("currentTime", new Date().toString()));
		
		add(new Label("numberOfUsers", userService.size() +""));
	}

}
