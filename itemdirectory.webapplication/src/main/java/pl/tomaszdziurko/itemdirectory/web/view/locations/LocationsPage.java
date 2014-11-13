package pl.tomaszdziurko.itemdirectory.web.view.locations;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.tomaszdziurko.itemdirectory.service.items.LocationService;
import pl.tomaszdziurko.itemdirectory.web.BasePage;

public class LocationsPage extends BasePage {
    @SpringBean
    private LocationService locationService;
    public LocationsPage() {
        initGui();
    }
    private void initGui() {
        add(new Label("numberOfLocations", locationService.size() + ""));
        add(new Link<BasePage>("addLocationPageLink") {
            @Override
            public void onClick() {
                setResponsePage(new AddLocationPage());
            }
        });
    }
}