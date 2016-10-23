# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

Our syntax is trees, named states, and naming walls and blanks rather than 
using NEWS.  We also wanted to be able to specify the starting state rather
than having it depend on the position of the rules within the List.

 * We were able to keep named states, since this was already implemented in the
provided API.
 * We kept the description of walls and blanks (i.e. `"NE! S_"` to describe 
 walls to the North and East, a blank to the South, and anything to the West.
 One compromise here was that the user needed to make this a string, because
 there are too many permutations of expressing a surrounding in this way for
 us to make objects represent surroundings.
 * We gave up on having a tree structure for states (in which rules of each
  state are nested together within the same block) for several reasons.
  Had we kept the tree structure as an internal DSL, it's implementation would 
  have looked something like the following:
    ```
    List(
      State1 {
        rule1
        rule2
      },
      State2 {
        rule1
        rule2
      }
    )
    ```
   * When we are translating this code into the given API, we would need to
    know the variable name of each state since the API takes in a string for 
    the state name. We would have needed a unique identifier for each state.
   * But if we address this issue by using strings as states. So instead of 
   `State1` we have `"State1"`, this would be too many implicit conversions 
   since we definitely need one to convert the string describing the 
   surroundings to a some `Surroundings` object in order to use functions to parse the rest of the rule.
   * We also just let the first rule specify the starting state. We did this to
   match the given API.
   * We used the APIs specification for directions (e.g. `East` and `StayHere`)
   rather than "E" and omitting the direction for `StayHere`. This was so `->`
   can have take one argument that's a tuple of fixed size.
   * There is quite a bit of boilerplate code in terms of import and setting
   up the room. For example, the implicit conversion absolutely needed 
   the `import State._` line in the same file as the code for it to work.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change 
your syntax?**

We give it a 7. The tree structure and the description of walls and blanks are 
the two major components of our syntax. We lost the tree structure. But managed
to keep the description of walls and blanks.

We also needed to add parentheses and keywords to connect different pieces of a
rule (i.e. `and`) as well as to specify precedence.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to 
map your syntax to the provided API?**

We give the difficulty an 8. Much of this was due to struggling with how Scala
decides whether or not to implicitly convert. Translating a string that 
describes the surroundings to the NEWS format in the API was not too bad but
it was cumbersome.

Our syntax allows the user to input an inconsistent surrounding (e.g. `E! E_`, 
there's a wall to the east and east is also blank). So we struggled a bit with
how to catch and relay this error to the user. We ended up with unspecified
behavior where it will prioritize the wall and disregard the blank, since we
added walls to the environment first in an if/else block.

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
