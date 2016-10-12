# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

1. The user must include quotes around each state_name, because there's no way to have arbitrary identifiers unless they're strings.

1. Now you have to add a comma between the states and wrap the state definitions in `Program ( ... )`.

1. Rules must be declared inside a `rule ( ... )` block. For example,
```scala
rule(move North, face East, "next_state")
```

1. We have to combine rules with `,` because it was much easier to implement as a list.

1. Made directions start with an uppercase so that we can reuse abstract representations

1. Require parentheses for all keywords, since we can make the infix operators, but we can't make them prefix functions.

1. Curly braces replaced by parentheses, since you can only use them interchangeably if the method is a one-argument function (whereas ours is variadic)

1. Now you must put everything inside a call to `runPiconot`, so the program will look like:

```scala
runPiconot(
    startstate="myState",
    startdir=North
)
(
    state("myState")(
        ...
        )
    ...
)
```

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

We would say a 7.  We were able to keep the structure of the language relatively intact, but the concrete syntax is almost entirely different.  In general, we had to include more parentheses, quotations, and commas then we originally hoped for.  We also had to replace curly braces with parentheses and added the `rule` "keyword".

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

Around a 6.  It definitely was not a 1-1 mapping from the internal representation to provided API, as was the case with the Regex assignment.  This was because for each "rule" that the user provided, we had to create four Picobot rules, since we incorporated relative direction.  However, this part of the assignment was much easier than creating the internal representation because it was more a programming challenge and less of a Scala challenge.

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

No changes.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

0

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

0(?) We were able to use the same internal representation and semantics as our
internal DSL.
