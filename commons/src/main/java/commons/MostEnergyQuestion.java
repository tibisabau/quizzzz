package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class MostEnergyQuestion {

    private Entry1 firstOption;
    private Entry1 secondOption;
    private Entry1 thirdOption;

    public MostEnergyQuestion(){

    }

    public MostEnergyQuestion(Entry1 firstOption, Entry1 secondOption, Entry1 thirdOption) {
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
    }

    public Entry1 getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(Entry1 firstOption) {
        this.firstOption = firstOption;
    }

    public Entry1 getSecondOption() {
        return secondOption;
    }

    public void setSecondOption(Entry1 secondOption) {
        this.secondOption = secondOption;
    }

    public Entry1 getThirdOption() {
        return thirdOption;
    }

    public void setThirdOption(Entry1 thirdOption) {
        this.thirdOption = thirdOption;
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
