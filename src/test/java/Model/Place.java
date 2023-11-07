package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    private String placeName;
    private String longitude;
    private String state;
    private String stateabbreviation;
    private String latitude;


    public String getPlaceName() {
        return placeName;
    }

    @JsonProperty("place name")
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getstateabbreviation() {
        return stateabbreviation;
    }

    @JsonProperty("state abbreviation")

    public void setstateabbreviation(String stateabbreviation) {
        this.stateabbreviation = stateabbreviation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "Place{" +
                "placeName='" + placeName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", state='" + state + '\'' +
                ", stateabbreviation='" + stateabbreviation + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
