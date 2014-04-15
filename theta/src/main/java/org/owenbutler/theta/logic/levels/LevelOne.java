package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelOne extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting

        showLevelIntro("level one");

        // setup the enemy spawns
        in(1.0f, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnSmallSeeker();
            }
        });

        float time = 3.0f;
        for (int i = 0; i < 5; ++i) {
            in(time, new Event() {
                public void trigger() {
                    spawnEnemy1();
                    spawnSmallSeeker();
                }
            });

            time += 2.0f;
        }

        // boss time!
        time += 4.0f;

        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");
                spawnEnemy1(true);
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
