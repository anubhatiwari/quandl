package quandl.utils;

public enum Browser
{
    CHROME ("Chrome"),
    FIREFOX ("Firefox"),
    IE ("IE");

    private String name;

    private Browser(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
