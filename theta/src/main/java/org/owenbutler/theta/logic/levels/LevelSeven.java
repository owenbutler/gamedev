package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelSeven extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level seven");

        // setup the enemy spawns
        float time = 0;

        // 2 2 2 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
            }
        });

        // 3 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        // 4 4 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy4();
            }
        });

        // 1 2 1 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy2();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
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
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy4();
            }
        });

        // 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // 3b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy3(true);
            }
        });

        // 1b 2b 3b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy1(true);
                spawnEnemy2(true);
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