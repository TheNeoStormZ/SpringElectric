# SpringElectric
## Ejecución local
Para poder ejecutar la aplicación, debemos tener instalado MongoDB. Para ello, el primer paso será instalar en nuestro sistema MongoDB. Si ya se tiene mongoDB instalado y configurado, salte al paso 3.

1. Nos dirigimos a  https://www.mongodb.com/try/download/community para descargar la última versión de mongodb, en su versión .msi si estamos en Windows.
2. Ejecuta el instalador de MongoDB y sigue las instrucciones del asistente de configuración.

    a. Puede ser que durante el instalador pregunte si desea instalar MongoDB como un servicio de Windows.

    b. Si instalas MongoDB como un servicio de Windows, significa que se iniciará automáticamente cuando arranques el sistema y se ejecutará en el fondo sin que tengas que abrirlo manualmente. Esto puede ser útil si quieres tener MongoDB siempre disponible y no te preocupa el consumo de recursos

    c. Si no instalas MongoDB como un servicio de Windows, significa que tendrás que ejecutarlo desde el intérprete de comandos cada vez que quieras usarlo. Esto puede ser útil si quieres tener más control sobre cuándo y cómo se ejecuta MongoDB y ahorrar recursos cuando no lo necesites.

    - Para ello, deberás navegar a la carpeta de instalación de mongo (ej: C:\mongodb\bin) y ejecutar desde ahí un intérprete de comandos (cmd) donde se ejecutará el comando “mongod.exe”.


3. Una vez instalado mongo, debemos asegurarnos de que tenemos la versión de Java apropiada instalada. 
    - Para ello, dentro de un intérprete de comandos, podemos ejecutar el comando:
    
            java –version
4. Debemos tener instalado Java 17 o superior.
 Si no es así, podemos descargar esta versión desde la pagina a continuación, siempre eligiendo la versión .MSI cuando sea posible.:
```
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html 
```


5. A continuación, debe clonar el repositorio del proyecto o descargarlo como un .zip.

    a. Si se descarga como un .zip asegúrese de al extraerlo se almacena en una carpeta cuyo único contenido es el proyecto en sí y no se comparte con más elementos, para evitar errores.

6. Con la carpeta del proyecto abierta en su directorio principal (debe ver un archivo readme), abrimos un intérprete de comandos ejecutamos el siguiente comando:
```
./mvnw spring-boot:run
```
7. El proyecto procederá a instalar todas las dependencias. Es un proceso automático, y cuando pasen unos segundos habrá finalizado, con el proyecto ya en ejecución.

    a. Se puede ver el proyecto en localhost:8080

# Licencia

La aplicación de muestra Spring PetClinic se publica bajo la versión 2.0 de la [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[spring-petclinic]: https://github.com/spring-projects/spring-petclinic
[spring-framework-petclinic]: https://github.com/spring-petclinic/spring-framework-petclinic
[spring-petclinic-angularjs]: https://github.com/spring-petclinic/spring-petclinic-angularjs 
[javaconfig branch]: https://github.com/spring-petclinic/spring-framework-petclinic/tree/javaconfig
[spring-petclinic-angular]: https://github.com/spring-petclinic/spring-petclinic-angular
[spring-petclinic-microservices]: https://github.com/spring-petclinic/spring-petclinic-microservices
[spring-petclinic-reactjs]: https://github.com/spring-petclinic/spring-petclinic-reactjs
[spring-petclinic-graphql]: https://github.com/spring-petclinic/spring-petclinic-graphql
[spring-petclinic-kotlin]: https://github.com/spring-petclinic/spring-petclinic-kotlin
[spring-petclinic-rest]: https://github.com/spring-petclinic/spring-petclinic-rest

