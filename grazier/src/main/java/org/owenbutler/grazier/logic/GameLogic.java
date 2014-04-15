package org.owenbutler.grazier.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.input.PauseListener;
import org.owenbutler.grazier.constants.GameConstants;
import org.owenbutler.grazier.renderables.Background;
import org.owenbutler.grazier.renderables.Enemy1;
import org.owenbutler.grazier.renderables.EnemyBullet;
import org.owenbutler.grazier.renderables.EnemyBulletGrazed;
import org.owenbutler.grazier.renderables.Flower;
import org.owenbutler.grazier.renderables.Grazer;
import org.owenbutler.grazier.renderables.Player;

import java.util.Set;

public class GameLogic implements MouseListener, PauseListener {

    protected Engine engine;

    protected HudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;
    protected Grazer grazer;

    protected float enemySpawnTime;

    protected Level level;

    protected Background background;

    public GameLogic(Engine engine) {
        this.engine = engine;

        engine.getInputManager().addMouseListener(this);

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

        // set our enemy spawning event
        level = new Level(engine);

        engine.getInputManager().addPauseListener(this);

//        background = new Background(0, 0);
//        engine.addGameObject(background);

        playerSpawns();
//        engine.getEventHandler().addEventIn(this, 120.0f, new Event() {
//            public void trigger() {
//                engine.shutdownEngine();
//            }
//        });
    }

    private void showIntroScreen() {

        if (hudManager == null) {
            hudManager = (HudManager) engine.getHudManager();
        }

        hudManager.showIntroScreen();
    }

    private void playerSpawns() {

        gameStarted = true;

        hudManager.hideIntroScreen();

        if (player == null) {
            player = new Player(400, 580);
            engine.getInputManager().addMovementListener(player);
            engine.getInputManager().addMouseListener(player);
            engine.registerGameService("player", player);
            engine.addGameObject(player);

            grazer = new Grazer(400, 580);
            engine.addGameObject(grazer);
        }

        player.setBlRender(true);
        player.setXY(400, 580);
        player.setDead(false);

        grazer.enable();

//        for (int i = 0; i < 10; ++i) {
//
//            Enemy1 enemy = new Enemy1();
//            engine.addGameObject(enemy);
//        }

        level.startLevel();
    }

    public void playerDies() {

        player.setBlRender(false);
        grazer.disable();

        gameStarted = false;
        hudManager.setShowGameOver(true);

        engine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                hudManager.showIntroScreen();
                hudManager.setShowGameOver(false);

                listeningForPlayerSpawn = true;

            }
        });

        level.endLevel();

        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            boolean spawnFlower = false;
            float fX = 0, fY = 0;

            if (gameObject instanceof EnemyBullet) {

                EnemyBullet bullet = (EnemyBullet) gameObject;

                spawnFlower = true;
                fX = bullet.getX();
                fY = bullet.getY();

            } else if (gameObject instanceof EnemyBulletGrazed) {

                EnemyBulletGrazed bullet = (EnemyBulletGrazed) gameObject;

                spawnFlower = true;
                fX = bullet.getX();
                fY = bullet.getY();
            }

            if (spawnFlower) {
                Flower flower = new Flower(fX, fY);
                flower.setShrinkAndRemove(20);
                flower.setWidth(32);
                flower.setHeight(32);

                flower.setVelY(GameConstants.ENEMY_VEL);

                engine.addGameObject(flower);
                engine.removeGameObject(gameObject);
                continue;
            }

            if (gameObject instanceof Enemy1) {
                Enemy1 enemy = (Enemy1) gameObject;
                enemy.enemyDie();
            }
        }

    }

    public void mouseEvent(int x, int y) {

    }

    public void button0Down() {

    }

    public void button0Up() {
        if (listeningForPlayerSpawn) {
            playerSpawns();
            listeningForPlayerSpawn = false;
        }
    }

    public void button1Down() {

    }

    public void button1Up() {

    }

    public void button2Down() {

    }

    public void button2Up() {

    }

    /**
     * Pause requested.
     */
    public void pause() {

        engine.pause();
    }

    /**
     * The opposite of pause.
     */
    public void unPause() {

        engine.unPause();
    }
}
