#!/bin/sh

# change this:
gameName='CHANGEME'

subFiles="src/main/jnlp/jnlp.xml assembleJNLP.sh manifest.txt pom.xml uploadGame.sh src/main/java/org/owenbutler/gamename/initialiser/Initialiser.java src/main/java/org/owenbutler/gamename/initialiser/GameLauncher.java src/main/java/org/owenbutler/gamename/logic/HudManager.java src/main/java/org/owenbutler/gamename/logic/GameLogic.java src/main/resources/org/owenbutler/gamename.xml src/main/java/org/owenbutler/gamename/renderables/Player.java src/main/java/org/owenbutler/gamename/constants/AssetConstants.java src/main/java/org/owenbutler/gamename/constants/GameConstants.java"

echo "starting substitutions"

for file in ${subFiles};
do
    cat ${file} | sed "s/JGE_ARTIFACT_ID/${gameName}/g" | sed "s/JGE_MAVEN_NAME/${gameName}/" \
     | sed "s/JGE_MANIFEST_PACKAGE/${gameName}/" | sed "s/JGE_JNLP_DESCRIPTION/${gameName}/" \
     | sed "s/JGE_JNLP_SHORT_DESCRIPTION/${gameName}/" > tmp.sub
    mv tmp.sub ${file}
done


chmod +x *.sh

# move the spring config file to the right place
mkdir src/main/resources/org/owenbutler/${gameName}
mv src/main/resources/org/owenbutler/gamename.xml src/main/resources/org/owenbutler/${gameName}/${gameName}.xml

mv src/main/java/org/owenbutler/gamename src/main/java/org/owenbutler/${gameName}

echo "done!"
