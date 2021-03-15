package fr.arpinum.voteer.modele.resolution;

import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.resolution.tideman.Graphe;

import java.util.Collections;
import java.util.List;

public class Classement {

    public int taille() {
        return résultats.size();
    }

    public List<Resultat> getResultats() {
        return Collections.unmodifiableList(résultats);
    }

    public void ajoute(Resultat résultat) {
        résultats.add(résultat);
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public void setGraphe(Graphe graphe) {
        this.graphe = graphe;
    }

    public Resultat gagnant() {
        return résultats.stream().findFirst().orElse(Resultat.NON_CONCLUANT);
    }

    private Graphe graphe;

    private List<Resultat> résultats = Lists.newArrayList();
}
