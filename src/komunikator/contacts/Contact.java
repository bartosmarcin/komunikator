package komunikator.contacts;

import komunikator.utils.DatabaseObject;

/**
 * @author Rafał Zawadzki
 */
public class Contact implements DatabaseObject {
    private Long id;
    private String organization;
    private String availability;

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
