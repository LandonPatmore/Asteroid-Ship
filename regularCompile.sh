#!/bin/bash

echo "Compiling Ship"
javac -cp asteroidsfw.jar ships/y2018/landon/*.java

echo "Injecting Ship"
jar uf asteroidsfw.jar ships/y2018/landon/*.class

echo "Compiling Ship"
javac -cp asteroidsfw.jar ships/y2018/marcello/*.java

echo "Injecting Ship"
jar uf asteroidsfw.jar ships/y2018/marcello/*.class

echo "Running Asteroid Game"
java -Djava.library.path=lib/native/macosx -jar asteroidsfw.jar gui=java2d file=input
