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

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    stringEdges: string
    //EdgeArray: any[];
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    canvasReference: React.RefObject<HTMLCanvasElement>


    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    /**
     *  Saves and stores the background Image
     */
    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            const newState = {
                backgroundImage: background
            };
            this.setState(newState);
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    /**
     *  Draws edges and dots of the "Connect the Dots! puzzle"
     */
    redraw = () => {
        if(this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        ctx.clearRect(0, 0, this.props.width, this.props.height);
        // Once the image is done loading, it'll be saved inside our state.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }

        let edgeArray: string[][] = this.onParseEdges();


        for (let edge of edgeArray) {

            // console.log('edge:' + edge.toString());
            // console.log('x1: ' + edge[0]);
            // console.log('y1: ' + parseInt(edge[0][1]));
            // console.log('x2: ' + parseInt(edge[0][2]));
            // console.log('y2: ' + parseInt(edge[0][3]));
            // console.log('color' + edge[2]);

            ctx.beginPath();
            ctx.moveTo((this.props.width * (parseInt(edge[0]) + 1)) / (this.props.size + 1),
            (this.props.height * (parseInt(edge[1]) + 1)) / (this.props.size + 1));
            ctx.lineTo((this.props.width * (parseInt(edge[2]) + 1)) /
            (this.props.size + 1), (this.props.height * (parseInt(edge[3]) + 1)) / (this.props.size + 1));
            ctx.strokeStyle = edge[4];
            ctx.lineWidth = 3;
            ctx.stroke();
        }

    };

    /**
        parses the edges from a string into individual lines with color
     */
    onParseEdges = () => {
        let input: string = this.props.stringEdges;
        let newEdgeArray = [];
        let lineNumber: number = 0
        let noEdges: boolean = false;
        // splits into individual edge
        let lineArray: string[] = input.split('\n');
        for (let line of lineArray) {
            lineNumber = lineNumber + 1;
            if (line.length === 0)
                continue;
            // split into coordinate points and color
            let args: string[] = line.split(" ");
            if ((args.length !== 3) || !(args[0].includes(",")) || !(args[1].includes(","))) {
                alert('There was an error with some of you line input. \n ' +
                    'For reference, the correct form for each line is: x1,y1 x2,y2 color \n \n' +
                    'Line ' + line + ' Extra portion of the line, or extra space');
                break;
            }
            else {
                let stringCoord1: string[] = args[0].split(",");

                // console.log('coordinate 1: ' + intCoord1);
                let stringCoord2: string[] = args[1].split(",");


                let coords: string[] = stringCoord1.concat(stringCoord2);


                // checks to see if there is valid input
                for (let coord of coords) {
                    let intCord: number = parseInt(coord);
                    if((isNaN(intCord))) {
                        alert('There was an error with some of you line input. \n ' +
                            'For reference, the correct form for each line is: x1,y1 x2,y2 color \n \n' +
                            'Coordinate ' + parseInt(coord) + 'on line ' + lineNumber + ' Extra portion of the line, or extra space');
                        noEdges = true;
                        break;
                    }
                    if (intCord >= this.props.size) {
                        alert('Cannot draw edges, grid must be at least size ' + (parseInt(coord) + 1) + '.');
                        noEdges = true;
                        break;
                    }
                    if (intCord < 0) {
                        alert('Line ' +  lineNumber + ': Coordinates cannot contain negative numbers');
                        noEdges = true;
                        break;
                    }
                }

                //console.log('color1: ' + args[2]);
                // creates all aspects for edges
                let x1: string = stringCoord1[0];
                let y1: string = stringCoord1[1];
                let x2: string = stringCoord2[0];
                let y2: string = stringCoord2[1];
                let color: string = args[2];
                //let newEdge = {coord1: intCoord1, coord2: intCoord2, colour: args[2]};
                // pushes new edge to list of edges
                let newEdge: string[] = []
                newEdge.push(x1);
                newEdge.push(y1);
                newEdge.push(x2);
                newEdge.push(y2);
                newEdge.push(color);

                newEdgeArray.push(newEdge);
            }
        }
        // deletes all edges in list if there is invalid input
        if(noEdges){
            return [];
        }
        //console.log('In grid size: ' + newEdgeArray[0].color);
        return newEdgeArray;
    }




    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        // A hardcoded 4x4 grid. Probably not going to work when we change the grid size...

        let newCoordinates: [number, number][] = [];

        //console.log("hello" + this.props.size);
        for(let i = 1; i <= this.props.size; i++){
            for(let j = 1; j <= this.props.size; j++){

                newCoordinates.push([((this.props.width * i) / (this.props.size + 1)), ((this.props.height  * j) / (this.props.size + 1))])
            }
        }
        //console.log(newCoordinates);
        return newCoordinates;

    };

    // You could write CanvasRenderingContext2D as the type for ctx, if you wanted.
    drawCircle = (ctx: any , coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };


    /**
     *  renders html text for client to view
     */
    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size} </p>
            </div>
        );
    }
}

export default Grid;
