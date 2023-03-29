import {MapContainer, TileLayer, useMapEvents, Marker, useMap, LayersControl, LayerGroup, ImageOverlay} from 'react-leaflet'
import "leaflet";
import "leaflet-geotiff-2";
import "leaflet-geotiff-2/dist/leaflet-geotiff-rgb";
import "plotty"
import "leaflet-geotiff-2/dist/leaflet-geotiff-plotty"

//import "leaflet-geotiff"
import React, {useState, useRef, useEffect, Component} from "react"
import {TextField, Button, Grid, Typography, Paper, Card, AppBar, ThemeProvider, createTheme} from "@mui/material"
import axios from "axios"
import markerA from './marker-a.svg'
import markerB from './marker-b.svg'
import bonfire1 from './bonfire1.png'
import bonfire2 from './bonfire2.png'
import '@fontsource/roboto/300.css'
import './App.css'
import 'leaflet/dist/leaflet.css'
import SRTM from './viz.SRTMGL3_color-relief.tif'
import {layers} from "leaflet/src/control/Control.Layers";


let center = [55.76, 37.64];

let L = window.L;

let iconA = L.icon({
    iconUrl: markerA,
    iconSize: [38, 95],
    iconAnchor: [19, 64],
    popupAnchor: [-3, -76],
});

let iconB = L.icon({
    iconUrl: markerB,
    iconSize: [38, 95],
    iconAnchor: [19, 64],
    popupAnchor: [-3, -76],
});

const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    },
});

let flag = false;



const App = (props) => {
    //const mapRef = React.createRef();
    const [firstCoordStr, setFCoordStr] = useState("");
    const [secondCoordStr, setSCoordStr] = useState("");
    let [firstCoords, setFCoords] = useState(null);
    let [secondCoords, setSCoords] = useState(null);

    const renderer = L.LeafletGeotiff.rgb();

    const options = {
        // Optional, band index to use as R-band
        //rBand: 0,
        // Optional, band index to use as G-band
        //gBand: 1,
        // Optional, band index to use as B-band
        //bBand: 2,
        // band index to use as alpha-band
        // NOTE: this can also be used in combination with transpValue, then referring to a
        // color band specifying a fixed value to be interpreted as transparent
        //alphaBand: 0,
        // for all values equal to transpValue in the band alphaBand, the newly created alpha
        // channel will be set to 0 (transparent), all other pixel values will result in alpha 255 (opaque)
        transpValue: 255,
        useWorker: true,
        renderer: renderer,
    };


    let layer = L.leafletGeotiff(SRTM, options);



    function GeoLayer() {

        var osm = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap'
        });

        const map = useMap();

        map.attributionControl.setPrefix('');

        if(!flag) {
            //layer.addTo(map);

            map.addLayer(osm);
            var overlayMaps = {
                "GeoTIFF": layer
            };

            var baseMaps = {
                "OpenStreetMap": osm,
            };
            let layerControl = L.control.layers(baseMaps, overlayMaps).addTo(map);
            flag = true;
        }


        return null;
    }
    function LocationMarkerA() {
        const map = useMapEvents({
             click(e) {
                const coords = [e.latlng.lat, e.latlng.lng];
                setFCoords(coords);
                setFCoordStr(
                    coords[0].toPrecision(8).toString() +
                    ", " +
                    coords[1].toPrecision(8).toString());


                    let elevation = layer.getValueAtLatLng(e.latlng.lat,e.latlng.lng);
                    console.log(elevation);
            },

        })

        return firstCoords === null ? null : (
            <Marker
                position={firstCoords}
                icon={iconA}>
            </Marker>
        )
    }
    function LocationMarkerB() {
        const map = useMapEvents({
            contextmenu(e) {
                const coords = [e.latlng.lat, e.latlng.lng];
                setSCoords(coords);
                setSCoordStr(
                    coords[0].toPrecision(8).toString() +
                    ", " +
                    coords[1].toPrecision(8).toString());
            },

        })

        return secondCoords === null ? null : (
            <Marker
                position={secondCoords}
                icon={iconB}>
            </Marker>
        )
    }

    const onButtonClicked = (e) => {
        //axios.post(url, )
    }


    return(
        <ThemeProvider theme={darkTheme}>
            <Paper elevation = {5}>
                <AppBar position="static" color="primary">
                    <Typography variant="h6" color="inherit" component="div">
                        PathFinder
                    </Typography>
                </AppBar>

                <MapContainer
                    center={center}
                    zoom={13}
                    scrollWheelZoom={true}
                >
                    <GeoLayer />

                    <LocationMarkerA />
                    <LocationMarkerB />

                </MapContainer>


                <Card>
                    <Typography variant="h7" component="h5">
                        LMB on map to pick first coordinate. RMB on map to pick second coordinate.
                    </Typography>

                </Card>

                <Grid
                    container
                    direction='column'
                    rowSpacing = {0.4}
                    justifyContent='space-evenly'
                    alignItems='center'
                >
                    <Grid item xs={8}>
                        <Typography variant="h4" component="h2">
                            Inputed coordinates
                        </Typography>
                    </Grid>
                    <Grid item xs={8}>
                        <TextField
                            variant="filled"
                            value={firstCoordStr}
                            label="First coordinate"
                            onChange={(e) => {
                                setFCoordStr(e.target.value);
                            }}
                            onKeyPress={(e) => {
                                if (e.key === 'Enter') {
                                    let str = e.target.value;
                                    setFCoords(str.split(',').map(function(item) {
                                        return parseFloat(item);
                                    }))
                                }
                            }}
                        />
                    </Grid>
                    <Grid item xs={8}>
                        <TextField
                            variant="filled"
                            value={secondCoordStr}
                            label="Second coordinate"
                            onChange={(e) => {
                                setSCoordStr(e.target.value);
                            }}
                            onKeyPress={(e) => {
                                if (e.key === 'Enter') {
                                    let str = e.target.value;
                                    setSCoords(str.split(',').map(function(item) {
                                        return parseFloat(item);
                                    }))
                                }
                            }}
                        />
                    </Grid>
                    <Grid item xs={8}>
                        <Button
                            variant="contained"
                            onClick={(e) => {
                                console.log("Button clicked")
                            }}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </ThemeProvider>

    );
};




export default App;
