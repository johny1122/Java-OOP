import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * class to check a line of variable (declaration\assign)
 */
public class CheckVariable {

    // constants
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String STRING = "String";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String COMMA = ",";

    // Error messages
    private static final String ERROR_MESSAGE_TYPE_INVALID = "ERROR: type is invalid";
    private static final String ERROR_MESSAGE_NAME_INVALID = "ERROR: name format is invalid";
    private static final String ERROR_MESSAGE_NAME_IS_NULL = "ERROR: name is null";
    private static final String ERROR_MESSAGE_NOTHING_BETWEEN_COMMAS = "ERROR: nothing between commas";
    private static final String ERROR_MESSAGE_FINAL_MUST_BE_ASSIGN = "ERROR: final must be assigned";
    private static final String ERROR_MESSAGE_INVALID_TYPE = "ERROR: invalid type";
    private static final String ERROR_MESSAGE_VALUE_DOESNT_FIT_TYPE
            = "ERROR: value doesn't fit the variable type";
    private static final String ERROR_MESSAGE_VARIABLE_NOT_FOUND_OR_FINAL
            = "ERROR: variable not found or is final";
    private static final String ERROR_MESSAGE_2_VARIABLES_IN_SCOPE_WITH_SAME_NAME =
            "ERROR: 2 variables in the scope with the same name";
    private static final String ERROR_MESSAGE_VARIABLE_NOT_FOUND_OR_NOT_INITIALIZED_OR_ILLEGAL_VALUE =
            "ERROR: variable not found or not initialized or illegal value";
    private static final String ERROR_MESSAGE_VARIABLE_LINE_DOEST_MATCH_ANY_FORMAT =
            "ERROR: variable line doesn't match any possible format";


    /* regex of whitespace one or more times */
    private static final String REGEX_WHITESPACE = "\\s+";

    /* regex of whitespace one or more times in the beginning */
    private static final String REGEX_START_WHITESPACE = "^\\s+";

    /* regex of final in the beginning */
    private static final String REGEX_START_WHITESPACE_AND_FINAL = "^\\s*final\\s*";

    /* regex of name format */
    private static final String REGEX_NAME_FORMAT = "([a-zA-Z][\\w]*)|(_[\\w]+)";

    /* regex of string value format */
    private static final String REGEX_STRING_VALUE = "\"[^\\s\"',\\\\]*\"";

    /* regex of char value format */
    private static final String REGEX_CHAR_VALUE = "'[^\\s\"',\\\\]'";

    /* regex of "name = value ;"   (a = 3;) */
    private static final String REGEX_VARIABLE_STARTS_WITH_NAME
            = "\\s*(\\w+)\\s*=\\s*([^\\s,\\\\]+)\\s*;\\s*";

    /* regex of "name = value" without the ';' in the end  (a = 3) */
    private static final String REGEX_ASSIGN_VALUE_TO_VARIABLE = "\\s*(\\w+)\\s*=\\s*([^\\s,\\\\]+)\\s*";

    /* regex of 	"type name ;" 							(int a;)
          or 		"type name = value ;"					(int a = 3;)
           or		"type name1, name2, name 3; 			(int a, b, c;)
          or		"type name1, name2 = value, name 3; 	(int a,b = 3,c;) */
    private static final String REGEX_VARIABLE_STARTS_WITH_TYPE =
            "\\s*([a-zA-Z]+)\\s+(\\w+(\\s*=\\s*[^\\s,\\\\]+)?)" +
                    "\\s*(\\s*,\\s*\\w+(\\s*=\\s*[^\\s,\\\\]+)?\\s*)*;\\s*";

    /* regex of "final type name = value ;" 					(final int a = 3;)
            or    "final type name1 = value1, name2 = value2;"	(final int a = 1, b = 2;) */
    private static final String REGEX_VARIABLE_STARTS_WITH_FINAL =
            "\\s*final\\s+([a-zA-Z]+)\\s+(\\w+(\\s*=\\s*[^\\s,\\\\]+)?)" +
                    "\\s*(\\s*,\\s*\\w+(\\s*=\\s*[^\\s,\\\\]+)?\\s*)*;\\s*";


    private static final String[] ARRAY_TYPES = new String[]{INT, DOUBLE, BOOLEAN, STRING, CHAR};
    private static final List<String> validTypesList = Arrays.asList(ARRAY_TYPES);


    private static final Pattern patternVariableStartsWithName = Pattern
            .compile(REGEX_VARIABLE_STARTS_WITH_NAME);
    private static final Pattern patternVariableStartsWithType = Pattern
            .compile(REGEX_VARIABLE_STARTS_WITH_TYPE);
    private static final Pattern patternVariableStartsWithFinal = Pattern
            .compile(REGEX_VARIABLE_STARTS_WITH_FINAL);
    private static final Pattern patternAssignValueToVariable = Pattern
            .compile(REGEX_ASSIGN_VALUE_TO_VARIABLE);


    /**
     * check the format of the Name
     * @param str: given string of name
     * @throws InvalidPropertiesFormatException throws if format is invalid
     */
    public static void checkNameFormat(String str) throws InvalidPropertiesFormatException {
        if (str.matches(REGEX_NAME_FORMAT)) {
            return;
        }
        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_NAME_INVALID);
    }


    /**
     * check if a given Value is in the right format for the given Type
     * @param type: given type
     * @param value: given value
     * @throws NumberFormatException: throws if value isn't int\double
     * @throws InvalidPropertiesFormatException: throws if one of the value doesn't match the other types
     */
    public static void checkValueAndTypeFormat(String type, String value)
            throws NumberFormatException, InvalidPropertiesFormatException {

        switch (type) {
            case INT:
                Integer.parseInt(value);
                return;

            case DOUBLE:
                Double.parseDouble(value);
                return;

            case STRING:
                if (value.matches(REGEX_STRING_VALUE)) {
                    return;
                }
                break;

            case BOOLEAN:
                if (value.equals(TRUE) || value.equals(FALSE)) {
                    return;
                }
                Double.parseDouble(value);
                return;

            case CHAR:
                if (value.matches(REGEX_CHAR_VALUE)) {
                    return;
                }
                break;
        }
        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_VALUE_DOESNT_FIT_TYPE);
    }


    /**
     * check if the given string is a valid Type to assign into variable with assignType type
     * @param assignType: type of variable tried to assign with
     * @param varType: type of the variable to assign into
     * @throws InvalidPropertiesFormatException: throws if the types dont allow the given type
     */
    private static void checkAssignType(String assignType, String varType)
            throws InvalidPropertiesFormatException {
        // check if the found assigned type is valid to assign to the given type
        switch (varType) {
            case DOUBLE:
                if (assignType.equals(DOUBLE) || assignType.equals(INT)) {
                    return;
                }
                break;

            case BOOLEAN:
                if (assignType.equals(BOOLEAN) || assignType.equals(INT) || assignType.equals(DOUBLE)) {
                    return;
                }
                break;

            case INT:
                if (assignType.equals(INT)) {
                    return;
                }
                break;

            case STRING:
                if (assignType.equals(STRING)) {
                    return;
                }
                break;

            case CHAR:
                if (assignType.equals(CHAR)) {
                    return;
                }
                break;
        }
        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_TYPE_INVALID);
    }


    /**
     * check the value - format and logical
     * @param type: type of the variable to assign into
     * @param value: value to assign with
     * @param thisScope: the current scope of the variable
     * @throws InvalidPropertiesFormatException: throws if the value assign is invalid
     */
    private static void checkValue(String type, String value, Scope thisScope)
            throws InvalidPropertiesFormatException {
        try {
            checkValueAndTypeFormat(type, value);
        }
        // value is not a legal value - so check if it is another variable name
        catch (NumberFormatException | InvalidPropertiesFormatException e) {
            Variable variableWithValueName = thisScope.findVariableInScopes(value);

            // check if the found variable exist and is initialized
            if (variableWithValueName != null && variableWithValueName.isInitialized()) {
                // if so - check if it's type is valid to the given type
                checkAssignType(variableWithValueName.getType(), type);
            } else { // if not found OR not initialized OR illegal value - throw
                // exception
                throw new InvalidPropertiesFormatException(
                        ERROR_MESSAGE_VARIABLE_NOT_FOUND_OR_NOT_INITIALIZED_OR_ILLEGAL_VALUE);
            }
        }
    }


    /**
     * check the name - format and logical
     * @param name: string of name to check
     * @param thisScope: the current scope
     * @throws InvalidPropertiesFormatException: throws if the name is invalid
     */
    private static void checkName(String name, Scope thisScope)
            throws InvalidPropertiesFormatException {
        checkNameFormat(name);
        // 2 variables with the same name in the same scope
        if (thisScope.getScopeVariables().containsKey(name)) {
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_2_VARIABLES_IN_SCOPE_WITH_SAME_NAME);
        }
    }


    /**
     * check the assign
     * @param name: name of variable
     * @param value: value to assign
     * @param type: type of the variable
     * @param thisScope: the current scope
     * @throws InvalidPropertiesFormatException: throws if the assign is invalid
     */
    private static void checkAssign(String name, String value, String type, Scope thisScope)
            throws InvalidPropertiesFormatException {
        if (name != null) {
            checkName(name, thisScope);
        } else {
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_NAME_IS_NULL);
        }
        checkValue(type, value, thisScope);
    }


    /**
     * check each variable in a line declaration - which starts with "final"
     * @param type: type declaration
     * @param thisScope: the current scope
     * @param splitByComma: string array of all the variables declarations
     * @param addVariableToScope: boolean if the variables need to be added to the scope or not
     * @throws InvalidPropertiesFormatException: throws if the declaration is invalid
     */
    private static void checkEachVarInDeclarationFinal(String type, Scope thisScope,
                                                       String[] splitByComma, boolean addVariableToScope)
            throws InvalidPropertiesFormatException {
        String name, value;
        Variable newVariable;
        Matcher matcher;
        for (String part : splitByComma) {
            if (part == null) {
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_NOTHING_BETWEEN_COMMAS);
            }

            matcher = patternAssignValueToVariable.matcher(part);
            if (matcher.matches()) { // with assign
                name = matcher.group(1);
                value = matcher.group(2);

                checkAssign(name, value, type, thisScope);
                if (thisScope.getParentScope() == null){ // global scope
                    newVariable = new Variable(type, name, true, true, true);
                }
                else{ // not global scope
                    newVariable = new Variable(type, name, true, true, false);
                }

            } else { // final must be assign in declaration
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_FINAL_MUST_BE_ASSIGN);
            }

            // if all good and need to add - add the new variable to this scope
            if (addVariableToScope) {
                thisScope.getScopeVariables().put(name, newVariable);
            }
        }
    }


    /**
     * check each variable in a line declaration - which doesnt starts with "final"
     * @param type: type declaration
     * @param thisScope: the current scope
     * @param splitByComma: string array of all the variables declarations
     * @param addVariableToScope: boolean if the variables need to be added to the scope or not
     * @throws InvalidPropertiesFormatException: throws if the declaration is invalid
     */
    private static void checkEachVarInDeclaration(String type, Scope thisScope,
                                                  String[] splitByComma, boolean addVariableToScope)
            throws InvalidPropertiesFormatException {
        String name, value = null;
        Variable newVariable;
        Matcher matcher;
        for (String part : splitByComma) {
            if (part == null) {
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_NOTHING_BETWEEN_COMMAS);
            }

            matcher = patternAssignValueToVariable.matcher(part);
            if (matcher.matches()) { // with assign
                name = matcher.group(1);
                value = matcher.group(2);

                checkAssign(name, value, type, thisScope);
                if (thisScope.getParentScope() == null){ // global scope
                    newVariable = new Variable(type, name, true, false, true);
                }
                else { // not global scope
                    newVariable = new Variable(type, name, true, false, false);
                }

            } else { // without assign
                name = part;
                checkName(name, thisScope);
                checkType(type);
                newVariable = new Variable(type, name, false, false, false);

            }

            // if all good and need to add - add the new variable to this scope
            if (addVariableToScope) {
                thisScope.getScopeVariables().put(name, newVariable);
            }
        }
    }


    /**
     * split by commas a given declaration line to the different variables parts assume there is nothing
     * before the type in the line
     * @param subLine: given line
     * @return string array of the different parts separated by commas
     */
    private static String[] splitVariablesByComma(String subLine) {
        // find first space (must be after type)
        int indexOfFirstSpaceAfterType = subLine.indexOf(SPACE);
        // take only the part of: "a" \ "a - 3" \ "a, b, c" \ "a, b = 3, c" (remove type part)
        subLine = subLine.substring(indexOfFirstSpaceAfterType + 1, subLine.lastIndexOf(";"));

        // remove all spaces
        subLine = subLine.replaceAll(REGEX_WHITESPACE, EMPTY_STRING);
        // split by ','
        return subLine.split(COMMA);
    }


    /**
     * check if the type is one of the valid types
     * @param type: given string to check
     * @throws InvalidPropertiesFormatException: throws if it's not one of the possible types
     */
    private static void checkType(String type) throws InvalidPropertiesFormatException {
        if (validTypesList.contains(type)){
            return;
        }
        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_INVALID_TYPE);
    }

    /**
     * check just if the format of the line is a match to a valid variable possible line
     * @param line: given string of a line
     * @return: true if the line match one of the possible formats and false otherwise
     */
    public static boolean checkJustFormat(String line) {
        Matcher matcher = patternVariableStartsWithName.matcher(line);
        if (matcher.matches()) {
            return true;
        }
        matcher = patternVariableStartsWithType.matcher(line);
        if (matcher.matches()) {
            return true;
        }
        matcher = patternVariableStartsWithFinal.matcher(line);
        return matcher.matches();
    }


    /**
     * check a line manipulating variables (declaration/assign)
     * @param line: given string of a line
     * @param thisScope: the current scope of the line
     * @param addVariableToScope: boolean if the variables need to be added to the scope or not
     * @throws InvalidPropertiesFormatException: throws if something went wrong
     * @throws NumberFormatException: throws if number assign went wrong
     */
    public static void checkVariableLine(String line, Scope thisScope, boolean addVariableToScope)
            throws InvalidPropertiesFormatException, NumberFormatException {

        // Variable starts with Name - (a = 3;)
        Matcher matcher = patternVariableStartsWithName.matcher(line);
        if (matcher.matches()) {
            String name = matcher.group(1);
            String value = matcher.group(2);

            checkNameFormat(name);
            Variable variableWithGivenName = thisScope.findVariableInScopes(name);

            // if a variable with such name exist and it's not final
            if ((variableWithGivenName != null) && (!variableWithGivenName.isFinal())) {
                checkValue(variableWithGivenName.getType(), value, thisScope);
            } else {
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_VARIABLE_NOT_FOUND_OR_FINAL);
            }
            variableWithGivenName.setInitialized();
            if (thisScope.getParentScope() == null){ // global scope
                variableWithGivenName.setIsGlobalInitialized();
            }
            return;
        }

        // Variable starts with Type - (int a;) or (int a = 3;) or (int a, b, c;) or (int a,b = 3,c;)
        matcher = patternVariableStartsWithType.matcher(line);
        if (matcher.matches()) {
            String type = matcher.group(1);

            // remove all beginning whitespaces
            String subLine = line.replaceAll(REGEX_START_WHITESPACE, EMPTY_STRING);
            // split by comma
            String[] splitByComma = splitVariablesByComma(subLine);

            checkEachVarInDeclaration(type, thisScope, splitByComma, addVariableToScope);
            return;
        }


        // Variable starts with final - (final int a;)
        matcher = patternVariableStartsWithFinal.matcher(line);
        if (matcher.matches()) {
            String type = matcher.group(1);

            // remove all beginning whitespaces and final
            String subLine = line.replaceAll(REGEX_START_WHITESPACE_AND_FINAL, EMPTY_STRING);
            // split by comma
            String[] splitByComma = splitVariablesByComma(subLine);

            checkEachVarInDeclarationFinal(type, thisScope, splitByComma, addVariableToScope);
            return;
        }

        // line doesn't match any of the possible variable-line formats
        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_VARIABLE_LINE_DOEST_MATCH_ANY_FORMAT);
    }

}