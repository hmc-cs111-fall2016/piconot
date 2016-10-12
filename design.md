# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?
Our target is the general public.  No programming knowledge would be required; thus, it is 
a syntax readable by anyone who wants to play with Picobot.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?
We wanted to make the syntax more readable than the original syntax.  We did this by
exchanging numbers for strings when describing state. We also modularized the rules more
clearly by creating separate objects for each state.

Furthermore, we wanted to simplify the program by limiting four possible states
corresponding to the possible directions the Picobot can move in. By doing this,
it is more clear to reader and user when we would change direction and states.
As opposed to having states anywhere from 1 - 99, and having no easy understanding
what a given numerical state represents in terms of directional movements, we 
can more clearly organize what should happen at any given move when reading the
code. 


## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

Because we do away with arbitrary numerical state and replace it with four 
directions, it becomes easier to reason with what the next step should 
be instead of figuring out which state we should move on to. On a related note,
because there are a set number of states, there is no need to memorize all 
previous defined states and worry about duplicating behaviors and running into 
errors. 


## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

The tradeoff for readability is that it's much more verbose. For rules within the same
state, in the current Picobot syntax, it'd be `0 X*** -> N 0`, whereas ours would require
something like: 
```
North = {
	if (Nothing[N]) Go North	
}
```

It also may be more difficult to express very specific or complicated manuevers.
Given that we only have four states, there can result in situations where 
too many rules hinders us from achieving an exact sequence because of having to
specifically define precedence within a group of rules for a given direction.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?
7 
We do away with custom, numerical state and replace it with directions. 
We feel that the syntax that we use now also completely changes the way a user
would think about implementing Picobot solutions.

## Is there anything you would improve about your design?
