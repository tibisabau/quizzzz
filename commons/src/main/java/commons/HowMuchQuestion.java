package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class HowMuchQuestion {


    private Activity firstOption;
    private Activity secondOption;
    private Activity thirdOption;
    private Activity correctOption;

    public HowMuchQuestion(){

    }

    public HowMuchQuestion(Activity firstOption, Activity secondOption, Activity thirdOption, Activity correctOption) {
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
        this.correctOption = correctOption;
    }

    public Activity getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(Activity firstOption) {
        this.firstOption = firstOption;
    }

    public Activity getSecondOption() {
        return secondOption;
    }

    public void setSecondOption(Activity secondOption) {
        this.secondOption = secondOption;
    }

    public Activity getThirdOption() {
        return thirdOption;
    }

    public void setThirdOption(Activity thirdOption) {
        this.thirdOption = thirdOption;
    }

    public Activity getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Activity correctOption) {
        this.correctOption = correctOption;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
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
