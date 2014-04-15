package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelSix extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level six");

        // setup the enemy spawns
        float time = 0;

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
                spawnSmallSeeker();
            }
        });

        // 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
                spawnEnemy4();
            }
        });

        // 3 3 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnSmallSeeker();
            }
        });

        // 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // 1
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // 1b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy1(true);
            }
        });

        // 2b 3b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy3(true);
                spawnEnemy3(true);
            }
        });

        time += 4.0f;

        in(time, new Event() {
            public void trigger() {
                endLevelWhenAllEnemiesDead();
            }
        });
    }

}