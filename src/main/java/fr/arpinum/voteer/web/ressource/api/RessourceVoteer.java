package fr.arpinum.voteer.web.ressource.api;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

public abstract class RessourceVoteer extends ServerResource {

    protected  <T> T check(ListenableFuture<ResultatExecution<T>> reponse, Status successStatus) {
        ResultatExecution<T> résultat = Futures.getUnchecked(reponse);
        if(résultat.isErreur()) {
            Throwables.propagate(résultat.erreur());
        }
        setStatus(successStatus);
        return résultat.donnees();
    }
}
