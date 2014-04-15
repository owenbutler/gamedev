package org.owenbutler.warpball.initialiser;

import org.jgameengine.launcher.EngineLauncher;

/**
 * Launcher for warpball.
 * 
 * @author Owen Butler
 */
public class GameLauncher {

    public static void main(String[] args) {

        String[] args2 = new String[] {"org/owenbutler/warpball/warpball.xml"};
        EngineLauncher.main(args2);

        System.exit(1);

    }

}
