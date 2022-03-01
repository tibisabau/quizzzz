package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entry {

    @Id
    public long id;

    public String description;
    public int answer;

    public Entry(String description, int answer, long id){
        this.description = description;
        this.answer = answer;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
