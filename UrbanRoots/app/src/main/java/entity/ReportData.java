package entity;

/**
 * Created by whdinata on 11/7/15.
 */
public class ReportData {
    private String encodedImage;
    private String description;
    private String longitude;
    private String latitude;
    private String kind;

    public ReportData(String encodedImage, String description, String longitude, String latitude, String kind){
        this.encodedImage = encodedImage;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.kind = kind;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public String getDescription() {
        return description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getKind() {
        return kind;
    }
}
