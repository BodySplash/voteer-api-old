package fr.arpinum.voteer.modele.sondage;

public class SondageNonModifiableException extends RuntimeException {

    public SondageNonModifiableException() {
        super("Votes already exist on this poll");
    }

    private static final long serialVersionUID = 1L;

}
