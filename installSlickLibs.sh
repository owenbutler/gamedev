cd lib/slick
mvn install:install-file -Dfile=slick-util.jar -DgroupId=com.cokeandcode.slick -DartifactId=slick-util -Dversion=20140416 -Dpackaging=jar
mvn install:install-file -Dfile=jogg-0.0.7.jar -DgroupId=com.cokeandcode.slick -DartifactId=jogg -Dversion=0.0.7 -Dpackaging=jar
mvn install:install-file -Dfile=jorbis-0.0.15.jar -DgroupId=com.cokeandcode.slick -DartifactId=jorbis -Dversion=0.0.15 -Dpackaging=jar
cd -