package listener;

import java.util.EventObject;

/**
 * Created by reda on 29/12/2016.
 */
public class ModelChangedEvent extends EventObject {

    public ModelChangedEvent(Object source) {
        super(source);
    }
}
