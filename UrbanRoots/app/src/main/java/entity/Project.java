package entity;

import org.json.JSONObject;

/**
 * Created by whdinata on 11/7/15.
 */
public class Project {
    private String description;
    private String image;
    private String num_people;
    private String address;
    private String lat;
    private String lng;
    private String id;
    private String name;

    public Project(JSONObject object){
        try {
            this.description = object.getString("description");
            this.image = object.getString("image");
            this.num_people = object.getString("num_people");
            this.address = object.getString("address");
            this.lat = object.getString("lat");
            this.lng = object.getString("lng");
            this.id = object.getString("id");
            this.name = object.getString("name");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getNum_people() {
        return num_people;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
