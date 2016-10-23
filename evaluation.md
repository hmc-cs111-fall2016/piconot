# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

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

After making these changes it was about a 5 to map our syntax to the provided API.
Our syntax collects all the necessary information and then some amount of processing
of our input is necessary to convert it to a format that is usable to the provided
API.  This conversion is a bit annoying and requires a number of if statements,
but is not overly difficult.  Therefor I think a 5 makes sense because it wasn't
really difficult to make the change, but it was a little tedious.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
