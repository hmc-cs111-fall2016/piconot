# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
The following are some observations we made and challenges we encountered when creating our internal DSL.

* Initially, we thought it would be very easy to move sequentially from "command" to the next (each line of instructions is a command). However, we then realized that it's tricky to have some commands include an explicit designation of the next command to jump to, and for other commands to not include that explicit designation. At first, we didn't want to write " +1 " at the end of every command if we wanted the machine to proceed by evaluating the next step. We only wanted to specify the next step if we wanted the machine to jump to any command NOT directly after the current command, check if the condition of the command is satisfied, move in the designated direction if so, and check the condition of the next command if the condition of the current command is not satisfied. We also wanted to use the asterisk to instruct the machine to reevaluate the condition of the current command (the condition being the combination of free/occupied States) again, and move accordingly, until the condition of that command is no logner satisfied.. However, we didn't know how to keep track of a counter that knew the current state, so we had to explicitly name the CurrentState and the NextState.  

* Throughout writing the internal DSL, we realized that it was very clever for the original Piconot language to present the state of the 4 RelativeDescriptions using an asterisk, N/E/W/S, or an X. This is really convenient from both the parsing perspective, and for conveniently translating from user input to create a new "Surrroundings" object. Our method for representing whether or not a RelativeDescription could be anything, occupied, or free, seemed like a good idea at first, but then proved to be difficult. Our language asks the user to put the letters of free RelativeDescriptions inside (), and to put the letters of free RelativeDescriptions inside []. The user does not have to explicitly name the letters that could be Anything (either free or occupied). However, this posed some challenges for creating the DSL.
1) How can we make it obvious to the user that he/she should first list the "free" letters, and then the "occupied" letters? Or should we design our internal/external DSL so that it can support either? 
2) What if there are no exclusively free letters and/or there are no exclusively occupied letters (where letters represent the RelativeDescriptions)? Should we have () and/or [] with nothing between the brackets? That would make our language easier to parse, but definitely less intuitive and unnecessarily verbose.

* We also realized why the original Picobot language included 2 integers to represent states: one to represent the current staet, and the other to represent the state that the machine should go to after the current command is executed.

* In the case clas Free, which takes 4 strings and has a method called toFreeArray, we realized that a "Free" array can have 0, 1, 2, 3, of 4 parameters, representing how many States are free. We did not want to create 5 different case class objects representing the 5 possible constructors for the case class Free, so we changed the syntax (see Empty.scala) so that Free needed to take 4 String parameters. Below is an example of a command:

> Condition(CurrentState(0), Free("W", "", "", ""), Occupied("", "", "", ""), Next("W"), 0).set.

Whereas our original language would have been:
 > Condition(CurrentState(0), Free("W"), Occupied(), Next("W"), 0).set, 
 
 which allows both Free and Occupied to only anywhere between 0 and 4 parameters, inclusive. 


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

It took quite a bit of thinking (especially when it came to using case classes, abstract classes, defs, etc.) , but this is how we ended up mapping our syntax to the provided API:

Within our InternalDSL class, we have a method called setSurroundings which takes two string arrays and returns a Surroundings object. The goal of this method is to take 2 string arrays, one representing the letters that are exclusively free (so not either free OR occupied) and the letters that are exclusively occupied. Our method achieves this by first creating an array of 4 RelativeDescriptions, the 0th index corresponding to the RelativeDescription of N that the user designates (either Anything, Occupied, or Free), and the 3rd index representing the RelativeDescription of S that the user designates (either Anything, Occupied, or Free). Then, we have 8 if statements to check whether N, E, W, or S is in the "Free" string array, and whether N, E, W, or S is in the "Occupied" string array. If, for example, W is in the "Free" string array, we would assign array[2] = Free to the array of 4 RelativeDescriptions. At the end, we return the array of 4 RelativeDescriptions, which is either populated with all "Anything"'s, or a combination of "Anything", "Free", and "Occupied". 

We tried to make our language fit within the Scala syntax, but we didn't see pattern matching as an option, which, after looking at Prof. Ben's solutions, seems to have been one of the only ways to make our language work as an internal DSL. However, we ended up with an internal DSL that served as an intermediate DSL to our external DSL. Implementing the internal DSL was difficult -- we would give it an 8.

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

We changed out syntax a little from our original design. 

The first change is that we include the integer of the current state. We made this change because it's difficult to otherwise keep track of the current state number.

The second change is that we use the plus character '+' to represent moving from one state directly to the next state. In our original design, we wanted to have an optional character at the end of one line of the command. If there was an asterisk at the end of the rule, that means that the program should stay at that state (the idea is that the asterisk generally represents repetition of a previously-undetermined amount of times). If there was no asterisk, that means the program would jump to the next state (aka currentState ++). If there was either a positive or negative number, then the program would add that number to the integer of the currentState to get the nextState. Now, we use the plus character '+' to represent moving from currentState to currentState++. Originally, we didn't want to have a special character designated for moving to the next state (aka currentState++) because we thought that a lot of programmers would be very used to working with sequential code, and moving to currentState++ seemed very obvious and unnecessary to explicitly state. However, including the optional character seemed to also introduce some inconsistencies in our language. Thus, we settled for replacing the "optional character" with a mandatory character (either asterisk, +, or a positive/negative integer) to represent the integer of the nextState.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

We wanted to allow some flexibility in the language. Our final external DSL language is in this format:

Int currentState ~ 
*(Array[RelativeDescriptions] free) ~ 
[Array[RelativeDescriptions occupied]* ~ 
RelativeDescription nextDirection ~
Int nextState

The elements in italics (freeArrayand occupiedArray) are optional:
If there are no free directions, the user can write () (parentheses with no letters inside) or exclude the () entirely. If the user decides to use (), he/she must list the occupiedArray after the freeArray. For example, the user can write either ()[N,E] or [N,E], but cannot write [N,E]().

Similarly, if there are no occupied directions, the user can write [] (square brackets with no letters inside) or exclude the [] entirely. If the user decides to use [], he/she must list the occupiedArray after the freeArray. For example, the user can write either (N,E)[] or (N,E), but cannot write [](N,E).

#Parser class 
In the Parser class, we also account for white spaces in the rule.

The first class we implemented in our external DSL was our Parser class. We defined each element of the "rule" :
currentState 
freeDirections
occDirections
dir (which represents the direction to go)
nextState

Afterwards, we also defined:
apply (which parses the entire program)
program (composed of 0 or more rules)
rule (one line of the program)

The following is the def nextState:
 def nextState : Parser[Int] = 
   ("""^-?[0-9]\d*""".r ^^ { s => (s.toInt)}
   | """^\*""".r ^^ {s => 0}
   | """^\+""".r ^^ {s => 1})

The challenge in implementing this understanding parser combinators. In our original design, we wanted to have a blank space at the end of the rule to represent advancing to currentState++. However, this always gave us an error message when we were creating the tests. Then, we realized that it is because we parse each rule with the override protected val.

Writing def dir was relatively simple.

Writing def freeDirections and def occDirections was a little difficult. The goal was to convert a list of directions between brackets to an occupied object used as an intermediate representation:
def freeDirections : Parser[Free] =  
   "(" ~> repsep(dir, ",") <~ ")" ^^ { (fd: List[MoveDirection]) => Free(fd) }
   
def occDirections : Parser[Occupied] =  
   "[" ~> repsep(dir, ",") <~ "]" ^^ { (od: List[MoveDirection]) => Occupied(od) }
  
At first, instead of returning Parser[Free] and Parser[Occupied], we returned Parser[Any] because we had not yet written the intermediate representation and semantics. 

Writing def rule allowed for the flexibility in whether the user wanted to include () if there were no free directions, and whether the user wanted to include [] if there were no occupied directions. However, in retrospect, we didn't define a way for the user to specify neither the free directions NOR the occupied directions. 
As you can see below, the first pattern matches for when currentState, freeDirections, dir, and nextState are specified.
The second pattern matches for when currentState, occDirections, dir, and nextState are specified.
The third pattern matches for when currentState, freeDirections, occDirections, dir, and nextState are specified.

def rule : Parser[Rule] = (
    currentState ~ freeDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ free ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, free, Occupied(List()), dir, nextState).createRule }
    | currentState ~ occDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ occ ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, Free(List()), occ, dir, nextState).createRule }
    | currentState ~ freeDirections ~ occDirections ~ ":" ~ dir ~ nextState ^^
      { case currState ~ free ~ occ ~ ":" ~ dir ~ nextState   ⇒
        Condition(currState, free, occ, dir, nextState).createRule }
 )
 
 Another thing we had to
 
 # Intermediate Representation 
 
 Our biggest struggle with the intermediate representation is that we did so much with the Parser class that for some parts of the program, it didn't seem like we needed to rely on another layer of representation to be defined in the intermediate representation class.
 
 For example, the following is the case class for the "Free" object, which takes a list of MoveDirections. In the original parser, MoveDirections had to be in a list because repsep creates a List object. We use the List object that def freeDirections, in the Parser class, creates, and use that as the argument to the Free case class, in the IR class, and make the Free object an array. However, we could have combined the two steps -- MoveDirections to List of MoveDirections to Array of MoveDirections -- into one step in the Parser. That's what we did at first, and why we didn't think we needed to define a case class for Free in the Internal Representation. However, we ended up deciding to break it down into two steps: MoveDirections to List of MoveDirections in the Parser class, and List of MoveDirections to Array of MoveDirections in the Intermediate Representation class. Here is the final case class we wrote in the Intermediate Representation class:
 
 // A list of of the "Open" directions in Surroundings
case class Free (freeList: List[MoveDirection]) extends ExternalDSL{ 
  def toFreeArray = {
    freeList.toArray
  } 
}

The case class Condition in our external DSL is very similar to the case class Condition in our internal DSL.

# Tests 

We spent a lot of time writing the tests for our external DSL. Part of the struggle was that the IDE did not provide a reason for any error, but it only provided what the assert specified that the result would be. We tried using "should be" statements, assertResult instead of assert, and also imported different packages and extended different classes. At first, we tried doing an assert on an entire rule (one line of the program), but then decided to break each rule down into its different components and test each additional component incrementally. For example, we first tested the parsing for currentState. When we parsed that correctly, we then parsed the currentState and freeDirections. When we parsed that correctly, we then parsed the currentState and freeDirections and occDirections, and so on, until we could parse different iterations of the rule correctly (different iterations because we provide some semantic flexibility in writing a rule).

It was during the tests that we realized we wanted to make the change from using an optional character to represent the nextState versus using one of 3 types of characters: a number (either positive or negative), an asterisk, or a plus sign. 

# Difficulty

Anna and I would say that we'd give implementing the external DSL a difficulty of 8. We struggled with a general lack of familiarity with Scala (and with other challenges I've already outlined), and sometimes had to deviate from our original design to make our language easier to parse. 
