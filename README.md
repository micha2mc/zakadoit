# Zakado IT
Para ejecutar la aplicación Zakado IT en local, se debe tener instalado JDK en su versión 21, MySQL que viene en el paquete wampserver64, por ejemplo, y tener acceso al repositorio público GIT para descargar el proyecto.

Tras comprobar estos requisitos mínimos, realizamos los pasos siguientes:

>• Descargar el proyecto desde el repositorio Git o clonarlo. El repositorio público de la aplicación es: https://github.com/micha2mc/zakadoit

>• Descomprimir el archivo del proyecto en un directorio de nuestro equipo.

>• Abrir el proyecto con cualquier IDE de desarrollo como IntelliJ o Visual Studio Code.
 
>• Ejecutamos el script database.sql de forma manual en el gestor de bases de datos MySQL para crear la base de datos.

>•	Opcionalmente ejecutar de forma manual (la aplicación ejecuta este script automáticamente) el script data.sql para insertar datos iniciales en la base de datos y crear el primer usuario administrador.

>•	Verificar las líneas de conexión a la base de datos en el archivo application.yml

>•	Abrir el archivo Application.java y ejecutarlo, ver figura 23.

>•	A partir de la configuración se abre un navegador que tengamos configurado por defecto de forma automática o introducimos el enlace: http://localhost:8081 y se nos abre la página de Login.

>•	Iniciamos sesión y tendremos acceso a la aplicación. A partir de este punto consultar el apéndice C.


![img_3.png](img_3.png)
