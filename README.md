# PathFinder

## Web application for determining the shortest path between two points on the map considering given topographical GeoTIFF elevation raster.

Web-front uses [Leaflet open-source JavaScript library](https://github.com/Leaflet/Leaflet) for the map widget. Altitude is determined via [leaflet-geotiff-2](https://github.com/onaci/leaflet-geotiff-2) Leaflet plugin. Testing raster GeoTIFF file was aquired from [OpenTopography](https://opentopography.org).

Back-end is programmed using [Maven](https://maven.apache.org) with raw Java. Back-end programm consits of self-writen REST-API to get JSON data from web-front, graph builder class that transforms data recieved from web to a graph and a A* algorithm solver.
[Servlet](https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api) maven dependency was used for the REST-API. Google's [GSON](https://mvnrepository.com/artifact/com.google.code.gson/gson) maven dependency was used for quick Json <-> POJO-class conversion.
