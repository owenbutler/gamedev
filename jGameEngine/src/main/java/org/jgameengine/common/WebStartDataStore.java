package org.jgameengine.common;

import org.apache.log4j.Logger;

import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

public class WebStartDataStore {

    private static final Logger logger = Logger.getLogger(WebStartDataStore.class);

    protected String storeUrl;

    protected boolean continueToAttemptWebStart = true;

    public WebStartDataStore(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Object loadObject(boolean fail) {

        if (!continueToAttemptWebStart) {
            return null;
        }

        Object retval;

        try {
            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");

            FileContents fileContents;
            try {
                fileContents = service.get(new URL(storeUrl));
            } catch (IOException e) {
                // empty high score list.  Fine
                return null;
            }

            ObjectInputStream inStream = new ObjectInputStream(fileContents.getInputStream());

            retval = inStream.readObject();

            inStream.close();

        } catch (Throwable thr) {
            if (fail) {
                continueToAttemptWebStart = false;
            }
            logger.debug("fatal error using webstart services to save object.");
            return null;
        }

        return retval;
    }

    public void saveObject(Object objectToSave) {

        if (!continueToAttemptWebStart) {
            return;
        }

        try {
            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");

            FileContents fileContents;
            try {
                fileContents = service.get(new URL(storeUrl));
            } catch (IOException e) {

                // empty high score list, create it
                long len = service.create(new URL(storeUrl), 8192);
                if (len != 8192) {
                    logger.warn("only got " + len + " bytes for storage, requested 8192");
                }
                fileContents = service.get(new URL(storeUrl));
            }

            ObjectOutputStream outStream = new ObjectOutputStream(fileContents.getOutputStream(true));

            outStream.writeObject(objectToSave);

            outStream.close();
        } catch (Throwable thr) {
            continueToAttemptWebStart = false;
            logger.debug("fatal error using webstart services to get object.");
        }
    }

}
