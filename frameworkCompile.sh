#!/bin/bash

echo "Compiling Framework"
scala-2.7.7.final/bin/scalac -cp asteroidsfw.jar java2d/AsteroidGraphics.scala java2d/BulletGraphics.scala java2d/Java2dFactory.scala java2d/Java2dGraphics.scala java2d/ShipControl.scala java2d/ShipGraphics.scala java2d/SpaceStationGraphics.scala

echo "Compiling Ship"
javac -cp asteroidsfw.jar ships/y2018/landon/landon.java

echo "Injecting New Framework"
jar uf asteroidsfw.jar asteroidsfw/java2d/*.class

echo "Injecting Ship"
jar uf asteroidsfw.jar ships/y2018/landon/landon.class

echo "Running Asteroid Game"
java -Djava.library.path=lib/native/macosx -jar asteroidsfw.jar gui=java2d file=input
