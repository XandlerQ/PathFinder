import { YMaps, Map, Placemark,} from '@pbe/react-yandex-maps';
import React, { useState } from "react";
import {TextField, Button, Grid, Typography, Paper, Card, AppBar, Toolbar, ThemeProvider, createTheme} from "@mui/material";
import axios from "axios"
import markerA from './marker-a.png'
import markerB from './marker-b.png'
import bonfire1 from './bonfire1.png'
import bonfire2 from './bonfire2.png'
import '@fontsource/roboto/300.css'

const center = [55.76, 37.64];

const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    },
});


const App = (props) => {

    const [firstCoordStr, setFCoordStr] = useState("");
    const [secondCoordStr, setSCoordStr] = useState("");
    let [firstCoords, setFCoords] = useState();
    let [secondCoords, setSCoords] = useState();
    //let firstCoords = center;
    //let secondCoords = center;


    const onLeftClick = (e) => {
        const coords = (e.get('coords'));
        setFCoords(coords);
        setFCoordStr(
            coords[0].toPrecision(8).toString() +
            ", " +
            coords[1].toPrecision(8).toString());

    }

    const onRightClick = (e) => {
        const coords = (e.get('coords'));
       setSCoords(coords);
       setSCoordStr(
            coords[0].toPrecision(8).toString() +
            ", " +
            coords[1].toPrecision(8).toString());
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
            <YMaps
                query= {{
                    load: "package.full",
                    apikey: "61f58c55-d215-41a0-b26f-4ed5d754ad30"
            }}>
                <Map
                    state={{
                        center,
                        zoom: 9,
                        controls: []
                    }}
                    width="99.345vw"
                    height="80vh"
                    onClick={onLeftClick}
                    onContextMenu={onRightClick}
                >
                    <Placemark
                        geometry = {firstCoords}
                        options={{
                            iconLayout: "default#image",
                            iconImageSize: [50, 50],
                            iconImageHref: bonfire1
                        }}
                    />
                    <Placemark
                        geometry = {secondCoords}
                         options={{
                             iconLayout: "default#image",
                             iconImageSize: [50, 50],
                             iconImageHref: bonfire2
                         }}
                    />
                </Map>
            </YMaps>

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
