/**
 * MethodCall class saving the name and values given as parameters
 */
public class MethodCall {
    private String name;
    private String[] values;

    /**
     * Basic constructor
     * @param name
     * @param values
     */
    public MethodCall(String name, String[] values){
        this.name = name;
        this.values = values;
    }

    /**
     * Getting the name of the method we are calling to
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Getting the values to send as parameters
     * @return
     */
    public String[] getValues(){
        return values;
    }
}
