
import React, {Component} from 'react';
//import {start} from "repl";

/**
 * Building Props
 */
interface BuildingProps {
    onStart(edges: string): void,
    onEnd(edges: string): void

}

/**
 * Building State
 */
interface BuildingState {
    startBuilding: string;
    endBuilding: string;

}

/**
 * A Textfield that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class Building extends Component<BuildingProps, BuildingState> {
    constructor(props: any) {
        super(props);
        this.state = {startBuilding: "Enter Start Building",
            endBuilding: "Enter Destination Building"}
    }

    /**
     * Allows client to type in edges to the textbox
     * @param event
     */
    onInputChangeStart = (event: any) => {
        this.setState({
            startBuilding: event.target.value
        })
        //this.props.onStart(this.state.startBuilding)
    }

    /**
     * Allows client to type in edges to the textbox
     * @param event
     */
    onInputChangeEnd = (event: any) => {
        this.setState({
            endBuilding: event.target.value
        })
        //this.props.onEnd(this.state.endBuilding)
    }

    /**
     * returns string to app
     * @param event when the draw button is pressed
     */
    returnString = () => {
        this.props.onStart(this.state.startBuilding)
        this.props.onEnd(this.state.endBuilding)
    }


    /**
     * Clears all edges
     * @param event when the clear button is pressed
     */
    clearTextBox = () => {
        this.setState({
            startBuilding: "Enter Start Building",
            endBuilding: "Enter Destination Building",
        })
        this.props.onStart("")
        this.props.onEnd("")
    }




    /**
     *  renders html text for client to view
     */
    render() {
        return (
            <div id="edge-list">
                Find the shortest path between buildings (make sure to enter the 'short name'):  <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange = {this.onInputChangeStart}
                    value = {this.state.startBuilding}
                />
                <textarea
                    rows={5}
                    cols={30}
                    onChange = {this.onInputChangeEnd}
                    value = {this.state.endBuilding}
                /> <br/>
                <button onClick={this.returnString}>Draw Shortest Path</button>
                <button onClick={this.clearTextBox}>Clear Path</button>
            </div>
        );
    }
}

export default Building;