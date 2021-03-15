package fr.arpinum.voteer.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflexionFacile {
    private ReflexionFacile() {
    }

    public static void changerChampPrivé(final Object o, final String nomDuChamp, final Object nouvelleValeur) {
        try {
            final Field champPrivé = trouverChampsPrivé(o.getClass(), nomDuChamp);
            champPrivé.set(o, nouvelleValeur);
        } catch (final Exception e) {
            throw new RéflexionFacileException(e);
        }
    }

    private static Field trouverChampsPrivé(final Class<?> clazz, final String nomDuChamp)
            throws NoSuchFieldException {
        Field champPrivé;
        try {
            champPrivé = clazz.getDeclaredField(nomDuChamp);
            champPrivé.setAccessible(true);
        } catch (final NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return trouverChampsPrivé(clazz.getSuperclass(), nomDuChamp);
            }
            throw e;
        }
        return champPrivé;
    }

    public static Object obtenirChampPrivé(final Object o, final String nomDuChamp) {
        try {
            final Field champPrivé = trouverChampsPrivé(o.getClass(), nomDuChamp);
            return champPrivé.get(o);
        } catch (final Exception e) {
            throw new RéflexionFacileException(e);
        }
    }

    public static <T> T instancier(final Class<T> clazz) {
        try {
            final Constructor<T> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (final Exception e) {
            throw new RéflexionFacileException(e);
        }
    }

    public static class RéflexionFacileException extends RuntimeException {
        public RéflexionFacileException(final Throwable t) {
            super(t);
        }

        private static final long serialVersionUID = -4030310387794694279L;
    }

    public static Object appeler(final Object o, final String nomMéthode) {
        try {
            final Method m = trouverMéthode(o.getClass(), nomMéthode);
            return m.invoke(o);
        } catch (final Exception e) {
            throw new RéflexionFacileException(e);
        }
    }

    private static Method trouverMéthode(final Class<?> clazz, final String nomMéthode)
            throws NoSuchMethodException {
        Method method;
        try {
            method = clazz.getDeclaredMethod(nomMéthode);
            method.setAccessible(true);
        } catch (final NoSuchMethodException e) {
            if (clazz.getSuperclass() != null) {
                return trouverMéthode(clazz.getSuperclass(), nomMéthode);
            }
            throw e;
        }
        return method;
    }
}
