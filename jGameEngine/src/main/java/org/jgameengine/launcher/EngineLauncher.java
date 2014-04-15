package org.jgameengine.launcher;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jgameengine.engine.Engine;
import org.lwjgl.opengl.Display;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EngineLauncher {

    private static final Logger logger = Logger.getLogger(FirstLauncher.class.getName());

    public static void main(String[] args) {

        BasicConfigurator.configure();

        // Init spring with the appropriate resource file
        Resource resource;
        BeanFactory factory;

        if (args.length != 1) {
            throw new IllegalArgumentException("must pass in a context file");
        }

        resource = new ClassPathResource(args[0]);

        factory = new XmlBeanFactory(resource);

        Engine gameEngine = (Engine) factory.getBean("engine");

        boolean finished = false;

        try {
            gameEngine.initEngine();

            while (!finished) {
                if (!Display.isVisible()) {
                    Thread.sleep(200);
                } else if (Display.isCloseRequested()) {
                    logger.info("close requested, shutdown");
                    finished = true;
                } else {
                    gameEngine.processNextFrame();
                }
            }

            logger.debug("calling shutdown");
            gameEngine.shutdownEngine();
            logger.debug("shutdown complete");
        } catch (Throwable t) {
            logger.error(t);
            t.printStackTrace();
        } finally {
            gameEngine.shutdownEngine();
        }

        logger.debug("exiting main");
    }
}
