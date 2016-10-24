# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?
Our target audience is people who want to play with Picobot.  We were thinking
of what how we would like to interact with it, so people like us are included in
the idea of who we think might prefer this syntax. The brevity of our
syntax and grouping of state behavior together we feel makes it more intuitive
for new users and nicer for experienced users to type.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?
We chose this design for a few reasons. One is that it is redundant if there
are a lot of commands in the same state. As a result, we pulled out the information
about which state a command is in. Another thing is that although wildcards are nice,
they can sometimes be redundant. As a result, we are making wildcards implicit. As a
result, users can just specify whether or not picobot is blocked or not.

We changed the syntax for the commands as well. On the left-hand side of a command,
we use a prefix operator and the initials for the four cardinal directions for
succinctness. On the right-hand side, we added natural language keywords like "go" and
"changeState" to make it easier to understand the program. This allows the user to
stay in the same state implicitly.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?
Ideas where the user would use an * to specify something is easier in our design
because the * is implied.  When the user has more than one idea per state it
is also easier to use our design since they need only specify the state once.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?
It is a little more verbose to specify something where you care about the state
of all 4 directions explicitly. For example writing something that was
previously written as 0 xxxx -> N 1 would be a little more difficult/verbose.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?
We would rate our thing a 6 because we changed the syntax of the state, added
natural language stuff, and pulled out information about the states. We still
use the arrow syntax, which makes it feel like it is not a complete makeover.

## Is there anything you would improve about your design?
We are quite satisfied with our design as it is currently described. If there was
some we wanted to change we would've made the change in the design document above.