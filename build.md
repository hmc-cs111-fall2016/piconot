# Building and running Piconot

To run the Piconot program defined in `source.bot` on the maze defined in
`maze.txt`, run the following command:
```bash
sbt "run-main piconot.external.Piconot maze.txt source.bot"
```

When the program runs, Piconot will start out in the first `state` defined in
that file, and he will be facing north. Piconot will try to execute each rule
in that state in the order in which they are listed. If a rule succeeds,
Piconot will stop trying rules and will repeat the procedure. If a rule fails
(because Piconot tries to move in a direction which is blocked by a wall),
Piconot will try the next rule in the state. If all the rules in a state fail,
Piconot will give up and be sad.
