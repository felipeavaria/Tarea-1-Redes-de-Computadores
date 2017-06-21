# Redes de Computadores


## Integrantes


| Nombre | Rol    |
|:-------|:-------|
| Felipe Avaria | 2923547-3 |
| Rolando Casanueva | 201204505-3 |
| Andrés Cifuentes | 201004652-4 |


-----------------------------------

## Tarea 1

Para esta tarea se requiere la utilización de [Java 1.7 o posterior](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html), a lo cual se debe agregar su [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) corespondiente. Particularmente consideramos que la utilización de *IDLEs* va de la mano con las preferencias de quien desarrolle, aún así aconsejamos la utilización de [Eclipse](https://www.eclipse.org/downloads/?) o [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows).

La aplicación requiere la utilización de ANT y para casos de *Windows* tener el directorio de *PATH* actualizado con las herramientas.

### Instrucciones

Desde una consola Linux, o bien, una consola bash de Windows:

```bash
:root/> cd Compilada
:root/Compilada/> sh compilar.sh 
```
Estas instrucciones compilarán y correrán el programa. **NOTAR** si el programa ya se ha compilado previamente, se debe correr desde la última carpeta
```bash
:root/Compilada/> java -Xms128M -Xmx1024M -jar Tarea1.jar
```

El servidor correrá en el puerto `port:8080` y puede accederse a su gestión web desde
```
localhost:8080
127.0.0.1:8080
```