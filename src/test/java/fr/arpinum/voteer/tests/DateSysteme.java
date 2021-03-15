package fr.arpinum.voteer.tests;

import fr.arpinum.voteer.modele.date.Dates;
import fr.arpinum.voteer.modele.date.ServiceDate;
import org.junit.rules.ExternalResource;

import java.util.Date;

public class DateSysteme extends ExternalResource {

    public static DateSysteme fixe(final Date date) {
        final DateSysteme résultat = new DateSysteme();
        résultat.changeCourante(date);
        return résultat;
    }

    public static DateSysteme fixe(final int jour, final int mois, final int année) {
        return fixe(Dates.nouvelle(jour, mois, année));
    }

    public static DateSysteme fixe(final int jour, final int mois, final int année, final int heure,
                                   final int minute) {
        return fixe(Dates.nouvelle(jour, mois, année, heure, minute));
    }

    @Override
    protected void after() {
        Dates.setServiceDate(new ServiceDate());
    }

    public void changeCourante(final Date dateDébut) {
        Dates.setServiceDate(new MockServiceDate(dateDébut));
    }

    public void changeCourante(final int jour, final int mois, final int année) {
        Dates.setServiceDate(new MockServiceDate(jour, mois, année));
    }

    public void normale() {
        Dates.setServiceDate(new ServiceDate());
    }

    public Date fixe() {
        final Date date = new Date();
        changeCourante(date);
        return date;
    }

}
