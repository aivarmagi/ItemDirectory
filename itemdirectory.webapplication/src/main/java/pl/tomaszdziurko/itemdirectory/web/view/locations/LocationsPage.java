package pl.tomaszdziurko.itemdirectory.web.view.locations;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;
import pl.tomaszdziurko.itemdirectory.service.items.LocationService;
import pl.tomaszdziurko.itemdirectory.web.BasePage;

import java.util.List;

public class LocationsPage extends BasePage {
    @SpringBean
    private LocationService locationService;
    public LocationsPage() {
        initGui();
    }
    private void initGui() {
        addLocationsModule();                               // (1)
        addCreateNewLocationLink();
    }

    private void addLocationsModule() {
        ListView<Location> locations = new ListView<Location>("locations", createModelForLocations()) { // (2)

            @Override
            protected void populateItem(final ListItem<Location> item) {             // (3)
                item.add(new Label("id", new PropertyModel<Location>(item.getModel(), "id")));    // (4)
                item.add(new Label("name", new PropertyModel<Location>(item.getModel(), "name")));

            Link<BasePage> editLocationLink = new Link<BasePage>("editLocationLink") {
                @Override
                public void onClick() {
                    setResponsePage(new AddEditLocationPage(item.getModelObject().getId()));
                }
            };

                item.add(editLocationLink);
                item.add(new RemoveLocationLink("removeLocationLink", item.getModelObject()));
            }
        };

        locations.setVisible(!locations.getList().isEmpty());
        add(locations);

        Label noLocationsLabel = new Label("noLocationLabel", "There are no locations in the database. Maybe you can add one?");
        noLocationsLabel.setVisible(!locations.isVisible());
        add(noLocationsLabel);
    }

    private LoadableDetachableModel<List<Location>> createModelForLocations() {    // (5)

        return new LoadableDetachableModel<List<Location>>() {    // (6)

            @Override
            protected List<Location> load() {    // (7)
                return locationService.findAll();
            }
        };
    }

    /*
    In (1) we add new method to initGui(), in (2) we create ListView component which renders list of object in a selected way.
    In (2) you can also notice a createModelForLocations() method invocation, but this method will be covered a little later.
    ListView class has one method we have to implement: populateItem (3) which is called for each object from collections
    or collection model passed to ListView.
    In this method we must add two labels declared inside locations element in HTML file, and we do it in (4). In this
    method we must add two labels declared inside locations element in HTML file, and we do it in (4).
     */



    private void addCreateNewLocationLink() {
        add(new Link<BasePage>("addLocationPageLink") {

            @Override
            public void onClick() {
                setResponsePage(new AddEditLocationPage());
            }
        });
    }
}