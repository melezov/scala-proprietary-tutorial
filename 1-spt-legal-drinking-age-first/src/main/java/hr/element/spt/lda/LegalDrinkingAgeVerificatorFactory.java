package hr.element.spt.lda;

import hr.element.spt.AgeVerificator;
import hr.element.spt.countries.Country;

import hr.element.spt.lda.impl.LegalDrinkingAgeCountryRepository;

/**
 * A singleton factory for creation of legal drinking age verificators.
 */
public enum LegalDrinkingAgeVerificatorFactory {
    INSTANCE;

    public AgeVerificator getAgeVerificator(final Country country) {
        return LegalDrinkingAgeCountryRepository.createAgeVerifier(country);
    }
}
