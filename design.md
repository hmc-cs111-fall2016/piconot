# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

We assume some familiarity with programming, because we use a code block-like
syntax of rules (as a grouped-by-state structure).

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

Picobot has a lot of redundancy in that the states need to mentioned on a per
rule basis in the original syntax. We both would group these rules together
anyway when writing in the old syntax, so it seemed helpful to enforce this.

We also wanted to be able to name states rather than relying on the user to
comment what states mean.  It is also helpful when seeing a state transition,
rather than relying on the commenting of the author.

We were frustrated with having to interpret `*x**` or any surroundings with
blanks, since we need to map the position to one of the directions of NEWS.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It might be easier to express a surrounding, since people are likely to think
about a surrounding as where walls are and where walls aren't, rather than
explicitly choosing one of [Wall, Blank, Wildcard] for each direction.

Having named states removes a layer of indirection because a state can be named
directly as what it represents, rather than having to translate from a meaning
to a number to a state.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

It might be more difficult to avoid syntax errors.
 * Since states are now strings rather than numbers, it might be easier to
 misspell them.
 * It is also possible for the user to input inconsistent surroundings, since
 they specify the walls and blanks separately (e.g. `S! SW_` which would mean
 that there was a wall to the south, but also a blank to the south).

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

Our syntax is about 5 different from the original syntax.  The grouping
structure is a significant change in syntax, and the naming of states is a
minor improvement.

The most substantive change from the original syntax is how we represent a
direction, in that we do not use the order of NEWS, but rather specify walls
and blanks separately.

## Is there anything you would improve about your design?

We thought it would cool to let the user define some sort of subroutine, almost
like a function call which would allow for greater abstraction as the user
designs their algorithm.  For example, allowing the user to define a "sweep
subroutine" which would, when called, do a single vertical state. We were not
sure how we would implement stack frames, so we decided to just stick with
individual states.
