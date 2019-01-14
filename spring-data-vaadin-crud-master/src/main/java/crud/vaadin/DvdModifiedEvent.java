package crud.vaadin;

import java.io.Serializable;

import crud.backend.Dvd;

public class DvdModifiedEvent implements Serializable {

    private final Dvd dvd;

    public DvdModifiedEvent(Dvd d) {
        this.dvd = d;
    }

    public Dvd getDvd() {
        return dvd;
    }

}
