<p align="center">
  <img width="300" height="300" src="https://vignette.wikia.nocookie.net/avengersalliance/images/b/b5/Magneto_Recruited.png/revision/latest?cb=20130416195249">
</p>

# Magneto-app

### Introducción 
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar
contra los X-Men.

### Descripción
Aplicación que puede detectar si un humano es mutante basándose en su secuencia de ADN la cual debe contenere al menos una secuencia de cuatro<sup>(1)</sup> letras iguales, de forma oblicua, horizontal o vertical, en base a un mensaje en formato JSON y que se expone mediante el verbo Post "_/mutant_" debiendo tener la siguiente estructura:
```
{
  "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTA"]
}
```
Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN.  

A su vez la aplicación expone un servicio mediante el verbo GET "_/stats_" donde se pueden obtener la estadísticas de las verificaciones de ADN, ejemplo:

```
{
  "count_mutant_dna":40, 
  "count_human_dna":100, 
  "ratio":0.4
}
```

## Pre-requisitos
La cantidad de letras de cada cadena nitrogenada debe ser **igual** a la cantidad de cadenas, es decir, si la cadena cuenta por ejemplo con 6 letras respetando el patrón (A,T,C,G) la cantidad de cadenas deben ser 6.  
Ejemplo 1: 
```
"ATGCGA" → "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTA"
```
Ejemplo 2:
```
"ATG" → "ATG","CAG","TTA"
```
A su vez la cantidad de letras **debe** ser mayor o igual a la cantidad de letras _**4**_ correspondientes a la cantidad mínima de letras igual que determinan si un ADN es mutante.
## Construido con

* [Java](https://www.java.com/es/download/) - El lenguaje utilizado
* [Maven](https://maven.apache.org/) - El administrador de dependencias 
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework para la exposición de Apis
* [Google Cloud SDK](https://cloud.google.com/sdk/) - Para la exposición de la Api en internet

## Instalacion
Ubicarse en algun lugar del equipo y ejecutar:

```
git clone https://github.com/elMauri/magneto-app.git
cd magneto-app
mvn clean package
```
En Windows:
```
java -jar target\magneto-app-1.0.0-SNAPSHOT.jar
```
En Linux:
```
java -jar target/magneto-app-1.0.0-SNAPSHOT.jar
```

## Exposición

La aplicación cuenta con la exposición de dos servicios:

#### **Servicio:**

```
“/mutant/”
```
el cual detecta si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato:

**HTTP Method**: POST → /mutant/
```
{
  "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

Dicho servicio se encuentra expuesto en la URL:

```
https://magneto-ml-app.appspot.com/magnetoapi/mutant/
```
Los codigos de respuestas manejados de este servicio son:

* **HTTP 200-OK** : _En caso de que un ADN sea Mutante_.  
* **403-Forbidden** : _En caso de que un ADN NO se Mutante_.  
* **406 Not Acceptable**: _En caso de que la cantidad de letras de cada cadena no se corresponda con la cantidad de cadenas (esto para hacer que la matriz sea de NxN)._  
* **406 Not Acceptable**: _En caso de que las letras de la cadena no cumpla con el patrón (A,T,C,G)._

#### **Servicio:**
```
“/stats/”
```
**HTTP Method**: GET → /stats/  

El cual retorna un Json con las estadísticas de las verificaciones de ADN  
ejemplo:
```
{
  "count_mutant_dna":40, 
  "count_human_dna":100, 
  "ratio":0.4
}
```
Dicho servicio se encuentra expuesto en la URL:  
```
https://magneto-ml-app.appspot.com/magnetoapi/stats/
```
Los codigos de respuestas manejados de este servicio son:

* **HTTP 200-OK** : _En caso de que no haya ningun problema con las estadisticas_.  

&nbsp; 
</br>
## Referencias
<sup>(1)</sup> Se podra modificar la cantidad de letras que conforman la base nitrogenada modificando el valor de la key <code>nitrogenous.base.mutant.size</code> en el archivo <code>src/main/resources/application.properties</code>
## Autor
* **Mauricio Borelli** - *Trabajo Inicial y Documentación*