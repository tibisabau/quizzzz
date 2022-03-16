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
    public String imagePath;
    public long consumptionInWh;

    public Activity(){}

    /**
     * Constructor method for activity
     * @param imagePath
     * @param title
     * @param consumptionInWh
     */
    public Activity(String imagePath, String title, long consumptionInWh){
        this.imagePath = imagePath;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
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

    public void setImagePath(String image_path) {
        this.imagePath = image_path;
    }

    public void setConsumptionInWh(int consumption_in_wh) {
        this.consumptionInWh = consumption_in_wh;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getConsumptionInWh() {
        return consumptionInWh;
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
