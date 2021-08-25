### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF when appropriate): 3/3

### Testing (test suite quality & implementation): 2/3

### Mechanics: 3/3

#### Overall Feedback

Really good design and code quality! Remember to cover edge cases in your test suite.

#### More Details

CampusMap.java:
Remember that Javadocs are inherited, so you don't need to repeat
documentation here. Specifically, in CampusMap, you don't need a top
level specification or method specifications for the four methods from
ModelAPI.

Path.java:
Type variables should be documented in the top level specification of the ADT.

Your test suite is lacking in coverage.  Here are a few ideas for interesting test
cases:

• Cyclic graphs with path finding.
• Paths where the difference between BFS and Dijkstra's algorithm is apparent.
• Graphs with multiple edges of varying costs between the same two nodes.