package komunikator.contacts;

import komunikator.utils.DatabaseObject;

/**
 * @author Rafał Zawadzki
 */
public class Contact extends DatabaseObject {
    private String organization;
    private String availability;

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
