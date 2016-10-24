package piconot.internal
import picolib.maze.Maze
import picolib.semantics._


  /**
   * Our rule represents the left half of a picobot rule.  That is it
   * represents for what initial state this rule applies to and for which
   * opened or closed NEWS directions the rule applies to.
   */
case class OurRule (val state : OurState, val cond : Conditions){

	/**
	 * helper function to convert strings such as "S" to the move direction South
	 */
  def charToMoveDirection (s : String) : MoveDirection=
	{
		var goDirection : MoveDirection  = North;
    if (s.equals("E"))
      goDirection = East
    else if (s.equals("W"))
      goDirection = West
    else if (s.equals("S"))
      goDirection = South
    goDirection
	}
  
  /**
   * This will create a Rule that the picobot API we were given can consume
   * @param actions a string of the form go N/E/W/S/nowhere changeState INTEGER
   */
  def -> (actions : String) : Rule = 
  {
    // split the string to an array containg ["go", "<direction>", "changeState", "<stateNum>"]
    val actionsArr = actions.split(" +")
    
    // sanity check
    assert(actionsArr(0).equals("go"))
    assert(actionsArr(2).equals("changeState"))
    
    // from the string find the direction we want picobot to move
    var goDirection : MoveDirection  = StayHere;
    if (!actionsArr(1).equals("nowhere"))
      goDirection = charToMoveDirection(actionsArr(1));
    
    // do some parsing of the condition to make it a format usable by picobot API
    // that we were given
    var north : RelativeDescription = Anything
    var east : RelativeDescription = Anything
    var west : RelativeDescription = Anything
    var south : RelativeDescription = Anything
    for ( (b, d) <- (cond.blocked zip cond.dir)) {
      if (d == 'N')
        if (b)
          north = Blocked
        else
          north = Open
      else if (d == 'E')
        if (b)
          east = Blocked
        else
          east = Open
      else if (d == 'S')
        if (b)
          south = Blocked
        else
          south = Open
      else if (d == 'W')
        if (b)
          west = Blocked
        else
          west = Open
    }

    // now return the rule we have constructed the information for
    Rule( 
      State(state.s.toString), 
      Surroundings(north, east, west, south), 
      goDirection, 
      State(actionsArr(3))
    )
  }
}

case class OurState (val s: Int)
{
  
}

case class Conditions (val blocked : List[Boolean], 
    val dir : List[Char])
{
  
}

object PicoBotExtender {
	def conditionConverterHelp(value : String, blockedBools : List[Boolean]
			, dirList : List[Char]) : Conditions =
			if (value.size == 0)
				Conditions(blockedBools, dirList)
			else if (value.charAt(0) == '~')
				conditionConverterHelp(value.tail.tail, true :: blockedBools, value.charAt(1) :: dirList)
			else
				conditionConverterHelp(value.tail, false :: blockedBools, value.charAt(0) :: dirList)

   implicit def StringToOurRule(value : String) : OurRule =
   {
		 // From a string like 0N~S will convert it to an appropriate OurRule object
		 // which represents the rule for state 0 when north is free and south is blocked
     OurRule(OurState(value.charAt(0).toString.toInt), conditionConverterHelp(value.tail, List(), List()))
   }

}