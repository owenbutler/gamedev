package org.jgameengine.engine;

public class EngineLocator {

    private static Engine engine = null;

    public static Engine getEngine() {
        if (engine == null) {
            throw new RuntimeException("engine locator not initliased");
        }

        return engine;
    }

    public static void setEngine(Engine engine) {
        EngineLocator.engine = engine;
    }
}
