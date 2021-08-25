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

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

interface GridSizePickerProps {
    value: string;                    // text to display in the text area
    onChange(newSize: number): void;  // called when a new size is picked
}

interface GridSizePicker1 {
    realGridSizeValue: string;
}

class GridSizePicker extends Component<GridSizePickerProps, GridSizePicker1> {

    constructor(props: any){
        super(props);
        this.state = {realGridSizeValue: this.props.value}
    }

    /**
     *  checks to see if size is within given range
     */
    onInputChange = (event: any) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below.
        //
        // We wrote "any" here because the type of this object is long and complex.
        // If you're curious, the exact type would be: React.ChangeEvent<HTMLInputElement>
        //
        // TODO - Not currently doing any validation or error handling. Should probably add some...
        let newSize: number = parseInt(event.target.value);
        if (newSize > 100 || newSize < 0) {
            alert('the size must be between 0 and 100 (inclusive)');
        } else{
            this.setState({
                realGridSizeValue: event.target.value
            })
        }
        if(isNaN(newSize)){
            newSize = 0;
        }
        this.props.onChange(newSize); // Tell our parent component about the new size.

    };

    /**
     *  renders html text for client to view
     */
    render() {
        return (
            <div id="grid-size-picker">
                <label>
                    Grid Size:
                    <input
                        value={this.state.realGridSizeValue}
                        onChange={this.onInputChange}
                        type="number"
                        min={1}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
