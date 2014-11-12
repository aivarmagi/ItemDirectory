package pl.tomaszdziurko.itemdirectory.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public abstract class BasePage extends WebPage {

	public BasePage() {
		super();
	}

	public BasePage(PageParameters parameters) {
		super(parameters);
	}
	
}
