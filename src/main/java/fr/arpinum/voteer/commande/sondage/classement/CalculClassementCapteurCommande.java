package fr.arpinum.voteer.commande.sondage.classement;


import com.google.common.collect.Lists;
import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.resolution.CalculateurResultat;
import fr.arpinum.voteer.modele.resolution.Classement;
import fr.arpinum.voteer.modele.resolution.Resultat;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.web.ressource.api.sondage.classement.VueResultat;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.VueProposition;

import java.util.List;
import java.util.stream.Collectors;

public class CalculClassementCapteurCommande implements CapteurCommande<CalculClassementCommande, List<VueResultat>> {

    @Override
    public List<VueResultat> execute(CalculClassementCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.idSondage);
        Classement classement = sondage.classement(CalculateurResultat.défaut());
        return converti(classement);
    }

    private List<VueResultat> converti(Classement classement) {
        List<VueResultat> résultat = Lists.newArrayList();
        for (Resultat resultat : classement.getResultats()) {
            VueResultat vue = new VueResultat(converti(resultat.gagnants()));
            résultat.add(vue);
        }
        return résultat;
    }

    private List<VueProposition> converti(List<String> gagnants) {
        List<VueProposition> résultat = Lists.newArrayList();
        résultat.addAll(gagnants.stream().map(VueProposition::new).collect(Collectors.toList()));
        return résultat;

    }

}
