This tutorial will demonstrate a way to create a proprietary Scala library
Topics covered: Java/Scala interop, SBT, Proguarding

# The setup (our story begins)

You have been contracted by a Client to create an implementation of a certain Java interface.  
The interface itself has been provided by the Client, is open source, and cannot be modified.

In our imaginary example, the interface in question will be an [AgeVerificator].

    package hr.element.spt;
    
    public interface AgeVerificator {
        public boolean isUnderage(final int age);
    } 

The Client is a well established entity in the world of age verification, and already has mulitple products which implement this AgeVerificator in different ways:

    MarriageableAgeVerificator
    WineMaturityVerificator
    ... etc ...
    
Your particular contract requires of you to implement a legal drinking age verification library which will determine whether a person can purchase a bottle of wine in various countries.

    package hr.element.spt.lda;
    
    public class LegalDrinkingAgeVerificatorFactory {
        public AgeVerificator getAgeVerificator(final Country country) {
           // ... code goes here ...
        }
    }
    
The client has also placed some sane requirements regarding the implementation:

- The implementation must work on JVM 6+

- You must provide JUnit tests providing code coverage

- If your library requires a set of dependencies, they must be either
  - from a set of libaries pre-approved by the Client (in our example, sbt-countries)
  - packaged within the library itself (the library should not have external dependencies)

You are a Scala developer, and solving such a problem using your favorite programming language will not be a problem.

# First implementation

In order to follow up on the setup, you need to create two dependencies for your project.

The first dependency is the sbt-age-verificator interface, the second is the list of [ISO-3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) countries that the client uses in their projects.  
Since in our story the dependencies have already been published by the client. To emulate this, we'll simply run publishLocal on the two Java projects:

  0-spt-age-verificator/publish-local.sh
  0-spt-countries/publish-local.sh

Now that the dependencies are in place, let's take a look at the first implementation:

But although the code has been written and all unit and integration tests pass, you can't quite finalize the project, because the Client's tech team starts imposing certain parts of their corporate culture and "the way they do things". You take a look at their requirements...

# Requirements #2

- Implementation should not leak any public classes nor methods
  - the only exception to the rule is the factory mentioned above (hr.element.spt.lda.LegalDrinkingAgeVerificatorFactory)
