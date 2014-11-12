package pl.tomaszdziurko.itemdirectory.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import pl.tomaszdziurko.itemdirectory.web.view.HomePage;
import pl.tomaszdziurko.itemdirectory.web.view.locations.LocationsPage;

@Component(value = "wicketApplication")
public class Application extends WebApplication {

	private static final String DEFAULT_ENCODING = "UTF-8";
	
	@Autowired
    private ApplicationContext applicationContext;

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this, applicationContext, true));

		getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);
		getRequestCycleSettings().setResponseRequestEncoding(DEFAULT_ENCODING);

		mountBookmarkablePages();
		mountErrorLandingPages();
		getMarkupSettings().setStripWicketTags(true);

		if (getConfigurationType().equals(WebApplication.DEPLOYMENT)) {
			getMarkupSettings().setStripComments(true);
			getMarkupSettings().setCompressWhitespace(true);
		}

	}

	private void mountBookmarkablePages() {
		mountBookmarkablePage("locations", LocationsPage.class);
	}

	private void mountErrorLandingPages() {

	}

	@Override
	public String getConfigurationType() {
		return WebApplication.DEVELOPMENT;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public static Application get() {
		return (Application) WebApplication.get();
	}

}
