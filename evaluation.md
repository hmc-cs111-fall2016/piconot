# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
In the internal DSL we changed from specifying all rules for one state to specifying
the state explicitely for each rule.  I think this is a change of size 4 because
while we do lose a little bit of the flavor of out language since a little more
typing is required, it is not a huge burden to type one more character for each
rule.  We made this change because it seemed very hard to figure out how to
specify something like "state 1 { rules }" in the internal language but not too
hard to just parse each rule individually if it was given the state.

We also made the user specify both go and changestate for each rule. We did this to
make it easier to process the string that the user gives. This seems like a change
of about 3 because while it was not in the orginal design, it does not detract 
too much from the users' experience of the language since it has a
fairly natural language feel to say go somewhere changeState some number.
Additionally we allow the user to say go nowhere, which further feels fairly
natural to say.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

After making these changes it was about a 5 to map our syntax to the provided API.
Our syntax collects all the necessary information and then some amount of processing
of our input is necessary to convert it to a format that is usable to the provided
API.  This conversion is a bit annoying and requires a number of if statements,
but is not overly difficult.  Therefor I think a 5 makes sense because it wasn't
really difficult to make the change, but it was a little tedious.



## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

From example-ideal.txt, one thing we changed was that the rules were grouped by states. The reason
why we made this change is because we thought it was going to be tricky to parse. However, after
implementing our current version of the external language, we believe it is now doable (although
we sadly don't have the time now to do it now).

There are a few smaller changes we have. There were ampersands between go's and changeState's,
but we felt it was ok to drop them. People can still understand the code fairly well in my opinion.
We added semicolons in the external DSL to mark the end of a rule to make it easier to read rules
if they are on the same line. So far a rule now looks something like:

`0~W -> go W changeState 1;`

We added colons between the currentState and which walls are blocked, free, etc. to temporarily
make things easier to read in case we do decide to improve upon this design. The above rule
now looks like:

`0:~W -> go W changeState 1;`

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

I feel like we had to make a few changes, giving it a 4. The main thing I really wanted was
there to be rules grouped by states, so that there does not have to be redundant information
about current states. I feel with a bit more time, we could implement this.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

I feel that it was a bit tedious to map the syntax, so a rating of 5 seems suitable. We wrote
a few helper functions that dealt with the cardinal directions. We had these helper functions
because we had to map values like "~W" and "N" to a Surrounding variable or a cardinal direction
like North. Perhaps the only technical thing that we had to look up was the unapply method in
order to pull out values from a Surrounding value. This was not too bad because we probably did
this in class, and it seemed natural to do so because pattern matching takes advantage of this.
