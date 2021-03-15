package fr.arpinum.voteer.modele.resolution;

import com.google.common.collect.Lists;

public class ResultatNonConcluant extends Resultat {

    public ResultatNonConcluant() {
        super(Lists.<String>newArrayList());
    }

    @Override
    public boolean nonConcluant() {
        return true;
    }

    @Override
    public String toString() {
        return "not conclusive";
    }
}
