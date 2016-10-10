# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

Anna and I are designing this language for users who have had the equivalent of CS51/CS5/AP CS. We are assuming some knowledge of an object oriented language.




## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

First, we will explain the current design. Then, we will explain our observations about what we like and dislike about the current design, and what we'd like to change.

*Explanation*

Picobot moves according to a set of rules of the form: ' StateNow   Surroundings   ->   MoveDirection   NewState '.

For example, ' 0   xxxS   ->   N   0 ' is a rule that says "if picobot starts in state 0 and sees the surroundings xxxS, it should move North and stay in state 0."

The MoveDirection can be N, E, W, S, or X. N, E, W, S represent the direction to move, and X represents the choice not to move. If picobot's only rule was  0   xxxS   ->   N   0  and if picobot started in state 0 at the bottom of an empty room, it would move N one square and stay in state 0. However, picobot would not move any further, because its surroundings would have changed to xxxx, which does not match the rule above.

Surroundings is described with x, N, E, W, S, and also " * ". An asterisk means that it does not matter whether there is a wall in that position. For example, ' **Wx ' means that it does not matter if there is a wall in N or E, but there must be a wall to the West, and there must not be a wall to the South. Thus, the example: ' 0   x***   ->   N   0 ' means that if picobot starts in state 0 and sees any surroundings without a wall to the North, it should move North and stay in state 0 (regardless of whether there is a wall to the E, W, and S)."

What we like about this design is that each command line is very organized, compact/compact, and lined up nicely. The syntax kind of reminded Sophia of SML because it looked like pattern matching. We also think that it's easy to parse this language, becasue every command line has exactly the same number of characters. 

We found the states and the use of X as a MoveDirection less immediately intuitive. We wanted to write a programming language that involved more of the control flow structures that we would see in java, such as "if" and "while". We also think that the use of the * is unnecessary -- we would rather represent ' NXW* ' as ' N,!E,W ' thereby replacing the * with just an unspecified letter. 

We went through several ideas: 
1. Our first idea is to have a series of while loops. For example:
⋅⋅* while (**x*) {W}
⋅⋅* while (**w*) {S}
⋅⋅* while (*EWX) {N}

While the first bullet point is true, go W. When it's not true, check if the second bullet point is true. If it is true, then go S, but if it is not true, check the third bullet point, and if it is true, go N until the condition is no longer met. 
However, we didn't like using **** as a representation for whether a block was occupied or not occupied.

2. Our second idea was to write:
⋅⋅* while (!W) {W}
⋅⋅* while (W) {S}
⋅⋅* while (E, W, !S) {N}
We liked this, but also wanted to move even further away from the existing language. We also wanted to take out { } if they are unnecesssary.

3. Our third idea was to move away from letters and use arrows. 
We could either do a combination of both:
⋅⋅*  while (N, E) v

Or just use arrows:
⋅⋅* while (^ <) v

Problem with the first option: We don't like how "North is occupied" is represented by N, and "move North" is represented by ^.
However, in the second option, it's not intuitive what the argument in the while loop means. Does it mean "while the block above and block to the left are both occupied", or does it mean "while the block above and block to the left are both free"? 

4. Our fourth idea was to do something like this:
⋅⋅* |N|:N* 
⋅⋅* |E|:S
⋅⋅* |W|:S*

We only describe when a block is free (or, until the block is not free). The first command means "if, in your current state, the block to the north is free, then continue to move north as many times as you'd like as long as this condition is still being satisfied." The second command means "if, in your current state, the block to the east is free, then move S once, and then move onto the next command." We are also thinking for there to only be one letter in the condition statement. This is because we only care about the block that is in the direction that you currently want to move; for example, in the first bullet point, if you want to move N continously, you might not care about what is to the W, S, or E of your current state, you only care to know whether the block to the N is free.

The problem with this implementation is that we are not able to jump back to a previous state; we just go in order of commands from top to bottom. This implementation also only allows us to represent conditions in which a block is free, not whether a block is occupied, although from our explanation above, we might not care to check if a block is occupied? 

However, to address the problem that we can move from one command to the next command but not from a command to a previous command, we implement the following: 
⋅⋅* |N|:N* +3
⋅⋅* |S|:S -1
⋅⋅* |E|:E* 

This means that once you finish executing |N|:N*, you move three steps ahead. However, do we want to specify +1? Or does the program automatically know to go to the next step if the current statement is no longer true?  

5. We also thought about some other options: 
⋅⋅* in this line: |N|:N* +3 , why do we want |N| to necessarily mean that it's free? What if we did (N) to mean the condition in which the block to the north is free, and [N] to mean the condition in which the block to the north is not free?
⋅⋅* do we even need the asterisk? If our goal is to check the statement to the left of the colon is true, and if so, execute the statement to the right of the colon, doesn't our check operate as a "while"???? 




## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?


We didn't really like the usage of the wildcard. Our idea is to say (

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

## Is there anything you would improve about your design?
