package fr.arpinum.voteer.modele.resolution.tideman;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import fr.arpinum.voteer.modele.resolution.*;
import fr.arpinum.voteer.modele.sondage.Duel;
import fr.arpinum.voteer.modele.sondage.Duels;

import java.util.List;

/**
 * Explication du système sur Wikipedia : http://en.wikipedia.org/wiki/Ranked_pairs
 * C'est un bon compromis entre facilité d'implémentation et cohérence des résultats
 * La méthode garantit un vainqueur, et les résultats sont ordonnés
 */
public class CalculateurResultatTideman extends CalculateurResultat {

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

    public Resultat résultat(List<String> propositions, Duels duels) {
        return doCalcule(propositions, duels);
    }

    private Resultat doCalcule(List<String> propositions, Duels duels) {
        return new Resultat(classementEnGraphe(propositions, duels).sourcesAvecLePlusDArcs());
    }

    private Duels duelsSansLesGagnants(Duels duelsCourants, final List<String> propositionsASupprimer) {
        return duelsCourants.sans(input -> propositionsASupprimer.contains(input.getProposition()) || propositionsASupprimer.contains(input.getConcurrent()));
    }

    private Graphe classementEnGraphe(List<String> propositions, Duels duels) {
        final Duels gagnants = duels.sans(PERDANTS);
        gagnants.trie(TIDEMAN);
        final Graphe graphe = new Graphe(propositions);
        verrouille(gagnants, graphe);
        return graphe;
    }

    private void verrouille(final Duels gagnants, final Graphe graphe) {
        for (Duel gagnant : gagnants) {
            if (!graphe.existeChemin(gagnant.getConcurrent(), gagnant.getProposition())) {
                graphe.connecte(gagnant.getProposition(), gagnant.getConcurrent());
            }
        }
    }

    public static final Predicate<Duel> PERDANTS = input -> input.isPerdant();
    public static final Ordering<Duel> TIDEMAN = new Ordering<Duel>() {
        @Override
        public int compare(final Duel duel, final Duel duel1) {
            if (duel.getMagnitudeVictoire() != duel1.getMagnitudeVictoire()) {
                return Ints.compare(duel1.getMagnitudeVictoire(), duel.getMagnitudeVictoire());
            }
            return Ints.compare(duel.getMagnitudeDéfaite(), duel1.getMagnitudeDéfaite());
        }
    };
}
