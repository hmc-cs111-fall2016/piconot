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
and `move` compiles into because Scala cannot take that as one argument. Note that `WhileInner` 
can only contain one rule (so that our code would work).

Additionally, we had to include parentheses around our directions in `free` and `move`.
Because they are methods called on one thing, we had to include parentheses. We could
remove these if these acted on an object, but that would also change our syntax. We had 
to use the words `East` and `West` instead of `E` and `W` as desired. We could have converted 
these shorthand directions, but we didn't have enough time.



**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
7. Our language ideally had 3 words, not including the cardinal directions (move, free, and 
while). With this, users must differentiate between inner while loops and outer while loops. 
Additionally, users must be able to make a List, which is completely unrelated to the
intended use of writing code for picobot. This makes it more and more difficult for users to
use.


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
10. Once we realized that we had to make a method for the While loop, we started running into 
even more problems around how to give our While loop Rules. We realized that we have to make 
an internal representation to get to Prof Ben’s internal representation, which can then be run 
by the PicoBot API.

NOTE: We couldn't do a right-hand program. First, we already used up our 4 hours. Second, 
I think our language design makes it really hard to do the right-hand program, especially since 
the sample solution is state-heavy and difficult to map out chronologically. If it were possible 
to use while-loops for the right-hand program, we would need to implement more features (like 
more nesting levels). "Ain't nobody got time for that" ~Tiff

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

We did not change much this time.
We added the ability to say `while (any)` to try to implement the RightHand Picobot so that, if we could, we would not have to restrict it 
to being chronological.

We did not implement a lot of nested while loops, specifically while loops with only one other while loop in it because we ran out of our 
time budget and it would have been rather difficult to find all the unmatched states and make filler states for them, as we did for the 
exit rule of the outermost while loop.

Although we did not have the time to implement RightHand, we realized that if we did have the time, we would be forced to implement `while 
(moving E)` which would go into the while loop if we had just moved east to get to our current position.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
2. We made some minor changes, but most of those would have been required to implement RightHand anyways and were not that major of a 
change from the ideal syntax (they at least had the structure of `while (cond) body`.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
6. We had to be able to create our own intermediate representation, which was a list of list of rules to be able to implement while loops 
as we wanted. This made it slightly difficult and made Empty take more time than it may have otherwise taken. However, this was much 
easier than implementing the internal DSL. Prof Ben was right.
