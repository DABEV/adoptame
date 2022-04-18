
# AdoptaMe 🐾
_AdoptaMe es una plataforma de adopciones de mascotas que funciona como un conector entre Voluntarios, Adoptadores y Administradores. Se gestionan mascotas, noticias, solicitudes y donaciones._
- Uso de Spring MVC
- Thymeleaf
- MDBootstrap

## Contenidos 📖
- [Comenzando](#comenzando)
- [Requisitos](#requisitos)
- [Base de datos](#base-de-datos)
- [Ejecución](#ejecucion)

<a name="comenzando"></a>
## Comenzando 🚀
- Clona el repositorio de [AdoptaMe](https://github.com/DABEV/adoptame.git)
- Asegúrate de contar con una conexión de MysQL
- Lee las indicaciones en el 
- Genera en tu disco local las siguientes carpetas
```
.
├── C:
│   └── mascotas
|       └── img-mascotas
```
- Ejecute el script de la base de datos que se detallerá en la sección de base de datos
- Ejecute el proyecto

<a name="requisitos"></a>
## Requisitos 📋

- Tener MySQL 8
- Contar con Java 11

<a name="base-de-datos"></a>
## Base de datos 📁

Las tablas se generarán auntomáticamente, solo debes de crear una base de datos para alojarla y colocar su nombre y acceso en las líneas del properties:

```
spring.datasource.url=jdbc:mysql://<server>:<port>/<db_name>?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true
spring.datasource.username=<user>
spring.datasource.password=<password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

```

Como parte de los protocolos de seguridad implementados, se realizaron procedimientos alamacenados que permiten almacenar un bitácora los movimientos realizados (Modificaciones, Inserciones, Eliminaciones)

Para poder implementar estos procedimientos, favor de ejecutar los scripts en la ruta:

```
.
├── sql
│   ├── inserts.sql
│   └── procedimientos-almacenados.sqlfile12.ext

```
### Primera ejecución
_Para la primera ejecución necesitarás tener dentro del archivo **application.properties** (Generado a partir del archivo application.properties.example) la línea:_


```
spring.jpa.hibernate.ddl-auto=create
```

### Ejecución contínua

_Deberás sustituir el create de la línea anterios mostrada por update


```
spring.jpa.hibernate.ddl-auto=update
```

<a name="ejecucion"></a>
## Ejecución ⚙️
Para una correcta configuración asegúrate de haber creado y configurado el **application.properties** con tus datos

### Métodos de ejecución
- Ejecuta la aplicación ejecutando el archivo **AdoptameApplication.java**

Puedes acceder a él en un editor de código como:

- Spring Boot (Eclipse)
- Visual Studio
- IntelliJ

# ¡Disfruta de la aplicación!

_Puedes seguirnos en Github_

## Integrantes
Programadores  | Perfil
------------- | -------------
Gandy Ávila  | [GandyA23](https://github.com/GandyA23)
Violeta MIllán  | [vimillan](https://github.com/vimillan)
Brian Medrano  | [DallasSpawn](https://github.com/DallasSpawn)
Gustavo López  | [GustavoLopez04](https://github.com/GustavoLopez04)
Dafne Jimenez  | [DafneJ](https://github.com/DafneJ)

