# Evaluation: running commentary

## Internal DSL

# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._
We had to change the while-loops to `WhileInner` and `WhileOuter` because the word
`while` is a keyword in Scala. Therefore, we can have `While` as a method that we define,
but we could not try to override `while`. We needed the two types of while loops because it
was easier to allow for (two level only) nesting.

Also, in the `WhileOuter` loops, we need to put all the things we want to do (`WhileInner`
and `move`) into a List because `WhileOuter` needs to take a list of rules so that it can do
multiple things if necessary. We cannot take a bunch of rules, which is what the `WhileInner`
and `move` compiles into because Scala cannot take that as one argument. 

Additionally, we had to include parentheses around our directions in `free` and `move`.
Because they are methods called on one thing, we had to include parentheses. We could
remove these if these acted on an object, but that would also change our syntax.



**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
7. Our language ideally had 3 words, not including the cardinal directions (move, free, and 
while). With this, users must differentiate between inner while loops and outer while loops. 
Additionally, users must be able to make a List, which is completely unrelated to the
intended use of writing code for picobot. This makes it more and more difficult for users to
use.


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
10. Once we realized that we had to make a method for the While loop, we started running into even more problems around how to give our While loop Rules. We realized that we have to make an internal representation to get to Prof Ben’s internal representation, which can then be run by the PicoBot API.

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._






**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**







**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**






