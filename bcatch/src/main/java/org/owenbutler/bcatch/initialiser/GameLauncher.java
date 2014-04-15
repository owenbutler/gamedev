package org.owenbutler.bcatch.initialiser;

import org.jgameengine.launcher.EngineLauncher;

/**
 * Launcher for bcatch game.
 *
 * @author Owen Butler
 */
public class GameLauncher {

    public static void main(String[] args) {

        String[] args2 = new String[] {"org/owenbutler/bcatch/bcatch.xml"};
        EngineLauncher.main(args2);

        System.exit(1);

    }

}
