#!/bin/bash
# Crear el .class
mkdir bin
mkdir bin/tarea1
javac Tarea1.java 
mv Tarea1.class ./bin/tarea1/Tarea1.class
ant
java -Xms128M -Xmx1024M -jar Tarea1.jar
