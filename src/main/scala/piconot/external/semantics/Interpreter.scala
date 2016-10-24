package piconot.external
import picolib.maze.Maze
import picolib.semantics._

import piconot.external.ir._

package object semantics {
  def eval(rules: List[OurRule]): List[Rule] = {
    rules.map(x => evalHelper(x))
  }
  def evalHelper(rule: OurRule): Rule = rule match {
    case OurRule(OurState(s,w),OnlyMove(m)) =>
        Rule( 
          State(s),
          convertToSurroundings(w),
          convertDirection(m), 
          State(s)
        )
    case OurRule(OurState(s,w),OnlyChangeState(c)) =>
        Rule( 
          State(s),
          convertToSurroundings(w),
          StayHere, 
          State(c)
        )
     case OurRule(OurState(s,w),BothActions(m,c)) =>
        Rule( 
          State(s),
          convertToSurroundings(w),
          convertDirection(m), 
          State(c)
        )
  }
  
  // WARNING: Doesn't check if the String is a valid command
  def convertDirection(dir: String): picolib.semantics.MoveDirection = {
    if (dir == "W") {
      West
    } else if (dir == "N") {
      North
    } else if (dir == "S") {
      South
    } else {
      East
    }
  }
  
  def convertToSurroundings(sur: String): picolib.semantics.Surroundings = {
    convertToSurroundingsHelper(sur, Surroundings(Anything, Anything, Anything, Anything))
  }
  
  def convertToSurroundingsHelper(stringSur: String, actualSur: picolib.semantics.Surroundings): picolib.semantics.Surroundings = {
    if (stringSur.length() == 0) {
      actualSur
    } else if (stringSur.charAt(0) == '~') {
      val newSur = createNewSur(stringSur.charAt(1), false, actualSur)
      convertToSurroundingsHelper(stringSur.substring(2), newSur)
    } else {
      val newSur = createNewSur(stringSur.charAt(0), true, actualSur)
      convertToSurroundingsHelper(stringSur.substring(1), newSur)
    }
  }
  
  def createNewSur(dir: Char, isBlocked: Boolean, actualSur: picolib.semantics.Surroundings): picolib.semantics.Surroundings = {
    var Surroundings(oldN, oldE, oldW, oldS) = actualSur
    
    if (isBlocked) {
      if (dir == 'N') {
        oldN = Blocked
        Surroundings(oldN, oldE, oldW, oldS)
      } else if (dir == 'E') {
        oldE = Blocked
        Surroundings(oldN, oldE, oldW, oldS)
      } else if (dir == 'W') {
        oldW = Blocked
        Surroundings(oldN, oldE, oldW, oldS)
      } else {
        oldS = Blocked
        Surroundings(oldN, oldE, oldW, oldS)
      }
    } else {
      if (dir == 'N') {
        oldN = Open
        Surroundings(oldN, oldE, oldW, oldS)
      } else if (dir == 'E') {
        oldE = Open
        Surroundings(oldN, oldE, oldW, oldS)
      } else if (dir == 'W') {
        oldW = Open
        Surroundings(oldN, oldE, oldW, oldS)
      } else {
        oldS = Open
        Surroundings(oldN, oldE, oldW, oldS)
      }
    }

  }
    
}