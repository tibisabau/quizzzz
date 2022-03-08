package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;


@Entity
public class Entry1 {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name="generator", sequenceName = "seq", allocationSize=1)



    public long id;
    public String title;
    public String image_path;
    public String source;
    public long consumption_in_wh;

    public Entry1(){

    }

    public Entry1(long id, String image_path, String title, long consumption_in_wh, String source){
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

    public long getConsumption_in_wh() {
        return consumption_in_wh;
    }

}