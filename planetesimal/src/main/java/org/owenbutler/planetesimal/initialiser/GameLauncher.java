package org.owenbutler.planetesimal.initialiser;

import org.jgameengine.launcher.EngineLauncher;

public class GameLauncher {

    public static void main(String[] args) {

        String[] args2 = new String[]{"org/owenbutler/planetesimal/planetesimal.xml"};
        EngineLauncher.main(args2);

        System.exit(1);

    }

}
