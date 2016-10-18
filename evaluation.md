# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
The following are some observations we made and challenges we encountered when creating our internal DSL.

* Initially, we thought it would be very easy to move sequentially from "command" to the next (each line of instructions is a command). However, we then realized that it's tricky to have some commands include an explicit designation of the next command to jump to, and for other commands to not include that explicit designation. At first, we didn't want to write " +1 " at the end of every command if we wanted the machine to proceed by evaluating the next step, 

* Throughout writing the internal DSL, we realized that it was very clever for the original Piconot language to present the state of the 4 RelativeDescriptions using an asterisk, N/E/W/S, or an X. This is really convenient from both the parsing perspective, and for conveniently translating from user input to create a new "Surrroundings" object. Our method for representing whether or not a RelativeDescription could be anything, occupied, or free, seemed like a good idea at first, but then proved to be difficult. Our language asks the user to put the letters of free RelativeDescriptions inside (), and to put the letters of free RelativeDescriptions inside []. The user does not have to explicitly name the letters that could be Anything (either free or occupied). However, this posed some challenges for creating the DSL.
1) How can we make it obvious to the user that he/she should first list the "free" letters, and then the "occupied" letters? Or should we design our internal/external DSL so that it can support either? 
2) What if there are no exclusively free letters and/or there are no exclusively occupied letters (where letters represent the RelativeDescriptions)? Should we have () and/or [] with nothing between the brackets? That would make our language easier to parse, but definitely less intuitive and unnecessarily verbose.

* We also realized why the original Picobot language included 2 integers to represent states: one to represent the current staet, and the other to represent the state that the machine should go to after the current command is executed.

* In the case clas Free, which takes 4 strings and has a method called toFreeArray, we realized that a "Free" array can have 0, 1, 2, 3, of 4 parameters, representing how many States are free. We did not want to create 5 different case class objects representing the 5 possible constructors for the case class Free, so we changed the syntax so that 
case class Free (dir1: String, dir2: String, dir3: String, dir4: String) {
    def toFreeArray = {
      var free_array = new Array[String](4)
      free_array.update(0, dir1)
      free_array.update(1, dir2)
      free_array.update(2, dir3)
      free_array.update(3, dir4)
      free_array.filter(_.nonEmpty)
      free_array
    }
}


**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

It took quite a bit of thinking (especially when it came to using case classes, abstract classes, defs, etc.) , but this is how we ended up mapping our syntax to the provided API:

Within our InternalDSL class, we have a method called setSurroundings which takes two string arrays and returns a Surroundings object. The goal of this method is to take 2 string arrays, one representing the letters that are exclusively free (so not either free OR occupied) and the letters that are exclusively occupied. Our method achieves this by first creating an array of 4 RelativeDescriptions, the 0th index corresponding to the RelativeDescription of N that the user designates (either Anything, Occupied, or Free), and the 3rd index representing the RelativeDescription of S that the user designates (either Anything, Occupied, or Free). Then, we have 8 if statements to check whether N, E, W, or S is in the "Free" string array, and whether N, E, W, or S is in the "Occupied" string array. If, for example, W is in the "Free" string array, we would assign array[2] = Free to the array of 4 RelativeDescriptions. At the end, we return the array of 4 RelativeDescriptions, which is either populated with all "Anything"'s, or a combination of "Anything", "Free", and "Occupied". 




## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**




**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
