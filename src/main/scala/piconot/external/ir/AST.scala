package piconot.external.ir

// General rule
case class OurRule(left: OurState, right: OurAction)

// Left-hand side of rule
case class OurState(stateNumber: String, walls: String)

// Right-hand side of rule
sealed abstract class OurAction

// Right-hand side can be one of three things
case class OnlyMove(m: String) extends OurAction
case class OnlyChangeState(c: String) extends OurAction
case class BothActions(m: String, c: String) extends OurAction
