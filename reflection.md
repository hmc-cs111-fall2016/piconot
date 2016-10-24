Reflection: Looking Back on Our Process
=======================================

After our group spent many more hours than the suggested time attempting to
implement all components of our initial goals, we decided to reflect on the
experience and the learning:

The first part of the project went smoothly: we met and brainstormed to come up
with a new design for the picolab langugae. After throwing around a few ideas,
we settled on the design explained in the project and partially implemented.

Once we moved to the Scala component, we stumbled to figure out the ideal layout
of the project and starter files. We began working on our AST which included
several case classes and other objects that fully encapsulated the structure.
We arrived at something we all agreed would work for our lanaguage. Next,
we attempted to create the implementation that would take something from our AST
and internal DSL and transform it into the list or rules that are compatible
with the provided API. While this compiled, the object responsible for
transforming our AST into the API-compatible representation kept producing null
pointer errors. The instance variables, despite being initialized, kept being
null; upon further investigation, we could not resolve the error. We composed a
basic example with one class and mimiced how we were using it and did not get
the error, but for some reason the way we were using it in the actual project
produced an error.

Although the code is not in a fully working state, we believe all the pieces are
there including a basic parser for our external DSL. Our general approach was 
as follows:

1. Create an AST for our DSL.
2. Create a function to transform our AST into the API-compatible representation.
3. Create the example programs.
4. Create the parser that produces an AST which we could reuse the evaluator from
   step 2.
5. Create a CLI for the externl DSL.

If we were to complete this project again, we would likely choose a simpler
lanaguge to try to implement since our main stumbling blocks were Scala itself
and not the languge parsing or representation. Despite our not completing all
the goals, we believe it was a valuable learning experience nonetheless. All
the logic for evaluation and state creation exists in our code as well as the
principles behind the parser.

