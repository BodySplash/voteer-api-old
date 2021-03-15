package fr.arpinum.voteer.modele.resolution.tideman;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Graphe {

    public Graphe(final List<String> sommets) {
        for (String sommet : sommets) {
            arcs.put(sommet, Lists.<String>newArrayList());
        }
    }

    public void connecte(final String sommet1, final String sommet2) {
        arcs.get(sommet1).add(sommet2);
    }

    public boolean existeChemin(final String sommet1, final String sommet2) {
        final List<String> voisins = arcs.get(sommet1);
        if (voisins.contains(sommet2)) {
            return true;
        }
        if (voisins.isEmpty()) {
            return false;
        }
        for (String voisin : voisins) {
            if (existeChemin(voisin, sommet2)) {
                return true;
            }
        }
        return false;
    }

    public List<String> sourcesAvecLePlusDArcs() {
        final List<String> sources = sources();
        final int maximumDArcs = maximumDArcs(sources);
        return Lists.newArrayList(Iterables.filter(sources, prédicatDeNombreDArcs(maximumDArcs)));
    }

    private List<String> sources() {
        final List<String> sommets = Lists.newArrayList(arcs.keySet());
        for (Map.Entry<String, List<String>> entrée : arcs.entrySet()) {
            sommets.removeAll(entrée.getValue());
        }
        return sommets;
    }

    private int maximumDArcs(List<String> sources) {
        int maximumDArcs = 0;
        for (String source : sources) {
            maximumDArcs = Math.max(maximumDArcs, arcs.get(source).size());
        }
        return maximumDArcs;
    }

    private Predicate<String> prédicatDeNombreDArcs(final int maximumDArcs) {
        return sommet -> arcs.get(sommet).size() == maximumDArcs;
    }

    private Map<String, List<String>> arcs = Maps.newConcurrentMap();
}
