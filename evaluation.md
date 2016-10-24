# Evaluation: running commentary

Please see [reflection.md](/reflection.md) for additional comments on our work.

## Internal DSL

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

3. Our ideal syntax isn't too different from the internal DSL we wrote. The main 
features such as going to a certain surrounding, jumping from a label to the other, 
and facing a specific direction remain the same. There are only two main differences.
Firstly, we were not able to write an `until` method so that instead of saying
`Go(<surrounding>)` we could say `Go until <surrounding>`. Secondly, to translate the internal
AST into the provided API we realized we needed to have our `Label` take in a list of rules
and that we needed a wrapper case class that took in a list of `Label`s. This meant we
had to have a program structure like:
```
Program (
  List(
    Label("one"){
      List(
        Rule1,
        Rule2,
        Rule3
      )
    },
    Label("two"){}
  )
)
```
instead of the intended internal structure of:
```
Label("one"){
    Rule1
    Rule2
}
Label("two"){
    Rule3
}


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
10. We never actually succeeded so it was indeed very difficult to us (as better explained in the `reflection.md`
file). We felt that the reason it was very difficult was because to have a `Jump` feature, we had to know the
start states for every `Label`. This not only required parsing through the input twice to map our syntax to
the provided API but also required a scala `Map[String,Int]` and some other class variables, which eventually
caused the errors that halted our progress. We think we eventually figured out how to map each part of our syntax
to the provided API though!



## External DSL

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
2. Our external syntax was nearly identical to our design syntax. Unlike our internal syntax, we were
able to include `Go until <surrounding>` in our syntax. The only difference was that we realized
our design syntax did not allow the user to describe all of its surroundings. We could describe
when there was a wall but we left out that the desired surrounding might be one with an
"opening." For example, if we are in the maze looking for the next place to turn left, we would
want to describe a left opening whereas previously would only describe a wall. Once we added
these cases to the internal and external parsers, it was complete.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
10. Our attempt to turn our External DSL's syntax into the provided API was never accomplished. In fact,
we never began this specific step because we believed (and still believe) that this process is identical 
for the External and Internal versions of the DSL. We explained the difficulties of this process for 
the internal DSL above - basically, trying to implement a `Map[String, Int]` and use class variables
did not work. If we were too fix the mapping for the internal DSL, it would be identical for the external one.
