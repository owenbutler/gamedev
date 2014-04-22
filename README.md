Small collection of java utilities to make 2d games.

# Description

## jGameEngine

Some small utilities for playing sound, loading and displaying 2d graphics.

Contains 194x, a sort of testbed using some 1942/3 inspired graphics.

## theta

Built for a 2 week game jam.  Top down shooter with 10 levels.

## invasion

Space invaders clone where you play as the alien defending against kamikaze spacemen.

## bcatch

Unfinished prototype where you catch bullets to throw them back at the enemy

## warpball

A pong clone

## planetesimal

An asteroids clone

Install
=======

Run the script:

    ./installSlickLibs.sh

Run the maven goal:

    mvn generate-resources

This will expand the native libraries to ${project.root}/target/natives.  Add this directory to your library path in your IDE:

eg:

    -Djava.library.path=/Users/user/dev/gamedev/jGameEngine/target/natives
