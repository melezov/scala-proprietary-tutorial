# Scala Proprietary Library

This tutorial will demonstrate a way to create a proprietary Scala library.  
Topics covered: Java/Scala interop, SBT, Proguarding & Co.

## The setup (our story begins)

You have been contracted by a Client to create an implementation of a certain Java interface.  
The interface itself has been provided by the Client, is open source, and cannot be modified.

In our imaginary example, the interface in question will be an [`AgeVerificator`](https://github.com/melezov/scala-proprietary-tutorial/blob/master/0-spt-age-verificator/src/main/java/hr/element/spt/AgeVerificator.java).

    package hr.element.spt;
    
    public interface AgeVerificator {
        public boolean isUnderage(final int age);
    } 

The Client is a well established entity in the world of age verification, and already has mulitple products which implement this AgeVerificator in different ways:

- `MarriageableAgeVerificator`
- `WineMaturityVerificator`

Your particular contract requires of you to implement a legal drinking age verification library which will determine whether a person can be held accountable for drinking alcohol, depending on which country we're interested in.

    package hr.element.spt.lda;
    
    public class LegalDrinkingAgeVerificatorFactory {
        public AgeVerificator getAgeVerificator(final Country country) {
           // ... code goes here ...
        }
    }
    
The client has also placed some pretty sane requirements regarding the implementation:

- The implementation must work on JVM 6+
- You must provide JUnit tests providing code coverage

You are a Scala developer, and solving such a problem using your favorite programming language will not be a problem.

## First implementation

In order to follow up on the setup, you need to create two dependencies for your project.

The first dependency is the sbt-age-verificator interface, the second is the list of [ISO-3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) countries that the client uses in their projects.  
In our story the dependencies have already been published by the client. To emulate this, we'll simply run publishLocal on the two Java projects:

    0-spt-age-verificator/publish-local.sh
    0-spt-countries/publish-local.sh

After publishing, you will have added the dependencies into the [build.sbt](https://github.com/melezov/scala-proprietary-tutorial/blob/master/1-legal-drinking-age-first/build.sbt) ...

	libraryDependencies ++= Seq(
	  "hr.element.spt" % "spt-age-verificator" % "1.0.0",
	  "hr.element.spt" % "spt-countries" % "1.0.0",
	  "com.novocode" % "junit-interface" % "0.9" % "test"
	) 

And let's take a look at the actual implementation:
    
    package hr.element.spt.lda.impl

	private [impl] object NoAgeVerification extends AgeVerificator {
	  def isUnderage(age: Int) = false
	}
	
	private [impl] class ThresholdAgeVerification(threshold: Int) extends AgeVerificator {
	  def isUnderage(age: Int) = age < threshold
	}
	
	private [lda] object LegalDrinkingAgeCountryRepository {
	  def createAgeVerifier(country: Country) =
	    country match {
	      case Country.CU | Country.NO =>
	        NoAgeVerification
	
	      case Country.GB | Country.HR | Country.NZ | Country.RU =>
	        new ThresholdAgeVerification(18)
		
		  // ...
	    }
	}

The codebase is pretty simple, and should not need commenting on.
Now we want to create a Java Factory class which will provide initialization for the Scala bits.

	package hr.element.spt.lda;

	public enum LegalDrinkingAgeVerificatorFactory {
	    INSTANCE;
	
	    public AgeVerificator getAgeVerificator(final Country country) {
	        return LegalDrinkingAgeCountryRepository.createAgeVerifier(country);
	    }
	}

Done, and done.

Usage of such a library can now be seen in a unit test:

	public void testLegalityInCroatia() {
		final AgeVerificator ageVerificator =
				LegalDrinkingAgeVerificatorFactory.INSTANCE.getAgeVerificator(Country.HR);

		Assert.assertTrue(ageVerificator.isUnderage(17));
		Assert.assertFalse(ageVerificator.isUnderage(18));
	}
 
You are now ready to prepare your first deliverable, so you package the project and send it over to the Client.  
**Y**: *This is just a first version, just take a look and send me a shout there are any questions or problems.*

... weekend passes ...

## Some eyebrows are raised

**C**: Hey the code works good, but um... could you polish it up a bit?  
**Y**: Sure, this is just the first version, what did you have in mind?  
**C**: Well, you know, our tech took your code and said it's OK but you're missing out on some standard stuff,  *like making the implementation classes private*?  
**Y**: -scratches head- Sure, um ...  
**C**: Because, you know, they said that we need to keep our packages or something clean and...  
**Y**: Of course, of course, I'll whip it up in a sec and send you the new version over.
 
At this point in time, you're pretty mad at yourself because, hey, this is a rookie error.  
But all you really need to do is sprinkle a couple of `private` decorators around and you're done.

Unfortunately, once you open up your project, you realize that it's not that simple.

Compiled from "LegalDrinkingAgeCountryRepository.scala"
public final class hr.element.spt.lda.impl.NoAgeVerification$ implements hr.element.spt.AgeVerificator {
  public static final hr.element.spt.lda.impl.NoAgeVerification$ MODULE$;
  public static {};
  public boolean isUnderage(int);
}

r:\hr\element\spt\lda\impl>javap NoAgeVerification.class
Compiled from "LegalDrinkingAgeCountryRepository.scala"
public final class hr.element.spt.lda.impl.NoAgeVerification {
  public static boolean isUnderage(int);
}

But although the code has been written and all unit and integration tests pass, you can't quite finalize the project, because the Client's tech team starts imposing certain parts of their corporate culture and "the way they do things". You take a look at their requirements...

## Requirements #2

- Implementation should not leak any public classes nor methods
  - the only exception to the rule is the factory mentioned above (hr.element.spt.lda.LegalDrinkingAgeVerificatorFactory)

## Requirements #3

 - If your library requires a set of dependencies, they must be either
  - from a set of libaries pre-approved by the Client (in our example, [sbt-countries](https://github.com/melezov/scala-proprietary-tutorial/blob/master/0-spt-countries/src/main/java/hr/element/spt/countries/Country.java), IRL JodaTime, etc ...)
  - packaged within the library itself (the library should not have external dependencies)
