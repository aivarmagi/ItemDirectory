package pl.tomaszdziurko.itemdirectory.web.view.locations;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.apache.wicket.validation.validator.StringValidator;

import pl.tomaszdziurko.itemdirectory.domain.entities.IEntity;
import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;
import pl.tomaszdziurko.itemdirectory.service.items.LocationService;

public class UniqueLocationNameValidator extends StringValidator {

    @SpringBean
    private LocationService locationService; // (1)

    private long locationIdToIgnore; // (2)

    public UniqueLocationNameValidator(long locationIdToIgnore) {
        this();
        this.locationIdToIgnore = locationIdToIgnore;
    }

    public UniqueLocationNameValidator() {
        InjectorHolder.getInjector().inject(this); // (3)
    }

    @Override
    protected void onValidate(IValidatable<String> validatable) { // (4)
        Location location = locationService.findByName(validatable.getValue());

        if(location != null && location.getId().longValue() != locationIdToIgnore) {
            error(validatable);
        }
    }
    /*
    1. As we will need service object to communicate with locations database table, we are injecting LocationService
    2. In the future, we will also use this validator to validate locations while editing them. Id of currently edited
    object will prevent validator from firing error when found object will have the same Id as edited one.
    Currently we will not use this feature.
    3. @SpringBean annotation is working without any other effort only in Wicket Component class and its children
    so when we want to use it in different class, we have to tell Wicket to auto inject beans here.
    4. onValidate() method is the core of validating class. We try to search for location with the same name as provided
    by user and if application finds one and its Id is different from edited entity object, we fire an error message.
     */
}