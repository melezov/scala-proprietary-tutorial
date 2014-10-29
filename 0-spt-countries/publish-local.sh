#!/bin/bash

# First, clean all targets to ensure no artifacts remained from previous compilations.
# Then, a compilation will be triggered by publishLocal - since it is a Java project, no cross-compilation is necessary.
# Finally (if the compilation was successful), publish the artifacts to a local binary repository.

echo Publishing project to a local repository ...
exec "$( dirname "$0" )/sbt.sh" clean publishLocal "$@"
