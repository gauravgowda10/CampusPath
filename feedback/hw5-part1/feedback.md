### Design: 2/3

Consider naming your methods following known naming conventions. If you'd like
to insert a node/edge, include "add" in the method name. If you'd like to remove
a node/edge, include "remove" in the method name.

Why do the `contains` method throw an exception when the param is not in this,
when the purpose of the method is to figure out if the param is in this or not?

The design of your `getEdge` method is very confusing. Edges can have duplicate
labels as long as the dup labels have different parents/children.

When overriding `equals` or `hashCode`, you must override both methods.

Consider if your graph needs methods such as `toString`, `equals`, and
`hashCode`. For such a large data structure, these may be expensive operations.

### Documentation & Specification (including JavaDoc): 2/3

If `@spec.requires` does not say the node/edge must be in the graph, your
specifications should always specify behavior given a node/edge not in the
graph.

`this` can never be null.

`clearGraph`'s `@spec.effects` should use && instead of ||.

Note: an object can never `.equals(null)`. To check for null, use ==.

### Testing (test suite quality & implementation): 3/3

JUnit test classes should follow general style guidelines for spacing and good
names.

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Overall good. Make sure that your specs are generally free of error and that you
are designing methods that do not contradict it's own behavior.

#### More Details

None.
