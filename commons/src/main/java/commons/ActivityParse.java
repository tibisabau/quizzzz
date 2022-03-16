package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * This class is used to parse JSON from
 * the provided file containing activities.
 */
public class ActivityParse {
    public String id;

    public String title;

    public String imagePath;

    public String source;

    public long consumptionInWh;

    /**
     * Constructor method for ActivityParse
     * @param id
     * @param imagePath
     * @param title
     * @param consumptionInWh
     * @param source
     */
    public ActivityParse(String id, String imagePath, String title,
                         long consumptionInWh, String source){
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.
                reflectionEquals(this, obj);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String image_path) {
        this.imagePath = image_path;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setConsumptionInWh(int consumption_in_wh) {
        this.consumptionInWh = consumption_in_wh;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSource() {
        return source;
    }

    public long getConsumptionInWh() {
        return consumptionInWh;
    }
}
