package crud.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import crud.backend.Book;
import crud.backend.BookRepository;
import crud.backend.Journal;
import crud.backend.JournalRepository;
import crud.backend.Dvd;
import crud.backend.DvdRepository;

import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.components.DisclosurePanel;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;

@Title("Innovative Library")
@Theme("valo")
@SpringUI
public class MainUI extends UI {

    private static final long serialVersionUID = 1L;
    EventBus.UIEventBus eventBus;

    //Repositories
    JournalRepository repo;
    BookRepository repoBook;
    DvdRepository repoDvd;

    //Forms to change the content
    BookForm bookForm;
    JournalForm journalForm;
    DvdForm dvdForm;



    // Tables to display the content of each type of product
    private MTable<Journal> list = new MTable<>(Journal.class)
            .withProperties( "title", "authors")
            .withColumnHeaders( "Title", "Authors")
            .setSortableProperties("title", "authors")
            .withFullWidth();
    
    private MTable<Book> listBooks = new MTable<>(Book.class)
            .withProperties( "title", "publisher")
            .withColumnHeaders( "Title", "Publisher")
            .setSortableProperties("title", "publisher")
            .withFullWidth();

    private MTable<Dvd> listDvds = new MTable<>(Dvd.class)
            .withProperties( "title", "actors")
            .withColumnHeaders( "Title", "Actors")
            .setSortableProperties("title", "actors")
            .withFullWidth();

    //Buttons and fields to manage the content of each product [add, edit, delete, filter and view]
    private TextField filterByJournalTitle = new MTextField()
            .withInputPrompt("Filter by Journal title");
    private Button addNewJournal = new MButton(FontAwesome.PLUS, this::addJournal);
    private Button editJournal = new MButton(FontAwesome.PENCIL_SQUARE_O, this::editJournal);
    private Button viewJournal = new MButton(FontAwesome.TOGGLE_RIGHT, this::viewJournal);
    private Button deleteJournal = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete this Journal", this::removeJournal);
    
    private TextField filterByBookTitle = new MTextField()
            .withInputPrompt("Filter by Book Title");
    private Button addNewBook = new MButton(FontAwesome.PLUS, this::addBook);
    private Button editBook = new MButton(FontAwesome.PENCIL_SQUARE_O, this::editBook);
    private Button viewBook = new MButton(FontAwesome.TOGGLE_RIGHT, this::viewBook);
    private Button deleteBook = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete this Book?", this::removeBook);


    private TextField filterByDvdTitle = new MTextField()
            .withInputPrompt("Filter by Dvd Title");
    private Button addNewDvd = new MButton(FontAwesome.PLUS, this::addDvd);
    private Button editDvd = new MButton(FontAwesome.PENCIL_SQUARE_O, this::editDvd);
    private Button viewDvd = new MButton(FontAwesome.TOGGLE_RIGHT, this::viewDvd);
    private Button deleteDvd = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete this DVD?", this::removeDvd);


    //Menu Bar and items
    private MenuBar menuBar = new MenuBar();
    private MenuBar.MenuItem journal = menuBar.addItem("Journals", null, null);
    private MenuBar.MenuItem dvds = menuBar.addItem("DVDs", null, null);
    private MenuBar.MenuItem books = menuBar.addItem("Books", null, null);
    private MenuBar.MenuItem admin = menuBar.addItem("Administration Panel", null, null);


    public MainUI(EventBus.UIEventBus b, JournalRepository r, JournalForm f, BookRepository rp, BookForm bf, DvdRepository dr, DvdForm df) {
        this.repo = r;
        this.journalForm = f;
        this.eventBus = b;
        this.repoBook = rp;
        this.bookForm = bf;
        this.repoDvd = dr;
        this.dvdForm = df;
    }
    
    @Override
    protected void init(VaadinRequest request) {
        DisclosurePanel aboutBox = new DisclosurePanel("COSTA's Library", new RichText().withMarkDownResource("/welcome.md"));
        RichText booksText = new RichText().withMarkDownResource("/books.md");
        RichText dvdsText = new RichText().withMarkDownResource("/dvds.md");
        RichText journalsText = new RichText().withMarkDownResource("/journals.md");
        aboutBox.setOpen(true);

        admin.addSeparator();

        books.setCommand(new MenuBar.Command() {


            public void menuSelected(MenuBar.MenuItem selectedItem) {

                books.setEnabled(true);
                books.setChecked(true);
                setContent(

                        new MVerticalLayout(
                                aboutBox,
                                menuBar, booksText,
                                new MHorizontalLayout(filterByBookTitle, viewBook),
                                listBooks
                        ).expand(listBooks)
                );
            }
        });

        dvds.setCommand(new MenuBar.Command() {


            public void menuSelected(MenuBar.MenuItem selectedItem) {

                dvds.setEnabled(true);
                dvds.setChecked(true);
                setContent(

                        new MVerticalLayout(
                                aboutBox,
                                menuBar, dvdsText,
                                new MHorizontalLayout(filterByDvdTitle, viewDvd),
                                listDvds
                        ).expand(listDvds)
                );
            }
        });

        journal.setCommand(new MenuBar.Command() {


            public void menuSelected(MenuBar.MenuItem selectedItem) {

                journal.setEnabled(true);
                journal.setChecked(true);
                setContent(

                        new MVerticalLayout(
                                aboutBox,
                                menuBar, journalsText,
                                new MHorizontalLayout(filterByJournalTitle, viewJournal),
                                list
                        ).expand(list)
                );
            }
        });

        RichText TJournals = new RichText().setRichText("Journals");
        RichText TBooks = new RichText().setRichText("Books");
        RichText TJDvds = new RichText().setRichText("DVDs");
        admin.setCommand(new MenuBar.Command() {


            public void menuSelected(MenuBar.MenuItem selectedItem) {

                admin.setEnabled(true);
                admin.setChecked(true);
                setContent(

                        new MVerticalLayout(
                                aboutBox,
                                menuBar,
                                new MHorizontalLayout( TJournals, filterByJournalTitle, addNewJournal, editJournal, deleteJournal, viewJournal),
                                list,
                                new MHorizontalLayout(TBooks, filterByBookTitle,addNewBook, editBook, deleteBook, viewBook),
                                listBooks,
                                new MHorizontalLayout(TJDvds, filterByDvdTitle,addNewDvd, editDvd, deleteDvd, viewDvd),
                                listDvds
                        ).expand(list,listBooks,listDvds)
                );
            }
        });


        setContent(
                new MVerticalLayout(
                        aboutBox,
                        menuBar, journalsText,
                        new MHorizontalLayout(filterByJournalTitle, viewJournal),
                        list
                ).expand(list)

        );

        listJournals();
        listBooks();
        listDvds();
        list.addMValueChangeListener(e -> adjustActionButtonState());
        listBooks.addMValueChangeListener(e -> adjustBookActionButtonState());
        listDvds.addMValueChangeListener(e -> adjustDvdActionButtonState());
        filterByJournalTitle.addTextChangeListener(e -> {
            listJournals(e.getText());
        });
        filterByBookTitle.addTextChangeListener(e -> {
            listBooks(e.getText());
        });
        filterByDvdTitle.addTextChangeListener(e -> {
            listDvds(e.getText());
        });
        // Listen to change events emitted by PersonForm see onEvent method
        eventBus.subscribe(this);
    }
    

    
    static final int PAGESIZE = 45;
   
    
    //---------------------------------------------------------------------------------
    //----------------------JOURNAL-----------------------------------------------------
    //---------------------------------------------------------------------------------
    
    private void listJournals() {
        listJournals(filterByJournalTitle.getValue());
    }
    
    private void listJournals(String nameFilter) {

        // But we want to support filtering, first add the % marks for SQL name query
        String likeFilter = "%" + nameFilter + "%";
        list.setRows(repo.findByTitleLikeIgnoreCase(likeFilter));
        adjustActionButtonState();
        
    }

	protected void adjustActionButtonState() {
        boolean hasSelection = list.getValue() != null;
        editJournal.setEnabled(hasSelection);
        deleteJournal.setEnabled(hasSelection);
    }
    
    public void addJournal(ClickEvent clickEvent) {
        editJournal(new Journal());
    }
    
    public void editJournal(ClickEvent e) {
        editJournal(list.getValue());
    }
    
    public void removeJournal(ClickEvent e) {
        repo.delete(list.getValue());
        list.setValue(null);
        listJournals();
    }
    public void viewJournal(ClickEvent e) {
        view(list.getValue());
    }
    
    protected void editJournal(final Journal phoneBookEntry) {
        journalForm.setEntity(phoneBookEntry);
        journalForm.openInModalPopup();
    }
    protected void view(final Journal phoneBookEntry) {
        Window subWindow = new Window(phoneBookEntry.getTitle().toUpperCase());
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Authors:"));
        subContent.addComponent(new Label(phoneBookEntry.getAuthors()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Publisher:"));
        subContent.addComponent(new Label( phoneBookEntry.getPublisher()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Volume:"));
        subContent.addComponent(new Label( phoneBookEntry.getVolume()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Location:"));
        subContent.addComponent(new Label( "  " + phoneBookEntry.getLocation()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Date:"));
        subContent.addComponent(new Label( "  " + phoneBookEntry.getPublication_date().toString()));

        // Center it in the browser window
        subWindow.center();
        subWindow.setWidth(50,PERCENTAGE);
        // Open it in the UI
        addWindow(subWindow);
    }

    @EventBusListenerMethod(scope = EventScope.UI)
    public void onPersonModified(JournalModifiedEvent event) {
        listJournals();
        journalForm.closePopup();
    }
    

    //---------------------------------------------------------------------------------
    //----------------------BOOKS------------------------------------------------------
    //---------------------------------------------------------------------------------


    private void listBooks() {
    	listBooks(filterByBookTitle.getValue());

	}

	private void listBooks(String value) {
		String likeFilter = "%" + value + "%";
        listBooks.setRows(repoBook.findByTitleLikeIgnoreCase(likeFilter));
        adjustBookActionButtonState();

	}
    protected void adjustBookActionButtonState() {
        boolean hasSelection = listBooks.getValue() != null;
        editBook.setEnabled(hasSelection);
        deleteBook.setEnabled(hasSelection);
    }

	 public void addBook(ClickEvent clickEvent) {
	        editBook(new Book());
	    }

	    public void editBook(ClickEvent e) {
	        editBook(listBooks.getValue());
	    }

	    public void removeBook(ClickEvent e) {
	        repoBook.delete(listBooks.getValue());
	        listBooks.setValue(null);
	        listBooks();
	    }
	    public void viewBook(ClickEvent e) {
	        view(listBooks.getValue());
	    }

	    protected void editBook(final Book phoneBookEntry) {
	        bookForm.setEntity(phoneBookEntry);
	        bookForm.openInModalPopup();
	    }
	    protected void view(final Book phoneBookEntry) {


            Window subWindow = new Window(phoneBookEntry.getTitle().toUpperCase());
            VerticalLayout subContent = new VerticalLayout();
            subContent.setMargin(true);
            subWindow.setContent(subContent);

            subContent.addComponent(new Label("________________________________________________________________"));
            subContent.addComponent(new Label("Author:"));
            subContent.addComponent(new Label(phoneBookEntry.getAuthor()));
            subContent.addComponent(new Label("________________________________________________________________"));
            subContent.addComponent(new Label("Code:"));
            subContent.addComponent(new Label( phoneBookEntry.getCode()));
            subContent.addComponent(new Label("________________________________________________________________"));
            subContent.addComponent(new Label("Publisher:"));
            subContent.addComponent(new Label( phoneBookEntry.getPublisher()));
            subContent.addComponent(new Label("________________________________________________________________"));
            subContent.addComponent(new Label("Date:"));
            subContent.addComponent(new Label( "  " + phoneBookEntry.getDate().toString()));

            // Center it in the browser window
            subWindow.center();
            subWindow.setWidth(50,PERCENTAGE);
            // Open it in the UI
            addWindow(subWindow);
	    }

	    @EventBusListenerMethod(scope = EventScope.UI)
	    public void onBookModified(BookModifiedEvent event) {
	        listBooks();
	        bookForm.closePopup();
	    }


    //---------------------------------------------------------------------------------
    //----------------------DVDS------------------------------------------------------
    //---------------------------------------------------------------------------------


    private void listDvds() {
        listDvds(filterByDvdTitle.getValue());

    }

    private void listDvds(String value) {
        String likeFilter = "%" + value + "%";
        listDvds.setRows(repoDvd.findByTitleLikeIgnoreCase(likeFilter));
        adjustDvdActionButtonState();

    }
    protected void adjustDvdActionButtonState() {
        boolean hasSelection = listDvds.getValue() != null;
        editDvd.setEnabled(hasSelection);
        deleteDvd.setEnabled(hasSelection);
    }

    public void addDvd(ClickEvent clickEvent) {
        editDvd(new Dvd());
    }

    public void editDvd(ClickEvent e) {
        editDvd(listDvds.getValue());
    }

    public void removeDvd(ClickEvent e) {
        repoDvd.delete(listDvds.getValue());
        listDvds.setValue(null);
        listDvds();
    }
    public void viewDvd(ClickEvent e) {
        view(listDvds.getValue());
    }

    protected void editDvd(final Dvd phoneBookEntry) {
        dvdForm.setEntity(phoneBookEntry);
        dvdForm.openInModalPopup();
    }
    protected void view(final Dvd phoneBookEntry) {
        Window subWindow = new Window(phoneBookEntry.getTitle().toUpperCase());
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Actors:"));
        subContent.addComponent(new Label(phoneBookEntry.getActors()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Format:"));
        subContent.addComponent(new Label( phoneBookEntry.getFormat()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Run time:"));
        subContent.addComponent(new Label( phoneBookEntry.getRuntime()));
        subContent.addComponent(new Label("________________________________________________________________"));
        subContent.addComponent(new Label("Location:"));
        subContent.addComponent(new Label( phoneBookEntry.getLocation()));

        // Center it in the browser window
        subWindow.center();
        subWindow.setWidth(50,PERCENTAGE);
        // Open it in the UI
        addWindow(subWindow);
    }

    @EventBusListenerMethod(scope = EventScope.UI)
    public void onDvdModified(DvdModifiedEvent event) {
        listDvds();
        dvdForm.closePopup();
    }





}
