Small collection of java utilities to make 2d games.

Install
=======

Run the script:

    ./installSlickLibs.sh

Run the maven goal:

    mvn generate-resources

This will expand the native libraries to ${project.root}/target/natives.  Add this directory to your library path in your IDE:

eg:

    -Djava.library.path=/Users/user/dev/gamedev/jGameEngine/target/natives