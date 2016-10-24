# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
10. The syntax is not as clear as we wanted it to be initially. Now we're relying
on one method, `If`, that calls every other method.  Thus, we're passing in strings as
parameters and calling the other relevant functions, `Nothing`, `Something`, and `Go` from
the method `If`. 

We've changed from having 
```
North = {
    if (Nothing[N]) Go North
    if (Something[N] and Nothing [W]) Go West
    if (Something[N, W]) Go East
}
``` 
to represent all the rules for going North, to having 
```
If ("N", "Nothing(N)","Go(North)")
If ("N", "Something(N) and Nothing(W)", "Go(West)")
If ("N", "Something(N, W)", "Go(East)")
```
Now, strings have to be passed in to the main function `If`. The readability is
definitely hurt since we moved the `North` header into a parameter for `If`. 
The initial syntax made it very easy to read which rules are relevant
to certain movement directions, and that is a bit lost since we are now passing
in the direction into `If`. 


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
Probably around a 9. Currently we're having a bunch of trouble really making it fit 
with the API. We use strings for the states because we take in start and next 
states as directions rather than numbers.  Furthermore, we don't ask users for
a full set of surroundings for each rule.  Rather, they only specify if there 
is a rule that they specifically need.  For example, while the original syntax
would take in something like "N***", specifying each direction's status, ours
would be only "Something(N)", which requires us to go through more work to
conform to the API.  

One of the biggest issues we had to deal with is the fact that we had to take
in strings as our parameters for our `If` function, in order to be able to 
control the order that `Nothing`, `Something`, and `Go` were called in. We used
regex to parse out the input for these, but it seems we weren't able to perfect
it even at this point. As a result, we could not provide the proper values needed 
in some cases and it can cause our right hand rule to mess up.

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
