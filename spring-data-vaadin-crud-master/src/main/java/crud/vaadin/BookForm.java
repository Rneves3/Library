package crud.vaadin;


import org.vaadin.spring.events.EventBus;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import crud.backend.Book;
import crud.backend.BookRepository;




@UIScope
@SpringComponent
public class BookForm extends AbstractForm<Book> {

    private static final long serialVersionUID = 1L;
    

    EventBus.UIEventBus eventBus;
    BookRepository repo;

    TextField title = new MTextField("Title");
    TextField author = new MTextField("Author");
    TextField publisher = new MTextField("Publisher");
    DateField date = new DateField("Date");
    TextField code = new TextField("Code");
    TextField location = new TextField("Location");

    BookForm(BookRepository r, EventBus.UIEventBus b) {
        this.repo = r;
        this.eventBus = b;

        // On save & cancel, publish events that other parts of the UI can listen
        setSavedHandler(book -> {
            // persist changes
            repo.save(book);
            // send the event for other parts of the application
            eventBus.publish(this, new BookModifiedEvent(book));
        });
        setResetHandler(p -> eventBus.publish(this, new BookModifiedEvent(p)));

        setSizeUndefined();
    }

    /*public BookForm() {

    }*/

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        title,
                        author,
                        publisher,
                        date,
                        code,
                        location
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
