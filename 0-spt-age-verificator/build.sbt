organization := "hr.element.spt"

name := "spt-age-verificator"

/** This interface is pretty stable, and has been around for years :), therefore 1.0.0 */
version := "1.0.0"

/** Although scala is not required for this project, we'll hardcode the same value as in the
  * implementation project so that we do not download multiple Scala libraries */
scalaVersion := "2.11.2"

/** In order to compile this interface against Java 6 runtime library, it is not enough simply to
  * set the source / target to 1.6, one needs to also provide the path to the lowest Java runtime library
  * via the -bootclasspath parameter.
  *
  * This is due to the fact that the actual runtime libraries for 6, 7 and 8 differ, so your goal should be
  * not just to mark the class file as Java 6 compatible, but also to compile against the lowest actual runtime
  * library that you are targeting. */
javacOptions := (javacOptions in doc).value ++ Seq(
  "-deprecation"
, "-Xlint"
, "-source", "1.6"
, "-target", "1.6"
) ++ (sys.env.get("JDK16_HOME") match {
  case Some(jdk16Home) => Seq("-bootclasspath", jdk16Home + "/jre/lib/rt.jar")
  case _ => Nil
})

/** JavaDoc doesn't understand "target", "Xlint", end so on, so we'll just define the encoding here */
javacOptions in doc := Seq(
  "-encoding", "UTF-8"
)

/** The resulting library package is not Scala version dependant, so we do not want SBT to append the
  * Scala version to the library name. Flipping the crossPaths setting key will result in
  * "age-verification-interface-1.0.0.jar" instead of "age-verification-interface_2.11-1.0.0.jar" */
crossPaths := false

/** By default, SBT will include the Scala library as a dependency, and there are two ways of dealing with this.
  * The first one is to set "libraryDependencies := Nil" (notice that we're _setting_ the libraryDependencies
  * with := instead of ++= where you just add artifacts.
  * The other way is to simply set the autoScalaLibrary to false and this will do the same thing */
autoScalaLibrary := false

/** We only care about the /src/main/java source folder */
unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil

/** No tests in the interface */
unmanagedSourceDirectories in Test := Nil

/** Some Eclipse settings, this is where Eclipse will output the class files. Unless you set this,
  * Eclipse will default to just "target" which has a high probability of messing with your SBT build step. */
EclipseKeys.eclipseOutput := Some(".target")

/** We are targeting JVM 6, so Eclipse needs to know about this */
EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)

/** Setting the project flavor to Java will remove the Scala dependencies from Eclipse. */
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
