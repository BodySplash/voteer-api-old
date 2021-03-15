package fr.arpinum.voteer.tests;


import fr.arpinum.voteer.modele.date.Dates;
import fr.arpinum.voteer.modele.date.ServiceDate;

import java.util.Date;

public class MockServiceDate extends ServiceDate {

    public MockServiceDate(final Date dateCourante) {
        this.dateCourante = dateCourante;
    }

    public MockServiceDate(final int jour, final int mois, final int année) {
        this(Dates.nouvelle(jour, mois, année));
    }

    public static void mockDateSystème(final Date dateCourante) {
        Dates.setServiceDate(new MockServiceDate(dateCourante));
    }

    @Override
    public Date dateCourante() {
        return (Date) dateCourante.clone();
    }

    private final Date dateCourante;
}
