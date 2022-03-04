package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import commons.EntryRead;
//import java.awt.image.BufferedImage;

@Entity
public class Entry {

    @Id
    public long id;
    public String title;
    public String image_path;
    public String source;
    public int consumption_in_wh;


    public Entry(long id, String image_path, String title, int consumption_in_wh, String source){
        this.id = id;
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setConsumption_in_wh(int consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getSource() {
        return source;
    }

    public int getConsumption_in_wh() {
        return consumption_in_wh;
    }

}
