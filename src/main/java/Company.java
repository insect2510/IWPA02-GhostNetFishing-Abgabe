import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Company
{
    private String name = "Shea Sepherd";

    private String street = "Behringstraße 69";

    private String postCode = "22763";

    private String city = "Hamburg";
    
    private String country = "Deutschland";

    public Company()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getStreet()
    {
        return street;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public String getCity()
    {
        return city;
    }

	public String getCountry() {
		return country;
	}

}
