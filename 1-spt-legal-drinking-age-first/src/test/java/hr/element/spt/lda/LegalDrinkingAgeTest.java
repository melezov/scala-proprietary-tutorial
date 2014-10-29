package hr.element.spt.lda;

import junit.framework.Assert;
import hr.element.spt.AgeVerificator;
import hr.element.spt.countries.Country;

import org.junit.Test;

public class LegalDrinkingAgeTest {
	@Test
	public void testLegalityInCroatia() {
		final AgeVerificator ageVerificator =
				LegalDrinkingAgeVerificatorFactory.INSTANCE.getAgeVerificator(Country.HR);

		Assert.assertTrue(ageVerificator.isUnderage(17));
		Assert.assertFalse(ageVerificator.isUnderage(18));
	}
}
