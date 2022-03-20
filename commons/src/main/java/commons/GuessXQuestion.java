package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class GuessXQuestion {

    private Activity correctOption;

    /**
     * default constructor for ObjectMapper
     */
    public GuessXQuestion() {

    }

    /**
     * constructor for "Guess The Amount Of Energy" question type
     * @param correctOption
     */
    public GuessXQuestion(Activity correctOption) {
        this.correctOption = correctOption;
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
