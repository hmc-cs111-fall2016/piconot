package piconot.external

import picolib.semantics._
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalatest.Matchers
import org.scalatest.FunSuite

class ParserCheck extends FunSuite with Matchers{  
   
   /*
    * Checking all rules to solve an empty maze.
    * Checks current state, open surroundings, blocked surroundings, 
    * next directions, and next state. 
    * 
    * Initially began with smaller test cases that dealt with individual methods
    * in the Parser and IR classes, but after passing those tests, simply tested
    * the actual rules that are required to solve the empty room.
    */
  
   test ("check first rule") {
     Parser("0 (W)[]:W*").get should be (List(Rule(State("0"), Surroundings(Anything, Anything, Open, Anything), West, State("0"))))
   }
   
   test ("check second rule") {
     Parser("0 ()[W]:_+").get should be (List(Rule(State("0"), Surroundings(Anything, Anything, Blocked, Anything), StayHere, State("1"))))
   }
   
   test ("check third rule") {
     Parser("1 (N):N*").get should be (List(Rule(State("1"), Surroundings(Open, Anything, Anything, Anything), North, State("1"))))
   }
   
   test ("check fourth rule") {
     Parser("1 [N]:S+").get should be (List(Rule(State("1"), Surroundings(Blocked, Anything, Anything, Anything), South, State("2"))))
   }
   
   test ("check fifth rule") {
     Parser("2 (S):S*").get should be (List(Rule(State("2"), Surroundings(Anything, Anything, Anything, Open), South, State("2"))))
   }
   
   test ("check sixth rule") {
     Parser("2 [S]:E+").get should be (List(Rule(State("2"), Surroundings(Anything, Anything, Anything, Blocked), East, State("3"))))
   }
   
   test ("check seventh rule") {
     Parser("3 (N):N*").get should be (List(Rule(State("3"), Surroundings(Open, Anything, Anything, Anything), North, State("3"))))
   }
   
   test ("check eighth rule") {
     Parser("3 [N]:E -1").get should be (List(Rule(State("3"), Surroundings(Blocked, Anything, Anything, Anything), East, State("2"))))
   }
}