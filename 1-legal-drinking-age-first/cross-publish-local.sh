#!/bin/bash

# First, clean all targets to ensure no artifacts remained from previous compilations.
# Then, compile project for all Scala versions.
# Finally (if all compilations were successful), publish the artifacts to a local binary repository.

echo Publishing project to a local repository ...
exec "$( dirname "$0" )/sbt.sh" clean +compile +publishLocal "$@"
