package org.owenbutler.planetesimal.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;
import org.owenbutler.planetesimal.renderables.Asteroid1;
import org.owenbutler.planetesimal.renderables.Asteroid2;
import org.owenbutler.planetesimal.renderables.BaseAsteroid;
import org.owenbutler.planetesimal.renderables.LargeExplosion;
import org.owenbutler.planetesimal.renderables.Particle;
import org.owenbutler.planetesimal.renderables.Player;
import org.owenbutler.planetesimal.renderables.PlayerBullet1;
import org.owenbutler.planetesimal.renderables.SmokePuff;
import org.owenbutler.planetesimal.renderables.VerySmallExplosion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PlanetesimalGameLogic implements MouseListener {

    protected Engine engine;

    protected PlanetesimalHudManager hud;

    protected boolean listeningForPlayerSpawn;

    protected Player player;


    protected int score;

    List<Integer> highScores = new ArrayList<>();
    private int gameLevel;
    private int livingAsteroids;


    public PlanetesimalGameLogic(Engine engine) {
        this.engine = engine;

        hud = (PlanetesimalHudManager) engine.getHudManager();

        engine.getInputManager().addMouseListener(this);
    }


    public void gameStarts() {

        resetScore();

        hud.showIntroScreen();

        listeningForPlayerSpawn = true;

    }

    private void spawnLevel() {
        gameLevel++;
        livingAsteroids = gameLevel;

        for (int i = 0; i < gameLevel; ++i) {
            Asteroid1 asteroid = new Asteroid1(0, 0);

            float asteroidX, asteroidY;

            float yVel = RandomUtils.nextInt(GameConstants.ASTEROID_VEL_RANDOM) - (GameConstants.ASTEROID_VEL_RANDOM / 2);
            float xVel = RandomUtils.nextInt(GameConstants.ASTEROID_VEL_RANDOM) - (GameConstants.ASTEROID_VEL_RANDOM / 2);

            if (yVel < 0) {
                asteroidY = engine.getRenderer().getScreenHeight() + asteroid.getSurface().getHeight();
            } else {
                asteroidY = -asteroid.getSurface().getHeight();
            }

            asteroidX = RandomUtils.nextInt(engine.getRenderer().getScreenWidth());

            asteroid.setXY(asteroidX, asteroidY);
            asteroid.setVelXY(xVel, yVel);

            engine.addGameObject(asteroid);
        }

    }

    private void resetScore() {
        score = 0;
        hud.updateScore(score);
    }


    public void gameEnds() {

        gameLevel = 0;

        hud.showIntroScreen();
        saveScore();
        resetScore();
        hud.hideScore();

        killAllAsteroidsAndBullets();
    }


    private void killAllAsteroidsAndBullets() {

        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof BaseAsteroid) {
                ((BaseAsteroid) gameObject).gameOverRemove();
            }

            if (gameObject instanceof PlayerBullet1) {
                ((PlayerBullet1) gameObject).removeSelf();
            }
        }
    }


    private void saveScore() {
        highScores.add(score);
        Collections.sort(highScores);
        Collections.reverse(highScores);
        if (highScores.size() > 10) {
            highScores.remove(10);
        }
        hud.updateHighScores(highScores);
    }


    public void playerSpawns() {
        hud.hideIntroScreen();

        if (player == null) {
            player = new Player(GameConstants.PLAYER_SPAWNX, GameConstants.PLAYER_SPAWNY);
            engine.addGameObject(player);
            engine.getInputManager().addMovementListener(player);
            engine.getInputManager().addMouseListener(player);
        }

        player.setBlRender(true);
        player.setDead(false);
        player.setXY(GameConstants.PLAYER_SPAWNX, GameConstants.PLAYER_SPAWNY);

        hud.showScore();

        spawnLevel();
    }


    public void playerDies() {

        if (player.isDead()) {
            return;
        }

        player.setDead(true);
        player.setBlRender(false);

        engine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                listeningForPlayerSpawn = true;
            }
        });

        engine.getEventHandler().addEventInRepeat(this, 0.0f, 0.05f, 7, new Event() {
            public void trigger() {
                spawnExplosion();

            }
        });

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerDead).playSample();
        gameEnds();
    }


    private void spawnExplosion() {

        int randomFactor = 50;
        float playerX = player.getX();
        float playerY = player.getY();

        float x = playerX + (RandomUtils.nextInt(randomFactor) - (randomFactor / 2));
        float y = playerY + (RandomUtils.nextInt(randomFactor) - (randomFactor / 2));
        engine.addGameObject(new LargeExplosion(x, y));

        for (int i = 0; i < 10; ++i) {
            Particle particle = new Particle(playerX, playerY);
            particle.setVelXY(RandomUtils.nextInt(300) - 150, RandomUtils.nextInt(300) - 150);
            particle.setFadeAndRemove(1.6f);
            engine.addGameObject(particle);
        }

        for (int i = 0; i < 3; ++i) {
            VerySmallExplosion explosion = new VerySmallExplosion(playerX, playerY);
            explosion.setVelXY(RandomUtils.nextInt(200) - 100, RandomUtils.nextInt(200) - 100);
            engine.addGameObject(explosion);

            SmokePuff smokePuff = new SmokePuff(playerX, playerY);
            smokePuff.setVelXY(RandomUtils.nextInt(200) - 100, RandomUtils.nextInt(200) - 100);
            engine.addGameObject(smokePuff);
        }
    }


    public void asteroidDies(BaseAsteroid asteroid) {

        int scoreFromRock;

        if (asteroid instanceof Asteroid1) {
            scoreFromRock = 10;
            livingAsteroids--;
        } else if (asteroid instanceof Asteroid2) {
            scoreFromRock = 5;
        } else {
            scoreFromRock = 1;
        }

        score += scoreFromRock;

        hud.updateScore(score);

        if (livingAsteroids == 0) {
            spawnLevel();
        }
    }

    public Engine getEngine() {
        return engine;
    }


    public void setEngine(Engine engine) {
        this.engine = engine;
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
