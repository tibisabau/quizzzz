package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
//import java.awt.image.BufferedImage;

/**
 * The type Entry.
 */
@Entity
public class Entry {

    /**
     * The Id.
     */
    @Id
    public long id;

    /**
     * The Description.
     */
    public String description;
    /**
     * The Answer.
     */
    public int answer;
   // public BufferedImage image;

    /**
     * Instantiates a new Entry.
     *
     * @param id          the id
     * @param description the description
     * @param answer      the answer
     */
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
