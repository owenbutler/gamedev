package org.owenbutler.theta.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.PauseListener;
import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.constants.GameConstants;
import org.owenbutler.theta.logic.levels.LevelEight;
import org.owenbutler.theta.logic.levels.LevelFive;
import org.owenbutler.theta.logic.levels.LevelFour;
import org.owenbutler.theta.logic.levels.LevelNine;
import org.owenbutler.theta.logic.levels.LevelOne;
import org.owenbutler.theta.logic.levels.LevelSeven;
import org.owenbutler.theta.logic.levels.LevelSix;
import org.owenbutler.theta.logic.levels.LevelTen;
import org.owenbutler.theta.logic.levels.LevelThree;
import org.owenbutler.theta.logic.levels.LevelTwo;
import org.owenbutler.theta.renderables.AbstractEnemyBase;
import org.owenbutler.theta.renderables.BackgroundCloud;
import org.owenbutler.theta.renderables.BossAura;
import org.owenbutler.theta.renderables.EnemyBullet;
import org.owenbutler.theta.renderables.Player;
import org.owenbutler.theta.renderables.PlayerBullet;

import java.util.Set;

public class GameLogic implements PauseListener, LevelEndListener {

    protected Engine engine;

    protected HudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;

    protected float enemySpawnTime;

    protected LevelChooser levelChooser;

    protected Level[] levels = {
            new LevelOne(),
            new LevelTwo(),
            new LevelThree(),
            new LevelFour(),
            new LevelFive(),
            new LevelSix(),
            new LevelSeven(),
            new LevelEight(),
            new LevelNine(),
            new LevelTen()
    };

    protected int currentLevel;

    public GameLogic(Engine engine) {
        this.engine = engine;
        engine.registerGameService("game", this);

        initLevels();

        levelChooser = new LevelChooser(engine);
    }

    private void initLevels() {
        for (Level level : levels) {
            level.setEngine(engine);
            level.setLevelEndListener(this);
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void gameLoads() {

        showIntroScreen();

        // listen for mouse button to start the game
        listeningForPlayerSpawn = true;

        engine.getEventHandler().addEventInLoop(this, GameConstants.CLOUD_SPAWN_INTERVAL, GameConstants.CLOUD_SPAWN_INTERVAL, new Event() {
            public void trigger() {
                spawnCloud();
            }
        });

        spawnInitialClounds();

        engine.getInputManager().addPauseListener(this);

        engine.getAudioManager().loadMusicFromURI("sfx/bgm.ogg").playStream();

    }

    private void spawnInitialClounds() {

        final int numInitialClouds = 3;
        for (int numClounds = 0; numClounds < numInitialClouds; ++numClounds) {
            spawnCloud(RandomUtils.nextInt(100));
        }
    }

    private void spawnCloud() {
        spawnCloud(-150);
    }

    private void spawnCloud(int yPos) {

        for (int i = 0; i < 3; ++i) {

            final int cloundLevel = RandomUtils.nextInt(50) - 25;

            BackgroundCloud cloud = new BackgroundCloud(cloundLevel);
            cloud.setXY(RandomUtils.nextInt(600) + 20, yPos);

            engine.addGameObject(cloud);
        }
    }

    private void showIntroScreen() {

        if (hudManager == null) {
            hudManager = (HudManager) engine.getHudManager();
        }

        hudManager.showIntroScreen();

        levelChooser.initShowLevelChoices();
    }

    private void playerSpawns(int atLevel) {

        hideIntroScreen();
        gameStarted = true;

        hudManager.setShowLife(true);

        if (player == null) {
            player = new Player(GameConstants.PLAYER_SPAWN_X, GameConstants.PLAYER_SPAWN_Y);
            engine.getInputManager().addMovementListener(player);
            engine.getInputManager().addMouseListener(player);
            engine.addGameObject(player);
            engine.registerGameService("player", player);
        }

        player.setBlRender(true);
        player.setXY(GameConstants.PLAYER_SPAWN_X, GameConstants.PLAYER_SPAWN_Y);
        player.lookat(GameConstants.PLAYER_SPAWN_X, GameConstants.PLAYER_SPAWN_Y - 100);
        player.setDead(false);

        player.init();

        currentLevel = atLevel - 1;
        levels[currentLevel].start();

    }

    private void hideIntroScreen() {

        hudManager.hideIntroScreen();

        levelChooser.hideLevelChoices();
    }

    public void levelAboutToEnd() {

    }

    public void levelEnded() {

        currentLevel++;

        if (currentLevel == levels.length) {
            gameEnds();
        } else {
            levels[currentLevel].start();
        }
    }

    private void gameEnds() {

        System.out.println("the game has ended");

    }

    public void playerDies() {

        player.setBlRender(false);
        player.setDead(true);
        gameStarted = false;
        hudManager.setShowGameOver(true);
        hudManager.setShowLife(false);
        hudManager.setShowLevelIntro(true);

        engine.getEventHandler().addEventIn(this, 4.0f, new Event() {
            public void trigger() {
                hudManager.showIntroScreen();
                hudManager.setShowGameOver(false);
                hudManager.setShowLevelIntro(false);

                listeningForPlayerSpawn = true;

            }
        });

        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof AbstractEnemyBase) {
                engine.getEventHandler().removeEventsForOwner(gameObject);
                ((BaseDrawableGameObject) gameObject).setFadeAndRemove(0.5f);
            } else if (gameObject instanceof EnemyBullet || gameObject instanceof PlayerBullet || gameObject instanceof BossAura) {
                ((BaseDrawableGameObject) gameObject).setFadeAndRemove(0.5f);
            }
        }

//        hudManager.setShowLevelIntro(false);
        hudManager.setShowLevelText(false);
        levelChooser.initShowLevelChoices();

        levels[currentLevel].killLevel();
        currentLevel = 0;

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_gameOver).playSample();

    }

    public void levelStartRequested(int selectedLevel) {
        if (listeningForPlayerSpawn) {
            playerSpawns(selectedLevel);
            listeningForPlayerSpawn = false;
        }
    }

    public void pause() {
        engine.pause();
    }

    public void unPause() {
        engine.unPause();
    }

}
