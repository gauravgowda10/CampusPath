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
import Map from "./Map"
import Building from "./Building";
//import {Simulate} from "react-dom/test-utils";
//import input = Simulate.input;

interface AppState{
    start: string;
    end: string
    buildingString: string
}

class App extends Component<{} , AppState> {

    /**
     * Sets initial values for the states
     * @param props
     */
    constructor(props: any) {
        super(props);
        this.state = {
            start:"",
            end:"",
            buildingString: "hello"
        }
        this.intializeBuildings();
    }

    /**
     * Sets the inputString for the start building as a state
     * @param inputString
     */
    passStart = (inputString: string) => {
        //console.log('input1: ' + inputString)
        this.setState({
            start: inputString,
        });
        //console.log('start1: ' + this.state.start)
    };

    /**
     * Sets the inputString for the end building as a state
     * @param inputString
     */
    passEnd = (inputString: string) => {
        //console.log('input2: ' + inputString)
        this.setState({
            end: inputString
        });
        //console.log('end2: ' + this.state.end)

    };

    /**
     *  Allows the user to see the list of buildings
     */
    async intializeBuildings() {

        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:4567/shortBuildingNames");
        xhr.onload = () => {
            if(xhr.responseText.length > 0) {
                let s: string = xhr.responseText
                s.replace(",", "\n")
                this.setState({
                    buildingString: s
                });
            }
            else{
                alert("Not able to connect to server")
            }
        }
        xhr.send(null);
        //console.log( "hello123" + xhr.statusText)

    }


    /**
     *  renders the webpage so the client can see it in browser
     */
    render() {
        // console.log(this.state.start)
        //console.log(this.state.buildingString)
        return (
            <div>
                List of Buildings: [Listed as ("Short Name":"Long Name")] <br/>
                <textarea readOnly
                    rows={52}
                    cols={100}
                    value = {this.state.buildingString}
                    //{"PAR:Parrington Hall
                    // \n MUS (S):Music Building (South Entrance)
                    // \nCHL:Chemistry Library (West Entrance)
                    // \nKNE (SW):Kane Hall (Southwest Entrance)
                    // \nUBS (Secret):University Bookstore (Secret Entrance)
                    // \nMCM (SW):McMahon Hall (Southwest Entrance)
                    // \nMLR:Miller Hall
                    // \nT65:Thai 65
                    // \nCHL (NE):Chemistry Library (Northeast Entrance)
                    // \nMGH (E):Mary Gates Hall (East Entrance)
                    // \nFSH:Fishery Sciences Building
                    // \nIMA:Intramural Activities Building
                    // \nEEB (S):Electrical Engineering Building (South Entrance)
                    // \nOUG:Odegaard Undergraduate Library
                    // \nEEB:Electrical Engineering Building (North Entrance)
                    // \nROB:Roberts Hall
                    // \nKNE (E):Kane Hall (East Entrance)
                    // \nMUS:Music Building (Northwest Entrance)
                    // \nCMU:Communications Building
                    // \nBGR:By George
                    // \nLOW:Loew Hall
                    // \nMNY (NW):Meany Hall (Northwest Entrance)
                    // \nSUZ:Suzzallo Library
                    // \nRAI (E):Raitt Hall (East Entrance)
                    // \nKNE (SE):Kane Hall (Southeast Entrance)
                    // \nMGH (S):Mary Gates Hall (South Entrance)
                    // \nKNE:Kane Hall (North Entrance)
                    // \nMGH (SW):Mary Gates Hall (Southwest Entrance)
                    // \nSAV:Savery Hall
                    // \nHUB (Food, W):Student Union Building (West Food Entrance)
                    // \nCSE:Paul G. Allen Center for Computer Science \u0026 Engineering
                    // \nHUB:Student Union Building (Main Entrance)
                    // \nHUB (Food, S):Student Union Building (South Food Entrance)
                    // \nMUS (SW):Music Building (Southwest Entrance)
                    // \nMNY:Meany Hall (Northeast Entrance)
                    // \nMUS (E):Music Building (East Entrance)
                    // \nCHL (SE):Chemistry Library (Southeast Entrance)
                    // \nGWN:Gowen Hall
                    // \nBAG (NE):Bagley Hall (Northeast Entrance)
                    // \nUBS:University Bookstore
                    // \nBAG:Bagley Hall (East Entrance)
                    // \nKNE (S):Kane Hall (South Entrance)
                    // \nDEN:Denny Hall
                    // \nMCC:McCarty Hall (Main Entrance)
                    // \nMGH:Mary Gates Hall (North Entrance)
                    // \nPAB:Physics/Astronomy Building
                    // \nMOR:Moore Hall
                    // \nPAA:Physics/Astronomy Building A
                    // \nMCM:McMahon Hall (Northwest Entrance)
                    // \nMCC (S):McCarty Hall (South Entrance)
                    // \nRAI:Raitt Hall (West Entrance)"}
                />  This program will calculate the shortest distance from one building to another on the UW campus.
                    Make sure to use the short building notation!!!!
                <Map start={this.state.start} end={this.state.end} />
                <Building onStart={this.passStart} onEnd={this.passEnd} />
            </div>

        );
    }

}

export default App;
