import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checking the method code validity
 */
public class CheckMethod {
    private static final String ERROR_MESSAGE_METHOD_DUPLICATE = "ERROR: Methods must have different names";
    private static final String ERROR_MESSAGE_METHOD_RETURN = "ERROR: Methods must end with return; line " +
            "followed by a closing curly bracket line";
    private static final String OPEN_CURLY_BRACKET = "{";
    private static final String NEW_LINE = "\n";
    static final String REGEX_CLOSE_CURLY_BRACKET = "^\\s*\\}\\s*$";
    static final String REGEX_OPEN_CURLY_BRACKET_END = "^[\\s\\S]*\\{\\s*$";
    static final String REGEX_SIGNATURE_FORMAT = "^\\s*\\bvoid\\b\\s+[a-zA-Z][\\w]*\\s*\\(\\s*(((\\s*final\\s+)" +
        "?(String|boolean|char|int|double)\\s+(([a-zA-Z][\\w]*)|(_[\\w]+))\\s*(\\)|(\\s*,\\s*(\\s*final\\s+)?" +
        "(String|boolean|char|int|double)\\s+(([a-zA-Z][\\w]*)|(_[\\w]+))\\s*)*\\s*\\)))|(\\s*\\)\\s*))\\s*\\{\\s*$";
    static final String REGEX_SIGNATURE_FIND = "\\bvoid\\b\\s+[a-zA-Z][\\w]*\\s*\\(\\s*(((\\s*final\\s+)" +
            "?(String|boolean|char|int|double)\\s+(([a-zA-Z][\\w]*)|(_[\\w]+))\\s*(\\)|(\\s*,\\s*(\\s*final\\s+)?" +
            "(String|boolean|char|int|double)\\s+(([a-zA-Z][\\w]*)|(_[\\w]+))\\s*)*\\s*\\)))|(\\s*\\)\\s*))\\s*\\{\\s*";
    static final String REGEX_SIGNATURE_LAYOUT = "\\s*\\S+\\s+\\S+\\s*\\([\\s\\S]*\\)\\s*\\{\\s*";
    static final String REGEX_PARAMETER = "(final\\s+)?(String|boolean|char|int|double)\\s+" +
            "(([a-zA-Z][\\w]*)|(_[\\w]+))";
    static final String REGEX_METHOD_NAME = "void\\b\\s+([a-zA-Z][\\w]*)\\s*\\(";
    static final String REGEX_RETURN = "^\\s*return\\s*;\\s*$";
    static final int FINAL_PARAM_FIELDS = 3;

    private static String lastMethodCode;

    /**
     * Checking validity of method's signature by regex
     * @param line
     * @return
     */
    public static boolean isValidMethodSignature(String line){
        return line.matches(REGEX_SIGNATURE_FORMAT);
    }

    /**
     * Checking for a method's signature by regex (not strict)
     * @param line
     * @return
     */
    public static boolean isMethodSignatureLayout(String line){
        return line.matches(REGEX_SIGNATURE_LAYOUT);
    }

    /**
     * Creating a String array out of the method's signature line to hold the parameters
     * @param line
     * @return
     */
    public static String[] getParameterVariablesLines(String line){
        Matcher m = Pattern.compile(REGEX_PARAMETER).matcher(line);
        String variables = "";

        while (m.find()) {
            variables += m.group() + ";" + NEW_LINE;
        }

        if(variables.isEmpty()){
            return null;
        }

        return variables.substring(0, variables.lastIndexOf(NEW_LINE)).split(NEW_LINE);
    }

    /**
     * Creating a Variable array out of the method's signature line to hold the parameters
     * @param line
     * @return
     */
    public static Variable[] getParameterVariables(String line){
        Matcher m = Pattern.compile(REGEX_PARAMETER).matcher(line);
        LinkedList<Variable> varLines = new LinkedList<>();

        while (m.find()) {
            String parameter = m.group();
            String[] parameterFields = parameter.split("\\s+");

            boolean isFinal = false;
            String varName = parameterFields[1];
            String varType = parameterFields[0];

            if(parameterFields.length == FINAL_PARAM_FIELDS){
                isFinal = true;
                varName = parameterFields[2];
                varType = parameterFields[1];
            }

            varLines.add(new Variable(varType, varName, true, isFinal, false));
        }

        return varLines.toArray(new Variable[varLines.size()]);
    }

    /**
     * Finding methods in the code and checking for duplicates/no return at the end etc
     * @param code
     * @return
     * @throws InvalidPropertiesFormatException
     */
    public static HashMap<String, Method> findMethods(String code) throws InvalidPropertiesFormatException {
        HashMap<String, Method> methods = new HashMap<>();
        Matcher methodMatcher = Pattern.compile(REGEX_SIGNATURE_FIND).matcher(code);

        while (methodMatcher.find()) {
            String currMethod = methodMatcher.group();
            if(isReturnEnd(code, currMethod, methodMatcher.end())) {
                Variable[] methodParams = getParameterVariables(currMethod);

                Matcher methodNameMatcher = Pattern.compile(REGEX_METHOD_NAME).matcher(currMethod);
                methodNameMatcher.find();
                String methodName = methodNameMatcher.group(1);

                if (methods.containsKey(methodName)) {
                    throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_DUPLICATE);
                } else {
                    methods.put(methodName, new Method(methodName, methodParams, lastMethodCode));
                }
            }
            else{
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_RETURN);
            }
        }

        return methods;
    }

    /**
     * Checking that there is a return statement at the end of the method's code
     * (Also getting the method's code along the search)
     * @param code
     * @param signature
     * @param startIndex
     * @return
     */
    public static boolean isReturnEnd(String code,String signature, int startIndex){
        Stack<String> stack = new Stack<>();
        stack.push(OPEN_CURLY_BRACKET);
        String[] lines = code.substring(startIndex).split(NEW_LINE);
        String prevCodeLine = "";
        int currIndex = 0;
        String methodCode = signature;

        while(!stack.isEmpty()){
            if(lines[currIndex].matches(REGEX_OPEN_CURLY_BRACKET_END)){
                stack.push(OPEN_CURLY_BRACKET);
            }
            else if(lines[currIndex].matches(REGEX_CLOSE_CURLY_BRACKET)){
                stack.pop();

                if(!stack.isEmpty()){
                    prevCodeLine = lines[currIndex];
                }
            }
            else{
                prevCodeLine = lines[currIndex];
            }

            methodCode += lines[currIndex] + NEW_LINE;
            currIndex++;
        }

        lastMethodCode = methodCode.substring(0, methodCode.lastIndexOf(NEW_LINE));
        return prevCodeLine.matches(REGEX_RETURN);
    }
}
