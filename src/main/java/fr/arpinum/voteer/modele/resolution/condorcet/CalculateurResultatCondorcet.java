package fr.arpinum.voteer.modele.resolution.condorcet;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.resolution.*;
import fr.arpinum.voteer.modele.sondage.Duel;
import fr.arpinum.voteer.modele.sondage.Duels;

import java.util.List;

public class CalculateurResultatCondorcet extends CalculateurResultat {

    @Override
    public Classement classement(List<String> propositions, Duels duels) {
        Classement classement = new Classement();
        List<String> propositionsCourantes = Lists.newArrayList(propositions);
        Duels duelsCourants = duels;
        while (propositionsCourantes.size() > 0) {
            final Resultat résultatCourant = résultat(propositionsCourantes, duelsCourants);
            classement.ajoute(résultatCourant);
            propositionsCourantes.removeAll(résultatCourant.gagnants());
            duelsCourants = duelsSansLesGagnants(duelsCourants, résultatCourant.gagnants());
        }
        return classement;
    }

    private Resultat résultat(List<String> propositions, Duels duels) {
        return doCalcule(propositions, duels);
    }

    private Resultat doCalcule(List<String> propositions, Duels duels) {
        if (propositions.size() == 1) {
            return new Resultat(propositions.get(0));
        }
        if (propositions.isEmpty()) {
            return Resultat.NON_CONCLUANT;
        }
        return organiseDuels(propositions, duels);
    }

    private Duels duelsSansLesGagnants(Duels duelsCourants, final List<String> propositionsASupprimer) {
        return duelsCourants.sans(input -> propositionsASupprimer.contains(input.getProposition()) || propositionsASupprimer.contains(input.getConcurrent()));
    }

    private Resultat organiseDuels(List<String> propositions, Duels duels) {
        Resultat résultat = trouveGagnant(propositions, duels);
        while ((résultat.nonConcluant() || résultat.égalité()) && duels.existeDéfaites()) {
            duels = sansDéfaitesFaibles(duels);
            résultat = trouveGagnant(propositions, duels);
        }
        return résultat;
    }

    private Resultat trouveGagnant(List<String> propositions, final Duels duels) {
        List<String> gagnants = Lists.newArrayList();
        for (String proposition : propositions) {
            if (tousGagnants(duels.de(proposition))) {
                gagnants.add(proposition);
            }
        }
        if (gagnants.size() > 0) {
            return new Resultat(gagnants);
        }
        return Resultat.NON_CONCLUANT;
    }

    private Duels sansDéfaitesFaibles(final Duels duels) {
        int min = Integer.MAX_VALUE;
        for (Duel duel : duels.getDuels()) {
            if (duel.isPerdant()) {
                min = Math.min(duel.getMagnitudeDéfaite(), min);
            }
        }
        return duels.sans(défaitesAvecMagnitude(min));
    }

    private Predicate<Duel> défaitesAvecMagnitude(final int min) {
        return duel -> duel.isPerdant() && duel.getMagnitudeDéfaite() == min;
    }

    private boolean tousGagnants(final List<Duel> duels) {
        boolean resultat = true;
        for (Duel duel : duels) {
            resultat &= duel.isGagnant();
        }
        return resultat;
    }
}
