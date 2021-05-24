import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checking the method call for parameters type and quantity
 */
public class CheckMethodCall {
    private static final String FINAL_VAR_PREFIX = "final ";
    private static final String ERROR_MESSAGE_METHOD_CALL_ARG_NUM = "ERROR: Method calls must provide the " +
            "exact number of arguments declared by the method";
    private static final String ERROR_MESSAGE_METHOD_CALL_NO_METHOD = "ERROR: Method call is calling an " +
            "unfamiliar method";
    private static final String ERROR_MESSAGE_METHOD_CALL_INCOMPATIBLE = "ERROR: Method calls must provide variables " +
            "or values of the right type";
    private static final String ERROR_MESSAGE_METHOD_CALL_VAR_UNINITIALIZED = "ERROR: Method call with uninitialized " +
            "variable";
    private static String REGEX_METHOD_CALL = "\\s*([a-zA-Z][\\w]*)\\s*\\(\\s*" +
            "([^\\s,]+\\s*(\\s*,\\s*[^\\s,]+\\s*)*)*\\)\\s*;\\s*";
    private static String REGEX_METHOD_CALL_LAYOUT = "\\s*[\\S]+\\s*\\([\\s\\S]*\\)\\s*;\\s*";
    private static final String COMMA = ",";

    /**
     * Checking the structure of a given line, deciding whether it's a method call
     * @param line
     * @return
     */
    public static boolean isValidMethodCall(String line){
        return line.matches(REGEX_METHOD_CALL);
    }

    /**
     * Checking the structure of a given line, deciding whether it's a method call layout
     * @param line
     * @return
     */
    public static boolean isValidMethodCallLayout(String line){
        return line.matches(REGEX_METHOD_CALL_LAYOUT);
    }

    /**
     * Creating a MethodCall object out of a valid method call line
     * @param methodCallLine
     * @return
     */
    public static MethodCall getMethodCall(String methodCallLine){
        Matcher methodCallMatcher = Pattern.compile(REGEX_METHOD_CALL).matcher(methodCallLine);

        methodCallMatcher.find();
        String methodName = methodCallMatcher.group(1);
        String values = methodCallMatcher.group(2);
        String[] valuesArr = new String[0];

        if(values != null && !values.isEmpty()){
            if(values.contains(COMMA)){
                valuesArr = values.split(COMMA);
            }
            else{
                valuesArr = new String[1];
                valuesArr[0] = values;
            }
        }

        return new MethodCall(methodName, valuesArr);
    }

    /**
     * Checking that the call is for an actual existing method, with valid parameters of correct types and quantity
     * @param methodCall
     * @param methods
     * @param scope
     * @throws InvalidPropertiesFormatException
     */
    public static void checkMethodCall(MethodCall methodCall, HashMap<String, Method> methods, Scope scope)
    throws InvalidPropertiesFormatException {
        if(methods.containsKey(methodCall.getName())){
            Method currMethod = methods.get(methodCall.getName());

            Variable[] methodVars = currMethod.getVariables();
            String[] methodCallValues = methodCall.getValues();

            if(methodVars.length == methodCallValues.length){
                for(int i = 0; i < methodCallValues.length; i++){
                    Variable methodCallVariable = scope.findVariableInScopes(methodCallValues[i]);
                    if(methodCallVariable != null){
                        if(!methodCallVariable.getType().equals(methodVars[i].getType())){
                            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL_INCOMPATIBLE);
                        }
                        if(!methodCallVariable.isInitialized()){
                            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL_VAR_UNINITIALIZED);
                        }
                    }
                    else {
                        Variable currVar = methodVars[i]; // CHECK IF VAR AND COMPARE TYPES
                        String assignmentLine = "";

                        if (currVar.isFinal()) {
                            assignmentLine = FINAL_VAR_PREFIX;
                        }

                        assignmentLine += currVar.getType() + " " + currVar.getName() + " = " + methodCallValues[i] +
                                ";";
                        try{
                            CheckVariable.checkVariableLine(assignmentLine, scope, false);
                        }
                        catch (InvalidPropertiesFormatException ignored){
                            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL_INCOMPATIBLE);
                        }

                    }
                }
            }
            else{
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL_ARG_NUM);
            }
        }
        else{
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL_NO_METHOD);
        }
    }
}
