package pl.tomaszdziurko.itemdirectory.web.view.locations;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import pl.tomaszdziurko.itemdirectory.web.BasePage;

public class AddLocationPage extends BasePage {

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
        final TextField<String> nameField = new TextField<String>("name");   // (3)
        addLocationForm.add(nameField);
        Button submitButton = new Button("submitButton") {          // (4)
            @Override
            public void onSubmit() {
                System.out.println("OnSubmit, name = " + name);
            }
        };
        addLocationForm.add(submitButton);
    }

}