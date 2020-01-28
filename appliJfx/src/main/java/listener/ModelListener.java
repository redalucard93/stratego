package listener;

import java.rmi.RemoteException;
import java.util.EventListener;

/**
 * Created by reda on 29/12/2016.
 */
public interface ModelListener extends EventListener{
    public void modelChanged(ModelChangedEvent event) throws RemoteException;
}
