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

1. We have to combine rules with `,` because `;` cannot be overloaded.

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

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
