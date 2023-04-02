import {
    MapContainer,
    TileLayer,
    useMapEvents,
    Marker,
    useMap,
    LayersControl,
    LayerGroup,
    ImageOverlay,
    Rectangle, Polyline
} from 'react-leaflet'
import "leaflet";
import "leaflet-geotiff-2";
import "leaflet-geotiff-2/dist/leaflet-geotiff-rgb";
import "plotty"
import "leaflet-geotiff-2/dist/leaflet-geotiff-plotty"

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
    const [firstCoordStr, setFCoordStr] = useState("");
    const [secondCoordStr, setSCoordStr] = useState("");
    let [firstCoords, setFCoords] = useState(null);
    let [secondCoords, setSCoords] = useState(null);

    let [areaArr,setAreaArr] = useState(null);
    let[path, setPath] = useState(null);
    let[hLattitude, setHLat] = useState(null);
    let[hLongitude, setHLng] = useState(null);


    const renderer = L.LeafletGeotiff.rgb();

    const options = {
        transpValue: 255,
        useWorker: false,
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

    function Area() {

        return areaArr === null ? null : (
            <>
                <Rectangle bounds={areaArr} pathOptions={blackOptions} />
            </>
    )
    }


    function Path () {

        if(path === null) {
            return null;
        }
        let mxPath = new Array (path.length);


        for (let i = 0; i < mxPath.length; i++) {
            mxPath[i] = new Array(2);
        }

        for(let i = 0; i < path.length; i++) {
            let lat = areaArr[0][0] + (399 - path[i][0]) * hLattitude;
            let lng = areaArr[0][1] + (path[i][1]) * hLongitude;


            mxPath[i][0] = lat;
            mxPath[i][1] = lng;

        }
        


        return (
            <>
                <Polyline positions={mxPath} pathOptions={redOptions} />
            </>
        )
    }



    const onButtonSubmitClicked = () => {
        let absCoordsDiffA = 2 * Math.abs(firstCoords[0] - secondCoords[0]);
        let absCoordsDiffB = Math.abs(firstCoords[1] - secondCoords[1]);

        let chebMetricTmp = Math.max(absCoordsDiffA, absCoordsDiffB) * 1.2;

        let sqCenter = [];
        sqCenter.push((firstCoords[0] + secondCoords[0]) / 2, (firstCoords[1] + secondCoords[1]) / 2);

        let areaArrTmp = [
            [sqCenter[0] - (chebMetricTmp / 4), sqCenter[1] - (chebMetricTmp / 2)],
            [sqCenter[0] + (chebMetricTmp / 4), sqCenter[1] + (chebMetricTmp / 2)]
        ];

       setAreaArr (areaArrTmp) ;
        
        
        let mx = new Array (400);

        for (let i = 0; i < mx.length; i++) {
            mx[i] = new Array(400);
        }
        

        let xH = 0;
        let yH = 0;
        let xT = 0;
        let yT = 0;
        let distH = 2 * chebMetricTmp;
        let distT = 2 * chebMetricTmp;

        let hLat = chebMetricTmp / 800;
        let hLng = chebMetricTmp / 400;
        setHLat(hLat);
        setHLng(hLng);
        for(let i = 399; i >= 0; i--) {
            let lat = areaArrTmp[0][0] + i * hLat;
            for(let j = 0; j < 400; j++) {
                let lng = areaArrTmp[0][1] + j * hLng;

                mx[399 - i][j] = layer.getValueAtLatLng(lat,lng);

                let absCoordsDiffHlat = 2*Math.abs(firstCoords[0] - lat);
                let absCoordsDiffHlng = Math.abs(firstCoords[1] - lng);

                let absCoordsDiffTlat = 2*Math.abs(secondCoords[0] - lat);
                let absCoordsDiffTlng = Math.abs(secondCoords[1] - lng);

                let chebMetricH = Math.max(absCoordsDiffHlat, absCoordsDiffHlng);
                let chebMetricT = Math.max(absCoordsDiffTlat, absCoordsDiffTlng);

                if(chebMetricH < distH) {
                    distH = chebMetricH;
                    yH = 399 - i;
                    xH = j;
                }

                if(chebMetricT < distT) {
                    distT = chebMetricT;
                    yT = 399 - i;
                    xT = j;
                }

            }
        }

        const postObj = JSON.stringify({n: 400,
            m: 400,
            x:chebMetricTmp * 111139,
            y: (chebMetricTmp / 2) * 111139,
            minTan: -0.5,
            maxTan: 0.7,
            xH: xH,
            yH: yH,
            xT: xT,
            yT: yT,
            mx: mx
        });


        axios.post("http://26.213.110.200:8080/PathFinder/data", postObj)
            .then(function (response) {
                console.log(response);

                const respPath = response.data.path.map(str => {
                    return [parseInt(str[0]),parseInt(str[1])];
                });
                setPath(respPath);



            })
            .catch(function (error) {
                console.log(error);
            });
    }

    const blackOptions = { color: 'black' }
    const redOptions = { color: 'red' }

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
                    <Area />
                    <Path />
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
                    <Grid item xs={8} >
                        <Button
                            variant="contained"
                            onClick={ onButtonSubmitClicked }
                        >
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </ThemeProvider>

    );
};




export default App;
