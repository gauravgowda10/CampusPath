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

interface EdgeListProps {
    onChange(edges: any): void;  // called when a new edge list is ready
    value: string;
    gridSize: number;
}

interface EdgeListText {
    editableText: string;
}

/**
 * A Textfield that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListText> {
    constructor(props: any) {
        super(props);
        this.state = {editableText: ""}
    }

    /**
     * Allows cleint to type in edges to the textbox
     * @param event
     */
    onInputChange = (event: any) => {
        this.setState({
            editableText: event.target.value
        })
    }

    /**
     * returns string to app
     * @param event when the draw button is pressed
     */
    returnString = (event: any) => {
        this.props.onChange(this.state.editableText);
    }


    /**
     * Clears all edges
     * @param event when the clear button is pressed
     */
    clearTextBox = (event: any) => {
        this.props.onChange("");
    }

    /**
     *  renders html text for client to view
     */
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange = {this.onInputChange}
                    value = {this.state.editableText}
                /> <br/>
                <button onClick={this.returnString}>Draw</button>
                <button onClick={this.clearTextBox}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
