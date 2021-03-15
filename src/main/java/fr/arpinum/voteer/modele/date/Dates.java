package fr.arpinum.voteer.modele.date;

import java.util.Calendar;
import java.util.Date;

public final class Dates {

    private Dates() {
    }

    public static Date nouvelle(final int jour, final int mois, final int année) {
        return nouvelle(jour, mois, année, 0, 0);
    }

    public static Date nouvelle(final int jour, final int mois, final int année, final int heures, final int minutes) {
        return nouvelle(jour, mois, année, heures, minutes, 0, 0);
    }

    public static Date nouvelle(final int jour, final int mois, final int année, final int heures, final int minutes,
                                final int secondes, final int millisecondes) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(année, mois, jour, heures, minutes, secondes);
        calendar.set(Calendar.MILLISECOND, millisecondes);
        return calendar.getTime();
    }

    public static Date courante() {
        return serviceDate.dateCourante();
    }

    public static void setServiceDate(final ServiceDate serviceDate) {
        Dates.serviceDate = serviceDate;
    }

    private static ServiceDate serviceDate = new ServiceDate();
}
