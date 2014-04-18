package org.owenbutler.invasion.launcher;

import org.jgameengine.launcher.EngineLauncher;

public class GameLauncher {

    public static void main(String[] args) {

        String[] args2 = new String[]{"org/owenbutler/invasion/invasion.xml"};
        EngineLauncher.main(args2);

        System.exit(1);

    }

}