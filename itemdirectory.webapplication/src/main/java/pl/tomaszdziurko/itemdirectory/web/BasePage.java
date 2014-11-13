package pl.tomaszdziurko.itemdirectory.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import pl.tomaszdziurko.itemdirectory.web.view.locations.LocationsPage;

public abstract class BasePage extends WebPage {

	public BasePage() {
		super();
		initGui();
	}

	public BasePage(PageParameters parameters) {
		super(parameters);
		initGui();
	}


	private void initGui(){
		addTopMenuLinks();
		add(new FeedbackPanel("feedbackPanel"));
	}

	private void addTopMenuLinks(){
		add(new BookmarkablePageLink<BasePage>("homePageLink", Application.get().getHomePage()));
		add(new BookmarkablePageLink<BasePage>("locationsPageLink", LocationsPage.class));
	}
}
