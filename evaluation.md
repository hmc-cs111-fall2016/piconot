# Evaluation: running commentary

Please see [reflection.md](/reflection.md) for additional comments on our work.

## Internal DSL
- Initially we planned to implement a method called "until", that takes in a surrounding, under the "Go" case class so that the user can say "Go until" <surrounding>. However, as we were writing our internal DSL, we didn't find the until feature as the top priority. So now the user is to write "Go" <surrounding?>, instead. 

- Similarly to the omission of "until", we wrote the internal DSL in such a way that the user is to say "Jump" <label>, instead of "Jump to" <label> for when a picobot has to move to another set of states, defined as a label. 

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax? 
3. Our ideal syntax isn't too different from the internal DSL we wrote. The main features such as going to a certain surrounding, jumping from a label to the other, and facing a specific direction remain the same. 

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._
- In the ideal syntax, we forgot to include surroundings that specified which sides were open; LEFT_OPENING and RIGHT_LEFT_OPENING, for example. As we realized that the ideal syntax allowed the user to tell picobot to go until it hit a specific wall, but not until it finds an opening on a specific side, we added more objects that extends the "Surroudning" class.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
