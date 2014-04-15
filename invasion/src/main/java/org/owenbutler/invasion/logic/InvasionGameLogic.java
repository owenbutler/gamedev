package org.owenbutler.invasion.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.owenbutler.invasion.constants.GameConstants;
import org.owenbutler.invasion.renderables.BaseBrick;
import org.owenbutler.invasion.renderables.Enemy;
import org.owenbutler.invasion.renderables.Planet;
import org.owenbutler.invasion.renderables.Player;
import org.owenbutler.invasion.renderables.PlayerBullet1;
import org.owenbutler.invasion.renderables.Spaceman;

import java.util.Set;

public class InvasionGameLogic implements MouseListener {

    protected Engine engine;

    protected InvasionHudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;

    protected float enemySpawnTime;

    public InvasionGameLogic(Engine engine) {
        this.engine = engine;

        engine.getInputManager().addMouseListener(this);

        enemySpawnTime = GameConstants.ENEMY_SPAWN_TIME;

        setNextEnemySpawnTime();
    }

    private void setNextEnemySpawnTime() {
        engine.getEventHandler().addEventIn(this, enemySpawnTime, new Event() {
            public void trigger() {
                spawnEnemy();
            }
        });
    }

    private void spawnEnemy() {

        if (gameStarted) {
            engine.addGameObject(new Enemy());
            modifySpawnTime();
        }

        setNextEnemySpawnTime();
    }

    private void modifySpawnTime() {
        if (enemySpawnTime > 0.3) {
            enemySpawnTime -= GameConstants.ENEMY_SPAWN_MOD;
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

        engine.addGameObject(new Planet());
    }

    private void showIntroScreen() {

        if (hudManager == null) {
            hudManager = (InvasionHudManager) engine.getHudManager();
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
            engine.addGameObject(player);
        }

        player.setBlRender(true);
        player.setXY(400, 580);
        player.setDead(false);

        enemySpawnTime = GameConstants.ENEMY_SPAWN_TIME;

        // spawn bases
        spawnBases();

    }

    private void spawnBases() {

        int startX = 130;

        for (int i = 0; i < GameConstants.NUMBER_BASES; ++i, startX += 100) {

            for (int y = 0; y < GameConstants.BASE_HEIGHT; ++y) {
                for (int x = 0; x < GameConstants.BASE_WIDTH; ++x) {

                    BaseBrick baseBrick = new BaseBrick(startX + (x * 6), 500 + (y * 6));
                    engine.addGameObject(baseBrick);
                }

            }

        }
    }

    public void playerDies() {

        player.setBlRender(false);
        player.dead();
        gameStarted = false;
        hudManager.setShowGameOver(true);

        engine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                hudManager.showIntroScreen();
                hudManager.setShowGameOver(false);

                listeningForPlayerSpawn = true;

            }
        });

        // remove all bases
        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            boolean remove = false;
            if (gameObject instanceof Enemy) {
                remove = true;
            } else if (gameObject instanceof BaseBrick) {
                remove = true;
            } else if (gameObject instanceof PlayerBullet1) {
                remove = true;
            } else if (gameObject instanceof Spaceman) {
                remove = true;
            }

            if (remove) {
                engine.removeGameObject(gameObject);
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

}
