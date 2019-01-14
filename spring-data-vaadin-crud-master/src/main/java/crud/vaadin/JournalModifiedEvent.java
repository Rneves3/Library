package crud.vaadin;

import java.io.Serializable;

import crud.backend.Journal;

public class JournalModifiedEvent implements Serializable {

    private final Journal journal;

    public JournalModifiedEvent(Journal p) {
        this.journal = p;
    }

    public Journal getJournal() {
        return journal;
    }
    
}
