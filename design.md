# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

Our design targets users who are comfortable with cardinal directions and explaining
directions in a more colloquial way. We are attempting to make the state hidden
from a user so they can think more naturally.

The idea of labeling and jump to will be more familiar to users who have prior
experience with code, but we think it's a natural way to have control flow.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

The original syntax is not intuitive and is difficult to parse for the human brain.
Without the comments, it is difficult to figure out what all the symbols and states
mean. Although it's concise, the syntax is unnatural to anyone who hasn't done
programming before. Reasoning about states is also difficult, so we have hidden
this behind keywords.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

Specifying the Picobots current direction is easier in our languag. Our notion
of looping is simpler since we use human readable labels instead of state numbers
which in the original langugae lead to multiple places.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

Our wall descriptions are more abstract thus limiting our ability to specify
the surrounding walls.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

6.5: We removed the states, arrow syntax, `*` syntax and used more natural
langugae. Structurally, we also changed how one expresses their ideas. It's similar

## Is there anything you would improve about your design?

We would also like to have more specific positional conditionals like `until <loc>`
where `loc := NW-Corner | NE-Corner | SE-Corner | SW-Corner`.
