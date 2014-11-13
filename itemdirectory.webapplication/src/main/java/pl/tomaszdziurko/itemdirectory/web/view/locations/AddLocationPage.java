package pl.tomaszdziurko.itemdirectory.web.view.locations;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import pl.tomaszdziurko.itemdirectory.domain.dao.items.LocationDao;
import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;
import pl.tomaszdziurko.itemdirectory.service.items.LocationService;
import pl.tomaszdziurko.itemdirectory.web.BasePage;
import pl.tomaszdziurko.itemdirectory.web.custom.UniqueEntityValidator;

public class AddLocationPage extends BasePage {

    private static final int MIN_LOCATION_NAME_LENGTH=5;
    private static final int MAX_LOCATION_NAME_LENGTH=255;

    @SpringBean
    private LocationService locationService;
    @SpringBean
    private LocationDao locationDao;

    private String name;
    public AddLocationPage() {
        initGui();
    }

    private void initGui() {
        Form<AddLocationPage> addLocationForm = new Form<AddLocationPage>("addLocationForm",
                new CompoundPropertyModel<AddLocationPage>(this));  // (1)
        add(addLocationForm);
        Label nameLabel = new Label("nameLabel", "Location name");    // (2)
        addLocationForm.add(nameLabel);

        RequiredTextField<String> nameField = new RequiredTextField<String>("name");
        nameField.setLabel(new Model<String>("Name"));
        nameField.add(StringValidator.LengthBetweenValidator.lengthBetween(MIN_LOCATION_NAME_LENGTH, MAX_LOCATION_NAME_LENGTH));

        UniqueEntityValidator<String> locationNameValidator = new UniqueEntityValidator<String>(locationDao, "name");
        nameField.add(locationNameValidator);

        addLocationForm.add(nameField); // (3)

        Button submitButton = new Button("submitButton") {          // (4)
            @Override
            public void onSubmit() {
                Location location = new Location(name);
                locationService.save(location);

                getSession().info("Location added successfully");
                setResponsePage(LocationsPage.class);
                System.out.println("OnSubmit, name = " + name);
            }
        };
        addLocationForm.add(submitButton);
    /*
    In (1) we create Wicket form with model to gather data from user. We use CompoundPropertyModel which automatically
    connects components with properties of model object with the same id/name. In our page model object is AddLocationPage
    itself and its name field (with usage of Compound model) works as a model for TextField component with id name.
    In (2), (3) and (4) we create and add to our form: label for input field, input field (note that its called name,
    as property in page class) and submit button with overriden onSubmit method in which we place save logic.
     */
    }

}