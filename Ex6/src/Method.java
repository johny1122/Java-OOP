/**
 * Method class that saves a method's name, parameters and code
 */
public class Method {
    private String name;
    private Variable[] parameters;
    private String code;

    /**
     * Basic constructor
     * @param name
     * @param parameters
     * @param code
     */
    public Method(String name, Variable[] parameters, String code){
        this.name = name;
        this.parameters = parameters;
        this.code = code;
    }

    /**
     * Getting the name of the method
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Getting the variables (parameters) of the method
     * @return
     */
    public Variable[] getVariables(){
        return parameters;
    }

    /**
     * Getting the the entire code of the method
     * @return
     */
    public String getCode(){
        return code;
    }
}
