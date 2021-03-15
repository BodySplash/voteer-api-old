package fr.arpinum.voteer.modele.resolution;

import fr.arpinum.voteer.modele.resolution.condorcet.CalculateurResultatCondorcet;
import fr.arpinum.voteer.modele.resolution.tideman.CalculateurResultatTideman;
import fr.arpinum.voteer.modele.sondage.Duels;

import java.util.List;

public abstract class CalculateurResultat {

    public static CalculateurResultat condorcet() {
        return new CalculateurResultatCondorcet();
    }

    public static CalculateurResultat tideman() {
        return new CalculateurResultatTideman();
    }

    public static CalculateurResultat défaut() {
        return tideman();
    }

    public abstract Classement classement(List<String> propositions, Duels duels);

}
