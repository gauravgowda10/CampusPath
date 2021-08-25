/*
 * Copyright (C) 2020 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import "./Map.css";

/**
 * state for Map
 */
interface MapState {
    backgroundImage: HTMLImageElement | null;
    importantData: any
    start: string
    end: string
}

/**
 * props for Map
 */
interface MapProps {
    start: string
    end: string;

}

class Map extends Component<MapProps, MapState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    /**
     * constructs initial values for state
     * @param props
     */
    constructor(props: any) {
        super(props);
        this.state = {
            backgroundImage: null,
            importantData: null,
            start: this.props.start,
            end: this.props.end,
        };
        this.canvas = React.createRef();
    }

    /**
     * checks to see if component mounted properly
     */
    componentDidMount() {

        this.fetchAndSaveImage();
        this.redraw()
    }

    /**
     * checks to see if component updated properly
     */
    componentDidUpdate() {
        console.log('second')
        this.redraw()
    }

    /**
     * saves image and displays it on page
     */
    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    /**
     *  Draws edges and dots of the "Connect the Dots! puzzle"
     */
    redraw = () => {
        console.log("right here");
        this.drawBackgroundImageAndPath();
       // this.drawPath();
    }

    /**
     * draw background and fastest path from start to end building
     */
    async drawBackgroundImageAndPath() {
        console.log('Here');
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) {   // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        let lines: number[][] | undefined = await this.sendRequest()

        if (lines !== undefined) {
            for (let edge of lines) {
                ctx.beginPath();
                ctx.moveTo(edge[0], edge[1]);
                ctx.lineTo(edge[2], edge[3]);
                ctx.strokeStyle = "red";
                ctx.lineWidth = 10;
                ctx.stroke();
            }
        }
    }

    /**
     * requests the server for information on shortest Path
     */
    async sendRequest() {
        //console.log(this.props.start)
        //console.log(this.props.end)
        try {
            let repsonsePromise = fetch("http://localhost:4567/shortestBuildingPath?start=" +
                encodeURIComponent(this.props.start) + "&end=" + encodeURIComponent(this.props.end));
            let response = await repsonsePromise
            let parsingPromise = response.json();
            let parsedObject = await parsingPromise;
            //console.log(parsedObject);
            if (this.props.start === this.state.start && this.props.end === this.state.end) {
                this.setState({
                    importantData: parsedObject
                })
            }
            let lines:  number[][] = []
            for(let item in parsedObject["path"]){
                let point: number[] = []
                let x1: number = parsedObject["path"][item]["start"]["x"]
                let y1: number = parsedObject["path"][item]["start"]["y"]
                point.push(x1);
                point.push(y1);
                let x2: number = parsedObject["path"][item]["end"]["x"]
                let y2: number = parsedObject["path"][item]["end"]["y"]
                point.push(x2);
                point.push(y2);
                lines.push(point)
                console.log(point);
            }
            return lines;
        } catch{
            alert("Make sure to use the short building notation when trying to find shortest path!")
        }


    }

    /**
     * renders web page for client
     */
    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default Map;