package org.owenbutler.theta.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.renderables.AbstractEnemyBase;
import org.owenbutler.theta.renderables.Enemy1;
import org.owenbutler.theta.renderables.Enemy2;
import org.owenbutler.theta.renderables.Enemy3;
import org.owenbutler.theta.renderables.Enemy4;
import org.owenbutler.theta.renderables.SmallHomingEnemy;

import java.util.ArrayList;
import java.util.List;

public abstract class LevelSupport implements Level {

    protected Engine gameEngine;

    protected LevelEndListener levelEndListener;

    List<AbstractEnemyBase> levelEnemies = new ArrayList<>();

    HudManager hudManager;

    protected boolean levelStarted;

    public void in(float time, Event newEvent) {

        gameEngine.getEventHandler().addEventIn(this, time, newEvent);
    }

    protected void spawnEnemy1(boolean bossmode) {

        float xPos = RandomUtils.nextInt(640);
        float yPos = -100;

        Enemy1 enemy1 = new Enemy1(xPos, yPos, bossmode);

        addAndTrackEnemy(enemy1);
    }

    protected void spawnEnemy2(boolean bossmode) {
        float xPos = RandomUtils.nextInt(640);
        float yPos = -100;

        Enemy2 enemy2 = new Enemy2(xPos, yPos, bossmode);

        addAndTrackEnemy(enemy2);
    }

    protected void spawnEnemy3(boolean bossmode) {
        float xPos = RandomUtils.nextInt(640);
        float yPos = -100;

        Enemy3 enemy3 = new Enemy3(xPos, yPos, bossmode);

        addAndTrackEnemy(enemy3);
    }

    protected void spawnEnemy4(boolean bossmode) {
        float xPos = RandomUtils.nextInt(640);
        float yPos = -100;

        Enemy4 enemy4 = new Enemy4(xPos, yPos, bossmode);

        addAndTrackEnemy(enemy4);
    }

    protected void spawnSmallSeeker() {

        SmallHomingEnemy smallHomingEnemy = new SmallHomingEnemy(RandomUtils.nextInt(640), -10);

        addAndTrackEnemy(smallHomingEnemy);

    }

    // boss modes for the enemies
    protected void spawnEnemy1() {
        spawnEnemy1(false);
    }

    protected void spawnEnemy2() {
        spawnEnemy2(false);
    }

    protected void spawnEnemy3() {
        spawnEnemy3(false);
    }

    protected void spawnEnemy4() {
        spawnEnemy4(false);
    }

    protected void addAndTrackEnemy(AbstractEnemyBase largeEnemy) {

        // add this largeEnemy to a list that we might use later
        levelEnemies.add(largeEnemy);

        gameEngine.addGameObject(largeEnemy);

        levelStarted = true;
    }

    public void endLevelWhenAllEnemiesDead() {

        // set an event to check whether all enemies are dead
        in(1, new Event() {
            public void trigger() {

                checkIfAllEnemiesAreDead();
            }
        });
    }

    protected void checkIfAllEnemiesAreDead() {

        List<AbstractEnemyBase> enemiesToRemove = new ArrayList<>();

        for (AbstractEnemyBase levelLargeEnemy : levelEnemies) {
            if (levelLargeEnemy.isDead()) {
                enemiesToRemove.add(levelLargeEnemy);
            }
        }

        levelEnemies.removeAll(enemiesToRemove);

        // if al enemies are dead, they are removed now
        // so end the level
        if (levelEnemies.size() == 0 && levelStarted) {
            end();
        } else {

            // else check again
            in(1, new Event() {
                public void trigger() {

                    checkIfAllEnemiesAreDead();
                }
            });
        }
    }

    public void end() {

        levelEndListener.levelAboutToEnd();

        levelEndListener.levelEnded();
    }

    public void setEngine(Engine gameEngine) {
        this.gameEngine = gameEngine;

        hudManager = (HudManager) gameEngine.getHudManager();
    }

    public void setLevelEndListener(LevelEndListener levelEndListener) {
        this.levelEndListener = levelEndListener;
    }

    protected void showLevelIntro(String levelText) {

        hudManager.setShowLevelIntro(true);
        hudManager.setLevelIntro(levelText);

        in(6, new Event() {
            public void trigger() {
                hudManager.setShowLevelIntro(false);
            }
        });

//        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyShoot).stopSample();
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyShoot).playSample();
    }

    protected void showLevelText(String text) {
        hudManager.setShowLevelText(true);
        hudManager.setLevelText(text);

        in(4, new Event() {
            public void trigger() {
                hudManager.setShowLevelText(false);
            }
        });

    }

    public void killLevel() {

        gameEngine.getEventHandler().removeEventsForOwner(this);
        this.levelEnemies.clear();
    }

}
