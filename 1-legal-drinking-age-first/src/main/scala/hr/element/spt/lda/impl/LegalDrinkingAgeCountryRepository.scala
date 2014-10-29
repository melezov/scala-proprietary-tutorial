package hr.element.spt
package lda
package impl

import countries.Country

private object NoAgeVerification
    extends AgeVerificator {
  def isUnderage(age: Int) = false
}

private class ThresholdAgeVerification(threshold: Int)
    extends AgeVerificator {
  def isUnderage(age: Int) = age < threshold
}

/** A mock implementation of a countries legal drinking age limits,
  * for the purposes of a reasonably complete implementation.
  * It goes without saying that IANAL, TINLA ;) */
object LegalDrinkingAgeCountryRepository {
  def createAgeVerifier(country: Country) =
    country match {
      case Country.CU | Country.NO =>
        NoAgeVerification

      case Country.BA | Country.IT =>
        new ThresholdAgeVerification(16)

      case Country.CY | Country.MT =>
        new ThresholdAgeVerification(17)

      case Country.GB | Country.HR | Country.NZ | Country.RU =>
        new ThresholdAgeVerification(18)

      case Country.KR | Country.NI =>
        new ThresholdAgeVerification(19)

      case Country.IS | Country.JP | Country.PY =>
        new ThresholdAgeVerification(20)

      case Country.KZ | Country.LK | Country.US =>
        new ThresholdAgeVerification(21)
    }
}
