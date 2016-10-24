# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

We are making this design for programmers more comfortable with imperative programming (i.e. while loops).

We assume that the users understand while-loops and some level of imperative programming. It would be helpful to understand the cardinal directions (NEWS) and boolean `&&`. 


## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

We want to express picobot in different types of programming. So instead of using states, we use imperative while-loops. This can allow users to program the same functionality using different methods and styles. Just like how we learned Scala by re-implementing what we know, users can learn imperative AND state-based programming by using Dodds' syntax and our syntax.


## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It is easier to express repeated actions without using states. It is also easier to express things in a chronological fashion (whereas states can be arbitrary).


## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

It might be more difficult to express actions that require many state changes that aren't in a predictable, chronological order. Mutual recursion might be hard.


## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

8. We got rid of states, mandated NEWS order, and `*`, while adding while-loops and a sense of chronological order.


## Is there anything you would improve about your design?

If we allowed functions, then mutual recursion might be easier to implement. Right now, we assume that one program represents one function.

