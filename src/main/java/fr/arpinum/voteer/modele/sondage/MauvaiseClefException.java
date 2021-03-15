package fr.arpinum.voteer.modele.sondage;

public class MauvaiseClefException extends RuntimeException {

    public MauvaiseClefException() {
        super("Wrong admin key");
    }
}
