# Design

A grammar for our language:
```
<program>    := <state> <program>
              | <state>

<state>      := state <state_name> <rules>

<rules>      := <rule> <rules>
              | <rule>

<rule>       := "|" move <dir>
              | "|" face <dir>
              | "|" <state_name>
              | "|" move <dir>, face <dir>
              | "|" move <dir>, <state_name>
              | "|" face <dir>, <state_name>
              | "|" move <dir>, face <dir> <state_name>

<dir>        := <absdir> | <reldir>
<absdir>     := north | south | east | west
<reldir>     := left | right | forward | backward

<state_name> := [a-zA-Z][a-zA-Z0-9_]*
```
## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

The target of this design is similar to the target for the original Picobot.
The language is meant for students with little CS knowledge, since it doesn't
assume familiarity with CS concepts other than states and state transitions, in
the same way Picobot does.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

We chose this design while thinking about the solution for the maze.  Many of
the states were redundant in order to achieve the same behavior when moving in
all four directions.  Our language adds the option to condense such groups of
four states into one.  We thought this would a good design since lots of
Picobot programs are symmetrical.  Our syntax is also more explicit than
Picobot: it allows users to name states and includes keywords like `move` and
`face`.  This allows a reader of the program to infer the meaning without
understanding too much of the structure.  By contrast, much of the meaning of
the Picobot program depends on the structure.  For example, in `N*x*` the
meaning of the `x` is unclear unless you are familiar with the structure.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It is easier to express symmetric sets of rules, since you only need one state
and not four.  This is possible since we can express direction relatively, so
you can express the action of moving "foward" in one state (`move forward`)
instead of four states (`move north`, `move west`, `move east`, `move south`).
It is also easier to express turning and moving in the same direction, since
the command `move right` implies that the picobot will then face right (you can
override this command with, for example, `face left`).  This is more similar to
the way humans think about moving.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

There are some Picobot programs that do not have an obvious translation to our
language.  For example, `0 xx** -> N 0` means that the bot should _only_ move
North if both the North and the West are open.  There is no easy way to express
this in our language, because you can only make decisions by trying a direction
and then moving in that same direction.  However, we could not think of any use
cases of this, so we decided not to over-complicate our language by including
this feature.  Also, we conjecture that any program written with that pattern
can be expressed in our language (the proof is left as an exercise for the
reader).

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

We would say it is a 7.  It is still modeled by a state machine, but the
appearance of the syntax is very different.  Our language includes more
keywords and rules are structured very differently.

## Is there anything you would improve about your design?

Ideally, we could express the pattern mentioned above, but the only way we
could think of doing so would be to include if statements.  We didn't want to
include too many features, so we decided that it would better to limit the
language in this way.
