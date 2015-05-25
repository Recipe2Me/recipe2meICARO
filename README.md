RECIPE2ME
=========

[![Stories in Ready](https://badge.waffle.io/Recipe2Me/recipe2meICARO.svg?label=ready&title=Ready)](http://waffle.io/Recipe2Me/recipe2meICARO) 

Este proyecto hace uso del gestor de dependencia y construcción gradle. No es necesario instalarlo en el sistema puesto que incluye una copia con el propio proyecto.

Para ejecutarlo (cumpliendo las dependencias del proyecto como la base de datos y la localización de los recursos necesarios) solo hace falta ejecutar en la linea de comando:
./gradlew runSimple

Dependencias:

-Base de Datos Mongo en local con la configuración por defeco<br>
-Gate Instalado<br>
-Definir las rutas de Gate en icaro.aplicaciones.recursos.extractorSemantico.ConfigRutasExtractorSemantico.java<br>

Importar base de datos:

-Windows
Ejecutar el ejecutable importarBaseDatos.bat
-Linux y Mac
Ejecutar en la linea de comando desde el proyecto: mongorestore dump/recipe2me

Importar plugin a Gate:
En cualquier sistema operativo copiar la carpeta ANNIECitas a la carpeta plugin de Gate.
