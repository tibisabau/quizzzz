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
     */
    public InsteadOfQuestion(Activity promptedOption,
                             Activity correctOption,
                             Activity firstOption, Activity secondOption) {
        this.promptedOption = promptedOption;
        this.correctOption = correctOption;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
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

    /**
     * Sets second option.
     *
     * @param secondOption the second option
     */
    public void setSecondOption(Activity secondOption) {
        this.secondOption = secondOption;
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
