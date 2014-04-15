package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelTwo extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level two");

        // setup the enemy spawns
        float time = 0;

        // one 1
        time += 1.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // one 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
            }
        });

        // one 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // two 1's
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
            }
        });

        // two 2's
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
            }
        });

        // 1 and 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
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
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnSmallSeeker();
            }
        });

        // boss 2
        time += 4.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy2(true);
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