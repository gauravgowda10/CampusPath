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

package marvel;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser {
    // this class does not represent and ADT, so
    // it does not have an abstraction function and Representation Invariant
    /**
     * Reads the Marvel Universe dataset. Each line of the input file contains a character name and a
     * comic book the character appeared in, separated by a tab character
     *
     * @param filename the file that will be read
     * @spec.requires filename is a valid file in the resources/data folder.
     * @return an iterator linked to the data in the given filename
     */
    // TODO: Replace 'void' with the type you want the parser to produce
    public static Iterator<MarvelParserModel> parseData(String filename) throws IllegalArgumentException{
        // You can use this code as an example for getting a file from the resources folder
        // in a project like this. If you access TSV files elsewhere in your code, you'll need
        // to use similar code. If you use this code elsewhere, don't forget:
        //   - Replace 'MarvelParser' in `MarvelParser.class' with the name of the class you write this in
        //   - If the class is in src/main, it'll get resources from src/main/resources
        //   - If the class is in src/test, it'll get resources from src/test/resources
        //   - The "/" at the beginning of the path is important
        // Note: Most students won't re-write this code anywhere, this explanation is just for completeness.
        InputStream stream = MarvelParser.class.getResourceAsStream("/data/" + filename);
        if(stream == null) {
            // stream is null if the file doesn't exist.
            // You'll probably want to handle this case so you don't try to call
            // getPath and have a null pointer exception.
            // Technically, you'd be allowed to just have the NPE because of
            // the @spec.requires, but it's good to program defensively. :)
            System.err.println("File not found: " + filename);
            throw new IllegalArgumentException("provided an invalid file name");
        }
        Reader reader = new BufferedReader(new InputStreamReader(stream));


        Iterator<MarvelParserModel> csvUserIterator =
                new CsvToBeanBuilder<MarvelParserModel>(reader)
                .withType(MarvelParserModel.class)
                .withSeparator('\t')
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .iterator();
        return csvUserIterator;





        // TODO: iterator -> Data Structure
        // TODO: Complete this method
        // Hint: You might want to create a new bean class to use with the OpenCSV Parser

        // return DS
    }
}