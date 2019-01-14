package crud.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import crud.backend.Journal;
import crud.backend.JournalRepository;

import org.vaadin.spring.events.EventBus;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@UIScope
@SpringComponent
public class JournalForm extends AbstractForm<Journal> {

    private static final long serialVersionUID = 1L;

    EventBus.UIEventBus eventBus;
    JournalRepository repo;


    TextField title = new MTextField("Title");
    TextField authors = new MTextField("Authors");
    TextField publisher = new MTextField("Publisher");
    DateField publication_date = new DateField("Publication date");
    TextField volume = new MTextField("Volume");
    TextField location = new MTextField("Location");

    JournalForm(JournalRepository r, EventBus.UIEventBus b) {
        this.repo = r;
        this.eventBus = b;

        // On save & cancel, publish events that other parts of the UI can listen
        setSavedHandler(journal -> {
            // persist changes
            repo.save(journal);
            // send the event for other parts of the application
            eventBus.publish(this, new JournalModifiedEvent(journal));
        });
        setResetHandler(p -> eventBus.publish(this, new JournalModifiedEvent(p)));

        setSizeUndefined();
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        title,
                        authors,
                        publisher,
                        publication_date,
                        volume,
                        location
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
