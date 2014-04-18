package org.jgameengine.nineteenfourtysomething.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.IntroSplashScreen;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Powerup;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DefaultGameFlowLogic
        implements GameFlowLogic {

    protected static final Logger logger = Logger.getLogger(DefaultGameFlowLogic.class);

    private Engine engine;

    Player player;

    private WaveGenerator waveGenerator;

    private BackgroundGenerator backgroundGenerator;

    private boolean firstRun = true;

    private List<HighScore> highScores = new ArrayList<>(10);

    private boolean attemptWebstartHighScores = true;

    private boolean playerDead = false;

    private int enemiesKilled;

    public DefaultGameFlowLogic(Engine engine) {
        this.engine = engine;
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    class IntroScreen implements MouseListener {

        IntroSplashScreen splashRenderable;

        public IntroScreen(Engine engine) {
            engine.getInputManager().addMouseListener(this);
            splashRenderable = new IntroSplashScreen();
            engine.addGameObject(splashRenderable);
        }

        public void button0Up() {
            engine.getInputManager().removeMouseListener(this);
            engine.removeGameObject(splashRenderable);

            playerSpawns();
        }

        public void mouseEvent(int x, int y) {
        }

        public void button0Down() {
        }

        public void button1Down() {
        }

        public void button1Up() {
        }

        public void button2Down() {
        }

        public void button2Up() {
        }

    }

    public void gameLoads() {

        if (firstRun) {
            new IntroScreen(engine);
            engine.getAudioManager().loadOrGetSample(AssetConstants.snd_warAmbient).loopSample();
        } else {
            engine.getEventHandler().addEventIn(this, 3.0f, new Event() {
                public void trigger() {
                    new IntroScreen(engine);
                }
            });
        }

        if (backgroundGenerator == null) {
            backgroundGenerator = new BackgroundGenerator(engine);
        }

//        resetHighScores();

        loadHighScores();

        NineHudManager hudManager = (NineHudManager) engine.getRegisteredGameService("hudManager");
        hudManager.highScoreMode();
    }

    private static String contentsUrl = "http://www.owenbutler.org/194x/";

    private void resetHighScores() {

        try {
            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");

            try {
                service.delete(new URL(contentsUrl));
            } catch (IOException e) {

            }

            logger.debug("scores reset");

        } catch (Throwable thr) {
            logger.error("error getting persistence service", thr);
            return;
        }
    }

    private void loadHighScores() {

        if (!attemptWebstartHighScores) {
            return;
        }

        try {
            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");

            FileContents fileContents;
            try {
                fileContents = service.get(new URL(contentsUrl));
            } catch (IOException e) {
                // empty high score list.  Fine
                return;
            }

            ObjectInputStream inStream = new ObjectInputStream(fileContents.getInputStream());

            //noinspection unchecked
            highScores = (List<HighScore>) inStream.readObject();

            inStream.close();

        } catch (Throwable thr) {
            attemptWebstartHighScores = false;
            logger.debug("fatal error using webstart services to get highscores.  Probably not running in webstart, so disabling further tries");
        }
    }

    private void saveHighScores() {

        if (!attemptWebstartHighScores) {
            return;
        }

        try {
            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");

            FileContents fileContents;
            try {
                fileContents = service.get(new URL(contentsUrl));
            } catch (IOException e) {

                // empty high score list, create it
                long len = service.create(new URL(contentsUrl), 8192);
                if (len != 8192) {
                    logger.warn("only got " + len + " bytes for storage, requested 8192");
                }
                fileContents = service.get(new URL(contentsUrl));
            }

            ObjectOutputStream outStream = new ObjectOutputStream(fileContents.getOutputStream(true));

            outStream.writeObject(highScores);

            outStream.close();
        } catch (Throwable thr) {
            attemptWebstartHighScores = false;
            logger.debug("fatal error using webstart services to get highscores.  Probably not running in webstart, so disabling further tries");
            return;
        }
    }

    private void addHighScore(HighScore highScore) {

        highScores.add(highScore);
        Collections.sort(highScores);
        if (highScores.size() > 10) {
            highScores.remove(10);
        }
    }

    /**
     * Called when the player requests to start the game.
     */
    public void playerSpawns() {

        if (player == null) {
            player = new Player();
            engine.getInputManager().addMouseListener(player);
            engine.getInputManager().addMovementListener(player);
        }
        player.init();
        playerDead = false;
        engine.addGameObject(player);
        engine.getScoreManager().show();

        waveGenerator = new WaveGenerator(engine, this);
        engine.setEngineEventListener(waveGenerator);

        enemiesKilled = 0;

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerIdle).loopSample();

        engine.getInputManager().addPauseListener(player);

        engine.getScoreManager().resetScore();

        NineHudManager hudManager = (NineHudManager) engine.getRegisteredGameService("hudManager");
        hudManager.currentScoreMode();
    }

    public void playerDies() {
        if (playerDead) {
            return;
        }

        playerDead = true;

        waveGenerator.stop();

        engine.removeGameObject(player);

        addHighScore(new HighScore(System.getProperty("user.name"), engine.getScoreManager().getScore().getPoints()));

        saveHighScores();

        // find all enemies in the game and explode them!
        killAllEnemiesRemovePowerups();

        firstRun = false;

        gameLoads();

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerIdle).stopSample();
        engine.getInputManager().removePauseListener(player);
    }

    public void enemyDies(BaseEnemy enemy) {

        logger.trace("enemy died");
        enemiesKilled++;

        if (enemy.isGiveBonus()) {
            if (enemiesKilled % GameConstants.POWERUP_SPAWN_RATE == 0) {
                powerupSpawns(enemy.getX(), enemy.getY());
            }
        }
    }

    public void powerupSpawns(float x, float y) {

        int powerupType = RandomUtils.nextInt(3);

        // spawn a powerup at this position
        Powerup powerup = new Powerup(x, y, powerupType);

        engine.addGameObject(powerup);
    }

    private void killAllEnemiesRemovePowerups() {
        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {

            if (gameObject instanceof BaseEnemy) {
                BaseEnemy enemy = (BaseEnemy) gameObject;
                enemy.setGiveBonus(false);
                enemy.killed();
            }
            if (gameObject instanceof Powerup) {
                engine.removeGameObject(gameObject);
            }
        }
    }
}
