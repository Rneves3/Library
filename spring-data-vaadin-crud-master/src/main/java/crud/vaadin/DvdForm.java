package crud.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import crud.backend.Dvd;
import crud.backend.DvdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@UIScope
@SpringComponent
public class DvdForm extends AbstractForm<Dvd> {

    private static final long serialVersionUID = 1L;

    EventBus.UIEventBus eventBus;
    DvdRepository repo;

    TextField title = new MTextField("Title");
    TextField actors = new MTextField("Actors");
    TextField format = new MTextField("Format");
    TextField runtime = new TextField("Run Time");
    TextField location = new TextField("Location");

    DvdForm(DvdRepository r, EventBus.UIEventBus b) {
        this.repo = r;
        this.eventBus = b;

        // On save & cancel, publish events that other parts of the UI can listen
        setSavedHandler(dvd -> {
            // persist changes
            repo.save(dvd);
            // send the event for other parts of the application
            eventBus.publish(this, new DvdModifiedEvent(dvd));
        });
        setResetHandler(d -> eventBus.publish(this, new DvdModifiedEvent(d)));

        setSizeUndefined();
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        title,
                        actors,
                        format,
                        runtime,
                        location
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
