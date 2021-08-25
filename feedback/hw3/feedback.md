### Code quality score: 3/3

### Mechanics: 3/3

#### General Feedback
Minor code styling issue. Overall great job!

#### Specific Feedback

When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.
