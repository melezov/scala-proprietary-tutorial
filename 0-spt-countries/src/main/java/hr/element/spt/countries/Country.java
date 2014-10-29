package hr.element.spt.countries;

/**
 * A partial ISO 3166-1 alpha-2 country enumeration.
 */
public enum Country {
    BA("Bosnia and Herzegovina"),
    CU("Cuba"),
    CY("Cyprus"),
    GB("United Kingdom"),
    HR("Croatia"),
    IS("Iceland"),
    IT("Italy"),
    JP("Japan"),
    KR("South Korea"),
    KZ("Kazakhstan"),
    LK("Sri Lanka"),
    MT("Malta"),
    NI("Nicaragua"),
    NO("Norway"),
    NZ("New Zealand"),
    PY("Paraguay"),
    RU("Russia"),
    US("United States");

    public final String countryName;

    private Country(final String countryName) {
        this.countryName = countryName;
    }
}
