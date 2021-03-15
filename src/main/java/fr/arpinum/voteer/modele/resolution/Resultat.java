package fr.arpinum.voteer.modele.resolution;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Resultat {

    public Resultat(final List<String> gagnants) {
        this.gagnants.addAll(gagnants);
    }

    public Resultat(final String valeur) {
        gagnants.add(valeur);
    }

    public boolean égalité() {
        return gagnants.size() > 1;
    }

    @Override
    public String toString() {
        if (gagnants.size() == 1) {
            return gagnants.get(0);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Tie ");
        for (String gagnant : gagnants) {
            builder.append(gagnant);
            builder.append(" / ");
        }
        return builder.substring(0, builder.length() - 3);
    }

    public boolean nonConcluant() {
        return false;
    }

    public List<String> gagnants() {
        return Collections.unmodifiableList(gagnants);
    }

    private List<String> gagnants = Lists.newArrayList();
    public static final Resultat NON_CONCLUANT = new ResultatNonConcluant();
}
