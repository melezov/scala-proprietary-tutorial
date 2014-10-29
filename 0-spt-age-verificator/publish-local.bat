@echo off

rem First, clean all targets to ensure no artifacts remained from previous compilations.
rem Then, a compilation will be triggered by publishLocal - since it is a Java project, no cross-compilation is necessary.
rem Finally (if the compilation was successful), publish the artifacts to a local binary repository.

echo Publishing project to a local repository ...
call "%~dp0sbt.bat" clean publishLocal %*
