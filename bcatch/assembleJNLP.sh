#!/bin/sh

# clean up any old stuff
mvn clean

# compile and create jar from main src
cd ..
mvn install
cd -

# create directory structure I'm going to need
mkdir target/jnlp

echo "copying existing jars"

# copy all the jars I'm going to need

# copy our own jar
cp target/bcatch-1.0-SNAPSHOT.jar target/jnlp/

cp ~/.m2/repository/org/owenbutler/jGameEngine/1.0-SNAPSHOT/jGameEngine-1.0-SNAPSHOT.jar target/jnlp/
cp ~/.m2/repository/org/springframework/spring-core/2.5.2/spring-core-2.5.2.jar target/jnlp/
cp ~/.m2/repository/org/springframework/spring-beans/2.5.2/spring-beans-2.5.2.jar target/jnlp/
cp ~/.m2/repository/commons-lang/commons-lang/2.2/commons-lang-2.2.jar target/jnlp/
cp ~/.m2/repository/log4j/log4j/1.2.14/log4j-1.2.14.jar target/jnlp/
cp ~/.m2/repository/commons-logging/commons-logging-api/1.0.4/commons-logging-api-1.0.4.jar target/jnlp/
cp ~/.m2/repository/slick-util/jogg/0.0.7/jogg-0.0.7.jar target/jnlp/
cp ~/.m2/repository/slick-util/jorbis/0.0.15/jorbis-0.0.15.jar target/jnlp/
cp ~/.m2/repository/slick-util/slick-util/20080319/slick-util-20080319.jar target/jnlp/
cp ~/lib/lwjgl-1.1.4/jar/lwjgl.jar target/jnlp/

# copy jars for native libs
cp ~/lib/lwjgl-1.1.4/native/*.jar target/jnlp/

echo "adding manifest to jar"

# add our manifest file to our jar
jar ufm target/jnlp/bcatch-1.0-SNAPSHOT.jar manifest.txt


# keytool -genkey -keystore jGameEngineStore -alias engineStore
# Enter keystore password:  45rtfgvb

echo "signing jars"

# sign jars
for file in `find target/jnlp -name "*.jar"`;
do
    pack200 --modification-time=latest --deflate-hint=true --strip-debug --repack $file
    jarsigner -keystore jGameEngineStore -storepass 45rtfgvb -keypass 45rtfgvb $file engineStore
    pack200 --modification-time=latest --deflate-hint=true --strip-debug ${file}.pack.gz $file
done

# create var files
cd target/jnlp
for file in `ls -1 *.jar`;
do
    # create var file
# URI: packed/file.jar.pack.gz
# Content-Type: x-java-archive
# Content-Encoding: pack200-gzip

# URI: unpacked/file.jar
# Content-Type: x-java-archive
    echo "URI: ${file}" > ${file}.var
    echo "" >> ${file}.var
    echo "URI: packed/${file}.pack.gz" >> ${file}.var
    echo "Content-Type: x-java-archive" >> ${file}.var
    echo "Content-Encoding: pack200-gzip" >> ${file}.var
    echo "" >> ${file}.var
    echo "URI: unpacked/${file}" >> ${file}.var
    echo "Content-Type: x-java-archive" >> ${file}.var

done

mkdir packed
mv *.pack.gz packed/
mkdir unpacked
mv *.jar unpacked/

cd -

# copy jnlp file and .htaccess
cp src/main/jnlp/jnlp.xml target/jnlp/game.jnlp
cp src/main/jnlp/.htaccess target/jnlp/


# copy splash and icon file
cp gfxPSD/splash.gif target/jnlp/
cp gfxPSD/icon.gif target/jnlp/
