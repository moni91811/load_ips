# load_ips

# Descripción proyecto.
El proyecto permite realizar la cargar y consultar información de una base de datos de geolocalización por IP que consta de combinaciones entre localización geográfica y rangos de direcciones IPv4. La carga se realiza a través de un archivo plano con una estructura de datos determinada.
Cada registro indica una ubicación geográfica (país y su código IANA, región, ciudad, latitud, longitud) asignada a un rango de direcciones IP. 
Para definir este rango, los campos IP_from e IP_to corresponden a las direcciones IP inicial y final de dicho rango en formato decimal. 

# Nota: 
Tener en cuenta que la información IP_from, IP_to corresponde al valor decimal del rango de ips que se puede calcular de la siguiente manera:

(segmento_1 x (256)^3) + (segmento_2 x (256)^2) + (segmento_3 x (256)^1) + (segmento_4 * (256)^0)

(segmento_1 x 16777216) + (segmento_2 x 65536) + (segmento_3 x 256) + (segmento_4)

Ejemplo:
La conversión de la dirección IP 201.184.37.54 en formato decimal sería:
(201 x 16777216) + (184 x 65536) + (37 x 256) + (54) = 3384288566

# Arquitectura
1. Lenguaje de programación : Java - Microservicios Rest con Spring Boot.
2. Versión Java : 1.8
3. Base de datos Relacional : MySQL
4. Tipo de archivo permitido : csv
5. Estructura del archivo: IP_from, IP_to, Country_code, Country, Region, City, Latitude, Longitude, TimeZone

# Servicios REST
1. POST ip/upload : Permite a través del archivo cargar la información.
2. GET ip/{ip} : De acuerdo a una ip, retorna la información correspondiente a {Country_code, Region, City, Timezone}

# Pruebas Aplicación:
# ip/upload
Consumir el siguiente endpoint http://host:port/ip/upload

En la sección form-data agregue un archivo con el nombre file y seleccione desde el explorador de su computador el archivo con extensión .csv que tenga la estructura mencionada anteriormente. En caso contrario se visualiza un mensaje de validación.

Una vez el archivo se carga se genera el siguiente mensaje de éxito: 
{
    "message": "Archivo + Nombre archivo + cargado exitosamente.",
    "data": null
}

En caso contrario, se visualizará el error al realizar el cargue.

# ip/{id}
Consumir el siguiente endpoint http://host:port/ip/{id}

Consulte la información que se encuentra almacenada en la base de datos de acuerdo a la ip suministrada en la consulta. 

Ejemplo  http://host:port/ip/1.1.1.1
En caso de encontrar información en la base de datos se visualiza el siguiente mensaje, donde se retorna la lista de información almacenada:

{
    "message": "Registros encontrados con éxito.",
    "data": [
        {
            "countryCode": "AU",
            "region": "VICTORIA",
            "city": "RESEARCH",
            "timezone": "?"
        }
    ]
}

En caso contrario
{
    "message": "No se encontró información para la IP + ip.",
    "data": null
}

En general considero que el diseño de base de datos y arquitectura mencionadas anteriormente están a la vanguardia de la tecnología y el desarrollo de software. Se utiliza el patrón Chained debido a que la arquitectura está dispuesta a que los servicios se puedan orquestar y sincronizar para generar una única respuesta.

Dentro del proyecto se puede evidenciar que los paquetes contienen cierta funcionalidad y respetan los atributos de la programación limpia (clean code) y los principios SOLID que permite mantener una alta cohesión y un bajo acoplamiento con lo cual se consigue un mejor escalamiento, una mayor robustez y software más estable

S – Single Responsibility Principle (SRP)
O – Open/Closed Principle (OCP)
L – Liskov Substitution Principle (LSP)
I – Interface Segregation Principle (ISP)
D – Dependency Inversion Principle (DIP)

 

