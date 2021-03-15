package fr.arpinum.voteer.web.ressource.api.sondage.classement;

import com.google.common.collect.Lists;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.VueProposition;

import java.util.List;

public class VueResultat {

    public VueResultat(List<VueProposition> propositions) {
        this.propositions.addAll(propositions);
    }

    public final List<VueProposition> propositions = Lists.newArrayList();
}
