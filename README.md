# PathFinder

## The Back-End Part Of Web Application for Topography-Aware Shortest Path Calculation

**PathFinder** is a web application that calculates the shortest path between two points on a map, accounting for terrain elevation using a topographical GeoTIFF raster.
This repo is for back-end only. Front-end can be found [here](https://github.com/Nighterion/PathFinder-web)

## Front-End

- Built with [Leaflet](https://github.com/Leaflet/Leaflet), an open-source JavaScript library for interactive maps.
- Elevation data is extracted from GeoTIFF files using the [leaflet-geotiff-2](https://github.com/onaci/leaflet-geotiff-2) plugin.
- The demo GeoTIFF raster is sourced from [OpenTopography](https://opentopography.org).

## Back-End

- Implemented in **Java** using **Maven**.
- Includes a custom-built **REST API** for communication with the front end.
- Core components:
  - A **Graph Builder** that converts raster data into a graph structure.
  - An **A* pathfinding algorithm** for solving the shortest path problem.
- Uses:
  - [Jakarta Servlet API](https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api) for REST endpoints.
  - [GSON](https://mvnrepository.com/artifact/com.google.code.gson/gson) for converting between JSON and Java objects.

## Screenshots

![Screenshot 1](https://github.com/user-attachments/assets/d50ee509-c465-49d5-a99b-c7613ede15a9)  
![Screenshot 2](https://github.com/user-attachments/assets/83c1c05e-b3f5-446b-b46b-f2277ad1ab15)
