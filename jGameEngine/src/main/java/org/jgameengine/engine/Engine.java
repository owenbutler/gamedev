package org.jgameengine.engine;

import org.apache.log4j.Logger;
import org.jgameengine.audio.AudioManager;
import org.jgameengine.audio.BlankAudioManager;
import org.jgameengine.collision.CollisionDetectionHandler;
import org.jgameengine.common.EngineConstants;
import org.jgameengine.common.InitialisationException;
import org.jgameengine.common.events.EventHandler;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.common.score.DefaultScoreManager;
import org.jgameengine.common.score.ScoreManager;
import org.jgameengine.engine.eventlistener.DefaultEngineEventListener;
import org.jgameengine.engine.eventlistener.EngineEventListener;
import org.jgameengine.engine.services.DefaultServiceManager;
import org.jgameengine.input.InputManager;
import org.jgameengine.network.ConnectionHandler;
import org.jgameengine.network.NetworkPacketHandler;
import org.jgameengine.renderer.HudCallback;
import org.jgameengine.renderer.HudManager;
import org.jgameengine.renderer.Renderable;
import org.jgameengine.renderer.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Engine {

    private static final Logger logger = Logger.getLogger(Engine.class.getName());

    private float currentTime = 0.0f;
    private long lastMillis = 0;

    private float timeDelta;

    private Set<GameObject> gameObjects = new HashSet<>();

    private Set<GameObject> pendingDeleteObjects = new HashSet<>();

    private List<GameObject> pendingAddObjects = new ArrayList<>(EngineConstants.INITIAL_ADD_GAME_OBJECTS_ARRAY_SIZE);

    private Renderer renderer;

    private InputManager inputManager;

    private ConnectionHandler connectionHandler;

    private NetworkPacketHandler packetHandler;

    private Object gameLogicHandler;

    private CollisionDetectionHandler collisionDetectionHandler;

    private EngineInitialiser engineInitialiser;

    private EventHandler eventHandler;

    private EngineEventListener engineEventListener = new DefaultEngineEventListener();

    private AudioManager audioManager = new BlankAudioManager();

    private Map<String, Object> gameRegisteredServices = new HashMap<>();

    private ScoreManager scoreManager = new DefaultScoreManager();

    private DefaultServiceManager serviceManager = new DefaultServiceManager();

    private HudManager hudManager = new HudManager() {
        public void init() {

        }

        public void renderHud() {

        }

        public void registerHudCallback(HudCallback hudCallback) {

        }

        public void deRegisterHudCallback(HudCallback hudCallback) {

        }
    };

    private boolean paused;

    public void runEngine() throws InitialisationException {
        initEngine();
    }

    public void processNextFrame() {

        engineEventListener.frameStart();
        audioManager.updateAudio();
        inputManager.processInput();

        if (!updateTime()) {
            return;
        }

        connectionHandler.processNetwork();
        eventHandler.processScheduledEvents();

        gameObjects.addAll(pendingAddObjects);
        pendingAddObjects.clear();

        for (GameObject gameObject : gameObjects) {
            gameObject.think();
        }

        doCollisionDetection();
        deletePendingObjects();
        renderer.renderFrame();
        hudManager.renderHud();
        renderer.finishFrame();
        engineEventListener.frameFinish();
    }

    private void deletePendingObjects() {
        gameObjects.removeAll(pendingDeleteObjects);

        for (GameObject deleted : pendingDeleteObjects) {
            engineEventListener.gameObjectRemoved(deleted);
        }

        renderer.removeRenderables(pendingDeleteObjects);
        pendingDeleteObjects.clear();
    }

    private void doCollisionDetection() {
        collisionDetectionHandler.doCollisionDetection(gameObjects);
    }

    private boolean updateTime() {
        long currentMillis = System.currentTimeMillis();

        long timeDiff = currentMillis - lastMillis;
        lastMillis = currentMillis;

        if (paused) {
            return false;
        }

        if (timeDiff == 0) {
            return false;
        }

        timeDelta = (float) timeDiff / 1000.0f;

        currentTime += timeDelta;

        return true;
    }

    public void shutdownEngine() {

        connectionHandler.shutdownNetwork();

        renderer.shutdown();
        audioManager.shutdownAudio();
    }

    public void initEngine() throws InitialisationException {

        EngineLocator.setEngine(this);

        renderer.init();
        hudManager.init();
        lastMillis = System.currentTimeMillis();
        inputManager.initInput();
        packetHandler.initPacketHandler(this);
        connectionHandler.initNetwork(this);
        audioManager.initAudio();
        scoreManager.initScore();
        engineInitialiser.soundPreLoad();
        engineInitialiser.graphicsPreLoad();
        engineInitialiser.initialiseGame();
    }

    public void addGameObject(GameObject objectToAdd) {
        engineEventListener.gameObjectAdded(objectToAdd);

        if (logger.isTraceEnabled()) {
            logger.trace("objectToAdd = " + objectToAdd);
        }

        pendingAddObjects.add(objectToAdd);

        if (objectToAdd instanceof Renderable) {
            renderer.addRenderable((Renderable) objectToAdd);
        }

        if (objectToAdd instanceof Collidable) {
            collisionDetectionHandler.collideableAdded((Collidable) objectToAdd);
        }
    }

    public void removeGameObject(GameObject objectToDelete) {
        if (logger.isTraceEnabled()) {
            logger.trace("objectToDelete = " + objectToDelete);
        }

        getEventHandler().removeEventsForOwner(objectToDelete);

        if (objectToDelete instanceof Collidable) {
            collisionDetectionHandler.collideableRemoved((Collidable) objectToDelete);
        }

        pendingDeleteObjects.add(objectToDelete);
    }

    public final float getTimeDelta() {
        return timeDelta;
    }

    public void registerGameService(String name, Object service) {
        gameRegisteredServices.put(name, service);
    }

    public Object getRegisteredGameService(String name) {
        Object service = gameRegisteredServices.get(name);

        if (service == null) {
            throw new IllegalArgumentException("service (" + name + ") not found");
        }

        return service;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void pause() {
        paused = true;
    }

    public void unPause() {
        paused = false;
    }

    public ConnectionHandler getNetworkDriver() {
        return connectionHandler;
    }

    public void setNetworkDriver(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public NetworkPacketHandler getPacketHandler() {
        return packetHandler;
    }

    public void setPacketHandler(NetworkPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public Object getGameLogicHandler() {
        return gameLogicHandler;
    }

    public void setGameLogicHandler(Object gameLogicHandler) {
        this.gameLogicHandler = gameLogicHandler;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public EngineInitialiser getEngineInitialiser() {
        return engineInitialiser;
    }

    public void setEngineInitialiser(EngineInitialiser engineInitialiser) {
        this.engineInitialiser = engineInitialiser;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public CollisionDetectionHandler getCollisionDetectionHandler() {
        return collisionDetectionHandler;
    }

    public void setCollisionDetectionHandler(CollisionDetectionHandler collisionDetectionHandler) {
        this.collisionDetectionHandler = collisionDetectionHandler;
    }

    public EngineEventListener getEngineEventListener() {
        return engineEventListener;
    }

    public void setEngineEventListener(EngineEventListener engineEventListener) {
        this.engineEventListener = engineEventListener;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public void setScoreManager(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public Set<GameObject> getGameObjects() {
        return gameObjects;
    }

    public HudManager getHudManager() {
        return hudManager;
    }

    public void setHudManager(HudManager hudManager) {
        this.hudManager = hudManager;
    }
}