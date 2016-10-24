# Building and running Piconot

To run piconot, run the following command in the same highest-level directory:

`sbt "run-main piconot.external.Piconot path/to/map path/to/program"`

where `path/to/map` is the path to the picobot map and `path/to/program` is the
path to the program in the external language.

Note that only text output of picobot moving is given.