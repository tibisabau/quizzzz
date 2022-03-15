package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name="generator", sequenceName = "seq", allocationSize=1)
    public long id;
    public String title;
    public String image_path;
    public long consumption_in_wh;

    public Activity(){

    }

    /**
     * Constructor method for activity
     * @param image_path
     * @param title
     * @param consumption_in_wh
     * @param source
     */
    public Activity(String image_path, String title, long consumption_in_wh){
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
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

    public long getConsumption_in_wh() {
        return consumption_in_wh;
    }


    public String toStringAnswer(){
        return this.title;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
