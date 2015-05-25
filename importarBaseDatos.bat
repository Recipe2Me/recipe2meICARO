echo Comenzamos el proceso de importacion de la base de datos a su instancia de mongo en local.
echo Asegurese de que no existe una base de datos llamada recipe2me para que no se produzcan conflictos.
mongorestore dump\recipe2me 
pause