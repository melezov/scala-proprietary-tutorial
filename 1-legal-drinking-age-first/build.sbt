organization := "hr.element.spt"

name := "legal-drinking-age-first"

version := "0.1.0"

scalaVersion := "2.11.2"

unmanagedSourceDirectories in Test := (javaSource in Test).value :: Nil

libraryDependencies ++= Seq(
  "hr.element.spt" % "spt-age-verificator" % "1.0.0",
  "hr.element.spt" % "spt-countries" % "1.0.0",
  "com.novocode" % "junit-interface" % "0.9" % "test"
)

EclipseKeys.eclipseOutput := Some(".target")

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)
