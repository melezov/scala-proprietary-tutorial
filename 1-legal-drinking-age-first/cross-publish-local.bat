@echo off

rem First, clean all targets to ensure no artifacts remained from previous compilations.
rem Then, compile project for all Scala versions.
rem Finally (if all compilations were successful), publish the artifacts to a local binary repository.

echo Publishing project to a local repository ...
call "%~dp0sbt.bat" clean +compile +publishLocal %*
