import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Stack;

/**
 * Checking the global scope and the methods scopes thoroughly
 */
public class CheckScope {
    private static final String ERROR_MESSAGE_GLOBAL = "ERROR: Global scope must contain methods and variable " +
            "declarations\\assignments only";
    private static final String ERROR_MESSAGE_NESTED_METHOD = "ERROR: Methods may only be declared at the global scope";
    private static final String OPEN_CURLY_BRACKET = "{";
    private static final String CLOSE_CURLY_BRACKET = "}";
    private static final String NEW_LINE = "\n";
    private static final String REGEX_EMPTY_LINE = "^\\s*$";
    private static final String REGEX_BRACKETS_DELIMITER = "(?<=[{}])|(?=[{}])";

    private static String[] methodParameterLines = null;
    private static HashMap<String, Method> methods;

    /**
     * Checking the scopes starting with the global scope and than the methods
     * @param code
     * @throws InvalidPropertiesFormatException
     */
    public static void checkScopes(String code) throws InvalidPropertiesFormatException {
        methods = CheckMethod.findMethods(code);
        String globalScopeCode = getGlobalScopeCode(code);
        Scope globalScope = checkGlobalScopeLines(globalScopeCode);

        checkMethods(globalScope);
    }

    /**
     * Getting the global scope code by deleting the methods from it
     * @param code
     * @return
     */
    private static String getGlobalScopeCode(String code){
        for(Method method: methods.values()){
            code = code.replace(method.getCode(), "");
        }

        return code;
    }

    /**
     * Checking the methods scope (and any scope beneath them)
     * @param globalScope
     * @throws InvalidPropertiesFormatException
     */
    private static void checkMethods(Scope globalScope) throws InvalidPropertiesFormatException {
        methodParameterLines = null;

        for(Method method: methods.values()){
            String[] splitCode = method.getCode().split(REGEX_BRACKETS_DELIMITER);
            scopify(splitCode, globalScope);
            setUninitializedGlobal(globalScope);
        }
    }

    /**
     * After checking each method we need to set the globally uninitialized global variables for the next method
     * @param globalScope
     */
    private static void setUninitializedGlobal(Scope globalScope){
        for(Variable variable: globalScope.getScopeVariables().values()){
            if(!variable.isGlobalInitialized()){
                variable.setUnInitialized();
            }
        }
    }

    /**
     * Going over the scopes inside the method and checking all lines validity and updating variables
     * @param splitCode
     * @param globalScope
     * @throws InvalidPropertiesFormatException
     */
    private static void scopify(String[] splitCode, Scope globalScope) throws InvalidPropertiesFormatException {
        Stack<Scope> scopeStack = new Stack<>();
        scopeStack.push(globalScope);
        String prevBracket = OPEN_CURLY_BRACKET;
        int currIndex = 0;
        Scope newScope;

        while(currIndex < splitCode.length){
            String currCode = splitCode[currIndex];

            if(isBracket(currCode)){
                prevBracket = currCode;
            }
            else{
                if(prevBracket.equals(OPEN_CURLY_BRACKET)){
                    newScope = new Scope(scopeStack.peek());
                    checkMethodLines(currCode + splitCode[currIndex + 1], newScope, globalScope);
                    scopeStack.push(newScope);
                }
                else{
                    scopeStack.pop();
                    Scope currScope = scopeStack.peek();

                    if(currScope != globalScope && currIndex + 1 < splitCode.length) {
                        checkMethodLines(currCode + splitCode[currIndex + 1], currScope, globalScope);
                    }
                }
            }

            currIndex++;
        }
    }

    /**
     * Is bracket "}" or "{"
     * @param str
     * @return
     */
    private static boolean isBracket(String str){
        return str.equals(OPEN_CURLY_BRACKET) || str.equals(CLOSE_CURLY_BRACKET);
    }

    /**
     * Checking the global scope - must have variable related code only (after removing the methods code)
     * and updating the global variables
     * @param code
     * @return
     * @throws InvalidPropertiesFormatException
     */
    private static Scope checkGlobalScopeLines(String code) throws InvalidPropertiesFormatException {
        String[] lines = code.split("\n");
        Scope globalScope = new Scope(null);

        for(String line: lines) {
            if(line.matches(REGEX_EMPTY_LINE)){
                continue;
            }
            else if(CheckVariable.checkJustFormat(line)){
                CheckVariable.checkVariableLine(line, globalScope, true);
            }
            else{
                throw new InvalidPropertiesFormatException(ERROR_MESSAGE_GLOBAL);
            }
        }

        return globalScope;
    }

    /**
     * Given a scope's portion of a code, we check each line for more advanced errors (not syntax)
     * @param code
     * @param scope
     * @param globalScope
     * @throws InvalidPropertiesFormatException
     */
    private static void checkMethodLines(String code, Scope scope, Scope globalScope)
            throws InvalidPropertiesFormatException {
        String[] lines = code.split(NEW_LINE);

        if(methodParameterLines != null){
            for(String line: methodParameterLines) {
                CheckVariable.checkVariableLine(line, scope, true);
            }

            for(Variable variable: scope.getScopeVariables().values()){
                variable.setInitialized();
            }

            methodParameterLines = null;
        }

        for(String line: lines) {
            if(line.matches(CheckMethod.REGEX_RETURN) ||
                    line.matches(CheckMethod.REGEX_CLOSE_CURLY_BRACKET) ||
                    line.matches(REGEX_EMPTY_LINE)){
                continue;
            }
            else if(CheckIfWhile.checkJustFormat(line)){
                CheckIfWhile.checkIfWhileLine(line, scope);
            }
            else if(CheckMethod.isMethodSignatureLayout(line)){
                if(scope.getParentScope() == globalScope) // Methods only on global scope
                {
                    methodParameterLines = CheckMethod.getParameterVariablesLines(line);
                }
                else{
                    throw new InvalidPropertiesFormatException(ERROR_MESSAGE_NESTED_METHOD);
                }
            }
            else if(CheckMethodCall.isValidMethodCallLayout(line)){
                CheckMethodCall.checkMethodCall(CheckMethodCall.getMethodCall(line), methods, scope);
            }
            else {
                CheckVariable.checkVariableLine(line, scope, true);
            }
        }
    }
}
