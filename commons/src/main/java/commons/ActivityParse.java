package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * This class is used to parse JSON from the provided file containing activities.
 */
public class ActivityParse {
    public String id;
    public String title;
    public String image_path;
    public String source;
    public long consumption_in_wh;

    /**
     * Constructor method for ActivityParse
     * @param id
     * @param image_path
     * @param title
     * @param consumption_in_wh
     * @param source
     */
    public ActivityParse(String id, String image_path, String title, long consumption_in_wh, String source){
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

    public void setId(String id) {
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

    public String getId() {
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
