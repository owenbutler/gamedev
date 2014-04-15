#!/bin/sh

subFiles="src/main/jnlp/jnlp.xml assembleJNLP.sh manifest.txt pom.xml uploadGame.sh"

echo "starting substitutions"

for file in ${subFiles};
do
    cat ${file} | sed 's/JGE_ARTIFACT_ID/invasion/' | sed 's/JGE_MAVEN_NAME/invasion/' \
     | sed 's/JGE_MANIFEST_PACKAGE/org.owenbutler.invasion.launcher/' | sed 's/JGE_JNLP_DESCRIPTION/a game about invasion/' \
     | sed 's/JGE_JNLP_SHORT_DESCRIPTION/a game about invasion/' > tmp.sub
    mv tmp.sub ${file}
done

chmod +x *.sh

echo "done!"
