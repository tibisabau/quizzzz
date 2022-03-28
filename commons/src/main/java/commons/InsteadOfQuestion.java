package commons;


import java.util.Objects;

/**
 * The type Instead of question.
 */
public class InsteadOfQuestion {

    private Activity promptedOption;

    private Activity correctOption;

    private Activity firstOption;

    private Activity secondOption;

    private Activity thirdOption;

    private String identity;

    /**
     * Instantiates a new Instead of question.
     */
    public InsteadOfQuestion(){

    }

    /**
     * Instantiates a new Instead of question.
     *
     * @param promptedOption the prompted option
     * @param correctOption  the correct option
     * @param firstOption    the first option
     * @param secondOption   the second option
     * @param thirdOption   the third option
     */
    public InsteadOfQuestion(Activity promptedOption,
                             Activity correctOption,
                             Activity firstOption, Activity secondOption,
                             Activity thirdOption) {
        this.promptedOption = promptedOption;
        this.correctOption = correctOption;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
        this.identity = "this is me";
    }

    /**
     * Gets prompted option.
     *
     * @return the prompted option
     */
    public Activity getPromptedOption() {
        return promptedOption;
    }

    /**
     * Gets identity option.
     *
     * @return the prompted option
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Sets prompted option.
     *
     * @param promptedOption the prompted option
     */
    public void setPromptedOption(Activity promptedOption) {
        this.promptedOption = promptedOption;
    }

    /**
     * Gets correct option.
     *
     * @return the correct option
     */
    public Activity getCorrectOption() {
        return correctOption;
    }

    /**
     * Sets correct option.
     *
     * @param correctOption the correct option
     */
    public void setCorrectOption(Activity correctOption) {
        this.correctOption = correctOption;
    }

    /**
     * Gets first option.
     *
     * @return the first option
     */
    public Activity getFirstOption() {
        return firstOption;
    }

    /**
     * Sets first option.
     *
     * @param firstOption the first option
     */
    public void setFirstOption(Activity firstOption) {
        this.firstOption = firstOption;
    }

    /**
     * Gets second option.
     *
     * @return the second option
     */
    public Activity getSecondOption() {
        return secondOption;
    }

    public Activity getThirdOption() {
        return thirdOption;
    }

    /**
     * Sets second option.
     *
     * @param secondOption the second option
     */
    public void setSecondOption(Activity secondOption) {
        this.secondOption = secondOption;
    }

    public void setThirdOption(Activity thirdOption) {
        this.thirdOption = thirdOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsteadOfQuestion that = (InsteadOfQuestion) o;
        return Objects.equals(promptedOption, that.promptedOption)
                && Objects.equals(correctOption, that.correctOption)
                && Objects.equals(firstOption, that.firstOption)
                && Objects.equals(secondOption, that.secondOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash
                (promptedOption, correctOption, firstOption, secondOption);
    }

    @Override
    public String toString() {
        return "InsteadOfQuestion{" +
                "promptedOption=" + promptedOption +
                ", correctOption=" + correctOption +
                ", firstOption=" + firstOption +
                ", secondOption=" + secondOption +
                '}';
    }
}
