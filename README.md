RECIPE2ME
=========

Este proyecto hace uso del gestor de dependencia y construcción gradle. No es necesario instalarlo en el sistema puesto que incluye una copia con el propio proyecto.

Para ejecutarlo (cumpliendo las dependencias del proyecto como la base de datos y la localización de los recursos necesarios) solo hace falta ejecutar en la linea de comando:
./gradlew runSimple

Dependencias:

-Base de Datos Mongo en local con la configuración por defeco<br>
-Gate Instalado<br>
-Definir las rutas de Gate en icaro.aplicaciones.recursos.extractorSemantico.ConfigRutasExtractorSemantico.java<br>