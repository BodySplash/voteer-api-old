package fr.arpinum.voteer.web.ressource.api.sondage.proposition;

@SuppressWarnings("UnusedDeclaration")
public class VueProposition {

    protected VueProposition() {
    }

    public VueProposition(String proposition) {
        this.label = proposition;
    }

    public String label;
}
