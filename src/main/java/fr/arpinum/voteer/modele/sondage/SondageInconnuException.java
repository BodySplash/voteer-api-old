package fr.arpinum.voteer.modele.sondage;

public class SondageInconnuException extends RuntimeException {

    public SondageInconnuException() {
        super("Unknown poll");
    }
}
