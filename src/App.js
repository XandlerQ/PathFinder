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

import React, {useState, useRef, useEffect, Component} from "react"
import {
    TextField,
    Button,
    Typography,
    Paper,
    Card,
    AppBar,
    Grid,
    ThemeProvider,
    createTheme,
    Slider, Tooltip
} from "@mui/material"
import axios from "axios"
import markerA from './marker-a.svg'
import markerB from './marker-b.svg'
import '@fontsource/roboto/300.css'
import './App.css'
import 'leaflet/dist/leaflet.css'
import SRTM from './viz.SRTMGL3_color-relief.tif'
import USGS from './viz.USGS30m_color-relief.tif'
import NASA from './viz.NASADEM_color-relief.tif'
import DEM from './viz.SRTMGL3_hillshade.tif'


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

const renderer = L.LeafletGeotiff.rgb();

const options = {
    transpValue: 255,
    useWorker: true,
    renderer: renderer,
};

//const raster = fromFile(NASA);


const layer = L.leafletGeotiff(SRTM, options);






const App = (props) => {
    const [firstCoordStr, setFCoordStr] = useState("");
    const [secondCoordStr, setSCoordStr] = useState("");
    let [firstCoords, setFCoords] = useState(null);
    let [secondCoords, setSCoords] = useState(null);
    let [areaArr,setAreaArr] = useState(null);
    let [prevPath, setPrevPath] = useState(null);
    let[path, setPath] = useState(null);
    let[mPath, setMPath] = useState(null);
    let[hLattitude, setHLat] = useState(null);
    let[hLongitude, setHLng] = useState(null);
    let[minTanSl, setMinTanSl] = useState(-0.7);
    let[maxTanSl, setMaxTanSl] = useState(0.7);
    let[fricSl, setFricSl] = useState(0.02);
    let[pathIdx, setPathIdx] = useState(0);

    function SetHeightOnChange() {

         const map = useMap();
         const mapContainer = map.getContainer();
         if (map != null) {
             map.invalidateSize();
         }

         return null;
    }

    const handleSlider1Change = (event, newValue) => {
        setMinTanSl(newValue);
    };

    const handleSlider2Change = (event, newValue) => {
        setMaxTanSl(newValue);
    };

    const handleSlider3Change = (event, newValue) => {
        setFricSl(newValue);
    };

    function GeoLayer() {

        var osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap contributors</a>'
        });

        var Stamen_Terrain = L.tileLayer('https://stamen-tiles-{s}.a.ssl.fastly.net/terrain/{z}/{x}/{y}{r}.{ext}', {
            attribution: 'Map tiles by <a href="http://stamen.com">Stamen Design</a>, <a href="http://creativecommons.org/licenses/by/3.0">CC BY 3.0</a> &mdash; Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            subdomains: 'abcd',
            minZoom: 0,
            maxZoom: 18,
            ext: 'png'
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

            L.control.layers(baseMaps, overlayMaps).addTo(map);


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

                console.log(layer.getValueAtLatLng(e.latlng.lat, e.latlng.lng) * 11.5294);

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

    function Path() {
        const map = useMap();


        if(path === null) {
             return null;
        }

        if(path === prevPath) {
            return null;
        }

        console.log("path");

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

        //setMPath(mxPath);
        let tech = path;

        setPrevPath(tech);




        let colorOptions;
        let idx = pathIdx;

        switch (idx % 5) {
            case 0: {
                colorOptions = redOptions;
                break;
            }
            case 1: {
                colorOptions = greenOptions;
                break;
            }
            case 2: {
                colorOptions = purpleOptions;
                break;
            }
            case 3: {
                colorOptions = blueOptions;
                break;
            }
            case 4: {
                colorOptions = orangeOptions;
                break;
            }
            default: {
                colorOptions = null;
            }
        }
        if (idx >= 5) {
            for(let i in map._layers) {
                if(map._layers[i]._path !== undefined) {
                        try {
                            map.removeLayer(map._layers[i]);
                            break;
                        }
                        catch(e) {
                            console.log("problem with " + e + map._layers[i]);
                            break;
                        }
                }
            }
        }
        setPathIdx(idx + 1);



        let polyline = L.polyline(mxPath, colorOptions);
        polyline.addTo(map);
        var PathLayer = {
            "Path": polyline
        };
        L.control.layers(null, PathLayer).addTo(map);
        setPath(null);


        return null;
    }

    const onButtonSubmitClicked = () => {
        let absCoordsDiffA = 2 * Math.abs(firstCoords[0] - secondCoords[0]);
        let absCoordsDiffB = Math.abs(firstCoords[1] - secondCoords[1]);

        let chebMetricTmp = Math.max(absCoordsDiffA, absCoordsDiffB) * 1.1;

        let sqCenter = [];
        sqCenter.push((firstCoords[0] + secondCoords[0]) / 2, (firstCoords[1] + secondCoords[1]) / 2);

        let areaArrTmp = [
            [sqCenter[0] - (chebMetricTmp / 4), sqCenter[1] - (chebMetricTmp / 2)],
            [sqCenter[0] + (chebMetricTmp / 4), sqCenter[1] + (chebMetricTmp / 2)]
        ];

        setAreaArr(areaArrTmp);


        let mx = new Array(400);

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

        for (let i = 399; i >= 0; i--) {
            let lat = areaArrTmp[0][0] + i * hLat;
            //console.log(lat);
            for (let j = 0; j < 400; j++) {
                let lng = areaArrTmp[0][1] + j * hLng;
                //console.log(lng);
                let eleVal = layer.getValueAtLatLng(lat, lng) * 11.5294;
                mx[399 - i][j] = eleVal;

                //console.log(layer.getValueAtLatLng(lat,lng));

                let absCoordsDiffHlat = 2 * Math.abs(firstCoords[0] - lat);
                let absCoordsDiffHlng = Math.abs(firstCoords[1] - lng);

                let absCoordsDiffTlat = 2 * Math.abs(secondCoords[0] - lat);
                let absCoordsDiffTlng = Math.abs(secondCoords[1] - lng);

                let chebMetricH = Math.max(absCoordsDiffHlat, absCoordsDiffHlng);
                let chebMetricT = Math.max(absCoordsDiffTlat, absCoordsDiffTlng);

                if (chebMetricH < distH) {
                    distH = chebMetricH;
                    yH = 399 - i;
                    xH = j;
                }

                if (chebMetricT < distT) {
                    distT = chebMetricT;
                    yT = 399 - i;
                    xT = j;
                }

            }
        }

        const postObj = JSON.stringify({
            n: 400,
            m: 400,
            x: chebMetricTmp * 111139,
            y: (chebMetricTmp / 2) * 111139,
            minTan: minTanSl,
            maxTan: maxTanSl,
            friction: fricSl,
            wElevation: 27 * 11.5294,
            xH: xH,
            yH: yH,
            xT: xT,
            yT: yT,
            mx: mx
        });

            axios.post("http://localhost:8080/PathFinder/data", postObj)
                .then(function (response) {
                    console.log(response);

                    const respPath = response.data.path.map(str => {
                        return [parseInt(str[0]), parseInt(str[1])];
                    });
                    setPath(respPath);


                })
                .catch(function (error) {
                    console.log(error);
                });
    }

    const blackOptions = { color: 'black' }
    const redOptions = { color: 'red' }
    const greenOptions = { color: 'green'}
    const purpleOptions = { color: 'purple'}
    const blueOptions = { color: 'blue'}
    const orangeOptions = {color: 'orange'}

    return(
        <ThemeProvider theme={darkTheme}>
            <Paper elevation = {6}>
                <Grid container

                      direction='column'
                      rowSpacing={0}
                      columnSpacing={0}
                      justifyContent='center'
                      alignItems='center'
                >
                    <Grid
                        sx={{ p: 1 }}
                        container
                        direction='column'
                        rowSpacing={0}
                        columnSpacing={0}
                        justifyContent='center'
                        alignItems='center'
                    >
                        <Grid item xs='auto' >
                            <AppBar position="static" color="primary">
                                <Typography variant="h6" color="inherit" component="div">
                                    PathFinder
                                </Typography>
                            </AppBar>
                        </Grid>

                        <Grid item xs={8}>
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
                                <SetHeightOnChange />
                            </MapContainer>
                        </Grid>
                        <Grid item xs='auto'>
                            <Card>
                                <Typography variant="h7" component="h5">
                                    LMB on map to pick first coordinate. RMB on map to pick second coordinate.
                                </Typography>

                            </Card>
                        </Grid>

                    </Grid>
                    <Grid container
                          sx={{ p: 1 }}
                          direction='row'
                          rowSpacing={0}
                          columnSpacing={4}
                          justifyContent='center'
                          alignItems='center'
                    >
                        <Grid item xs={'auto'}>
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
                        <Grid item xs='auto'>
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
                    </Grid>
                    <Grid container
                          sx={{ p: 1 }}
                          direction='row'
                          rowSpacing={3}
                          columnSpacing={4}
                          justifyContent='center'
                          alignItems='center'
                    >
                        <Grid item xs={3}>
                            <Paper elevation={1} >
                                <Tooltip title="Minimal tangent of path slope angle" >
                                    <Typography variant="h6" component="h3">
                                        Minimal path slope
                                    </Typography>
                                </Tooltip>
                                <Slider
                                    aria-label="Minimal path slope"
                                    defaultValue={-0.7}
                                    //getAriaValueText={sliderValue}
                                    valueLabelDisplay="auto"
                                    step={0.1}
                                    marks
                                    min={-2}
                                    max={0}
                                    onChange={handleSlider1Change}
                                />
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper elevation={1} >
                                <Tooltip title="Maximal tangent of path slope angle" >
                                    <Typography variant="h6" component="h3">
                                        Maximal path slope
                                    </Typography>
                                </Tooltip>
                                <Slider
                                    aria-label="Maximal path slope"
                                    defaultValue={0.7}
                                    valueLabelDisplay="auto"
                                    step={0.1}
                                    marks
                                    min={0}
                                    max={2}
                                    onChange={handleSlider2Change}
                                />
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper elevation={1} >
                                <Tooltip title="Friction coefficient determines an extent of path steepness: the lower the coefficient the gentlier slope will be calculated" >
                                    <Typography variant="h6" component="h3">
                                        Friction coefficient
                                    </Typography>
                                </Tooltip>
                                <Slider
                                    aria-label="Friction coefficient"
                                    defaultValue={0.02}
                                    valueLabelDisplay="auto"
                                    step={0.0001}
                                    min={0}
                                    max={0.1}
                                    onChange={handleSlider3Change}
                                />
                            </Paper>

                        </Grid>
                    </Grid>
                    <Grid container
                          sx={{ p: 1 }}
                          direction='row'
                          rowSpacing={3}
                          columnSpacing={4}
                          justifyContent='center'
                          alignItems='center'
                    >
                        <Grid item>
                            <Button
                                variant="contained"
                                onClick={ onButtonSubmitClicked }
                            >
                                Submit
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>


            </Paper>
        </ThemeProvider>

    );
};




export default App;
