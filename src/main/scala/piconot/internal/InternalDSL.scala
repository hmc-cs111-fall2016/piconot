package piconot.internal
import picolib.maze.Maze
import picolib.semantics._
import scala.collection.mutable.MutableList
import java.io.File
import scalafx.application.JFXApp

/*
 * Internal DSL implementation for creating Picobot rules.
 * Despite great effort, we were unable to make an accurate implementation 
 * of our language using Internal DSL methods. Though it may have been possible
 * with intense pattern-matching, this would have been way too timely. We decided
 * to make a version of our DSL that ended up being useful as an intermediate 
 * representation for our External DSL. So not all was done in vain!
 */
class InternalDSL extends JFXApp {
  
  def setSurroundings (freeArray:Array[String], occArray:Array[String]) : Surroundings = { 
    
    // Define surroundings
     var surr_array = new Array[RelativeDescription](4)
     
    // Initialize all indices to Anything
     var x = 0
     for (x<- 0 to (surr_array.size - 1)) {
       surr_array.update(x, Anything)
     }
     
    // Set N, E, W, or S to Open
     if (freeArray.contains("N")) {
       surr_array.update(0, Open)
     }
     if (freeArray.contains("E")) {
       surr_array.update(1, Open)
     }
     if (freeArray.contains("W")) {
       surr_array.update(2, Open)
     }
     if (freeArray.contains("S")) {
       surr_array.update(3, Open)
     }
     
     // Set N, E, W, or S to Blocked
     if (occArray.contains("N")) {
       surr_array.update(0, Blocked)
     }
     if (occArray.contains("E")) {
       surr_array.update(1, Blocked)
     }
     if (occArray.contains("W")) {
       surr_array.update(2, Blocked)
     }
     if (occArray.contains("S")) {
       surr_array.update(3, Blocked)
     }
     
     // Return Surroundings object
     new Surroundings(surr_array(0), surr_array(1), surr_array(2), surr_array(3))   
  }
  
  // Read in maze file and run picobot using list of rules
  def runPicobot (rules: List[Rule], mazeType: String) {
    val emptyMaze = Maze("resources" + File.separator + mazeType)
    
    val bot = new Picobot(emptyMaze, rules) with TextDisplay with GUIDisplay

    stage = bot.mainStage

    bot.run()
  }
}  

// Current state of picobot
case class CurrentState(state: Int)

// Open directions in Surroundings
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

// Blocked directions in Surroundings
case class Occupied (dir1: String, dir2: String, dir3: String, dir4: String) {
  def toOccArray = {
    var occ_array = new Array[String](4)
    occ_array.update(0, dir1)
    occ_array.update(1, dir2)
    occ_array.update(2, dir3)
    occ_array.update(3, dir4)
    occ_array.filter(_.nonEmpty)
   
    occ_array
  }
}

// Next direction for picobot to travel in
case class Next (dir: String) extends InternalDSL {
  def setNext = 
   dir match {
      case "N" => North
      case "E" => East
      case "W" => West
      case "S" => South
      case "_" => StayHere
   }
}

// Creates a Rule object based on set of conditions
case class Condition (currState: CurrentState, free: Free, occ: Occupied, dir: Next, nextState: Int) 
  extends InternalDSL {
  
  def set = {
    Rule(
      State(currState.state.toString), 
      setSurroundings(free.toFreeArray, occ.toOccArray), 
      dir.setNext,
      State((currState.state + nextState).toString()))
  }
}
