package org.owenbutler.theta.initialiser;

import org.jgameengine.launcher.EngineLauncher;

/**
 * Launcher for theta game.
 *
 * @author Owen Butler
 */
public class GameLauncher {

    public static void main(String[] args) {

        String[] args2 = new String[] {"org/owenbutler/theta/theta.xml"};
        EngineLauncher.main(args2);

        System.exit(1);

    }

}
