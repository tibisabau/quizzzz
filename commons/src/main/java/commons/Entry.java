package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
//import java.awt.image.BufferedImage;

@Entity
public class Entry {

    @Id
    public long id;

    public String description;
    public int answer;
   // public BufferedImage image;

    public Entry(long id, String description, int answer/**, BufferedImage image**/){
        this.id = id;
        this.description = description;
        this.answer = answer;
       // this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
