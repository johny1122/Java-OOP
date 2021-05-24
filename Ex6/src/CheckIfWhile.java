import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * class to check a line of if\while
 */
public class CheckIfWhile {

    private static final String EMPTY_STRING = "";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String OPEN_ROUND_BRACKET = "(";

    private static final String ERROR_MESSAGE_CONDITION_INPUT_INVALID = "ERROR: condition input is invalid";
    private static final String ERROR_MESSAGE_INVALID_IF_WHILE_FORMAT
            = "ERROR: if\\while line format is invalid";


    /* regex of whitespace one or more times */
    private static final String REGEX_WHITESPACE = "\\s+";

    /* regex of || or && */
    private static final String REGEX_AND_OR_OR = "\\|\\||&&";

    /* regex of if\while format */
    private static final String REGEX_IF_WHILE_FORMAT =
            "\\s*(if|while)\\s*\\(\\s*([\\w.-]+)\\s*(\\s*(\\|\\||&&)" +
                    "\\s*[\\w.-]+\\s*)*\\s*\\)\\s*\\{\\s*";

    private static final Pattern patternIfWhile = Pattern.compile(REGEX_IF_WHILE_FORMAT);


    /**
     * check a single part of the condition is valid
     * @param input: a single part of the condition
     * @param thisScope: the current scope of the if\while
     * @throws InvalidPropertiesFormatException: throws if the pars is invalid
     */
    private static void checkConditionInput(String input, Scope thisScope)
            throws InvalidPropertiesFormatException {
        if ((input.equals(TRUE)) || (input.equals(FALSE))) {
            return;
        }
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            // if it isn't a number - maybe it is a variable name
            Variable variable = thisScope.findVariableInScopes(input);
            // if variable doesnt exist OR not initialized OR not in the correct Type (boolean\double\int)
            if ((variable == null) || (!variable.isInitialized()) || ((!variable.getType().equals(BOOLEAN)) &&
                    (!variable.getType().equals(INT)) &&
                    (!variable.getType().equals(DOUBLE)))) {
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_CONDITION_INPUT_INVALID);
            }
        }
    }


    /**
     * check just if the format of the line is a match to a valid if\while line
     * @param line: given string of a line
     * @return: true if the line match the format and false otherwise
     */
    public static boolean checkJustFormat(String line) {
        Matcher matcher = patternIfWhile.matcher(line);
        return matcher.matches();
    }


    /**
     * check a line of if\while - format and logic
     * @param line: string of a possible if\while line
     * @param thisScope: the current scope the if\while is in
     * @throws InvalidPropertiesFormatException: throws if the logic or the format is wrong
     */
    public static void checkIfWhileLine(String line, Scope thisScope)
            throws InvalidPropertiesFormatException {
        Matcher matcher = patternIfWhile.matcher(line);

        if (matcher.matches()) {
            int indexOfOpenBracket = line.indexOf(OPEN_ROUND_BRACKET);
            // take only the part after "("
            String subLine = line.substring(indexOfOpenBracket + 1);
            // remove all whitespaces
            subLine = subLine.replaceAll(REGEX_WHITESPACE, EMPTY_STRING);
            // take only the part before ")"
            subLine = subLine.substring(0, subLine.length() - 2);
            // split by || or &&
            String[] splitInputs = subLine.split(REGEX_AND_OR_OR);

            for (String input : splitInputs) {
                checkConditionInput(input, thisScope);
            }
        } else {
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_INVALID_IF_WHILE_FORMAT);
        }
    }
}