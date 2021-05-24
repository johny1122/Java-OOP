import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Stack;
import java.util.regex.Pattern;

public class Sjavac {
    private static String LEGAL = "0";
    private static String ILLEGAL = "1";
    private static String IO_ERROR = "2";
    private static final String REGEX_LEGAL_COMMENT = "((^\\/\\/[\\s\\S]*?)|((?<=(\\n))\\/\\/[\\s\\S]*?))\n";
    private static final String OPEN_CURLY_BRACKET = "{";
    private static final String CLOSE_CURLY_BRACKET = "}";
    private static final String ERROR_MESSAGE_METHOD = "ERROR: Invalid method declaration, must be 'void methodName" +
            "([type param[, type param...]]){'";
    private static final String ERROR_MESSAGE_METHOD_CALL = "ERROR: Invalid method call, must be " +
            "'methodName([param1 [,param2...]]);'";
    private static final String ERROR_MESSAGE_INVALID_CODE_LINE = "ERROR: Invalid syntax or not supported by Sjava";
    private static final String ERROR_MESSAGE_IMBALANCED_BRACKETS = "ERROR: Missing a bracket ('{','}','(',')')";
    private static final String ERROR_MESSAGE_CONSECUTIVE_BRACKETS = "ERROR: Cannot have consecutive brackets of '{,}'";
    private static final String ERROR_MESSAGE_BRACKET_START = "ERROR: Code cannot start with a bracket";
    private static final String NEW_LINE = "\n";
    private static String code;

    /**
     * The main method to run the procedure and catch errors
     * @param args
     */
    public static void main(String[] args){
        try {
            code = removeLegalComments(new String(Files.readAllBytes(Paths.get(args[0]))));
        }
        catch (IOException ignored){
            System.out.println(IO_ERROR);
            return;
        }

        try {
            checkLines();
            CheckScope.checkScopes(code);
        }
        catch (InvalidPropertiesFormatException e){
            System.err.println(e.getMessage());
            System.out.println(ILLEGAL);
            return;
        }

        System.out.println(LEGAL);
    }

    /**
     * Removing legal comments using a regex
     * @param code
     * @return
     */
    private static String removeLegalComments(String code){
        return code.replaceAll(REGEX_LEGAL_COMMENT, "");
    }

    /**
     * Checking for syntax errors of Sjava or unfamiliar code to the language
     * @throws InvalidPropertiesFormatException
     */
    public static void checkLines() throws InvalidPropertiesFormatException {
        if(Pattern.compile("[{}][{}]").matcher(code).find()){
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_CONSECUTIVE_BRACKETS);
        }
        if(code.charAt(0) == OPEN_CURLY_BRACKET.charAt(0) || code.charAt(0) == CLOSE_CURLY_BRACKET.charAt(0)){
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_BRACKET_START);
        }

        if(!isBalancedBrackets(code.replaceAll("[^{}()]", ""))){
            throw new InvalidPropertiesFormatException(ERROR_MESSAGE_IMBALANCED_BRACKETS);
        }

        for(String line: code.split(NEW_LINE)){
            if(line.matches("^[\\s]*$")){ // EMPTY LINE
                continue;
            }
            else if(line.matches("^[\\s\\S]*[{};]\\s*")){ // CODE LINE
                if(CheckMethod.isMethodSignatureLayout(line)){
                    if(!CheckMethod.isValidMethodSignature(line)) {
                        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD);
                    }
                }
                else if(CheckMethodCall.isValidMethodCallLayout(line)){
                    if(!CheckMethodCall.isValidMethodCall(line)) {
                        throw new InvalidPropertiesFormatException(ERROR_MESSAGE_METHOD_CALL);
                    }
                }
                else if(CheckIfWhile.checkJustFormat(line)){
                    continue;
                }
                else if(line.matches(CheckMethod.REGEX_RETURN) || line.matches(CheckMethod.REGEX_CLOSE_CURLY_BRACKET)){
                    continue;
                }
               else if(!CheckVariable.checkJustFormat(line)){
                    throw new InvalidPropertiesFormatException(ERROR_MESSAGE_INVALID_CODE_LINE);
                }
            }
            else{
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_INVALID_CODE_LINE);
            }
        }
    }

    /**
     * Checking the brackets in the code are balanced given the brackets only
     * @param brackets
     * @return
     */
    private static boolean isBalancedBrackets(String brackets){
        Stack<Character> bracketsStack = new Stack<>();
        for(int i = 0; i < brackets.length(); i++){
            char currChar = brackets.charAt(i);
            if(currChar == '{' || currChar == '('){
                bracketsStack.push(currChar);
            }
            else if(!bracketsStack.isEmpty()){
                if(currChar == '}' && bracketsStack.pop() != '{'){
                    return false;
                }
                else if(currChar == ')' && bracketsStack.pop() != '('){
                    return false;
                }
            }
            else{
                return false;
            }
        }

        return bracketsStack.isEmpty();
    }
}
