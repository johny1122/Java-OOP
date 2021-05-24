/**
 * class representing a variable
 */
public class Variable {

    private final String type;
    private final String name;
    private boolean isInitialized;
    private final boolean isFinal;
    private boolean isGlobalInitialized;


    /**
     * constructor
     * @param type: string of the variable's type
     * @param name: string of the variable's name
     * @param isInitialized: boolean as if the variable is already initialized
     * @param isFinal: boolean as if the variable is final
     * @param isGlobalInitialized: boolean as if the variable is defined in the global scope and also
     * initialized there
     */
    public Variable(String type, String name, boolean isInitialized, boolean isFinal,
                    boolean isGlobalInitialized) {
        this.type = type;
        this.name = name;
        this.isInitialized = isInitialized;
        this.isFinal = isFinal;
        this.isGlobalInitialized = isGlobalInitialized;
    }


    /**
     * return the variable's type
     * @return return the variable's type
     */
    public String getType() {
        return type;
    }


    /**
     * return the variable's name
     * @return return the variable's name
     */
    public String getName() {
        return name;
    }


    /**
     * return true if the variable is defined in the global scope and also initialized there. false otherwise
     * @return return true if the variable is defined in the global scope and also initialized there. false
     * otherwise
     */
    public boolean isGlobalInitialized() {
        return isGlobalInitialized;
    }


    /**
     * return true if the variable is final and false otherwise
     * @return return true if the variable is final and false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }


    /**
     * return true if the variable is initialized and false otherwise
     * @return return true if the variable is initialized and false otherwise
     */
    public boolean isInitialized() {
        return isInitialized;
    }


    /**
     * set the isInitialized field to true (after the variable has been initialized)
     */
    public void setInitialized() {
        isInitialized = true;
    }


    /**
     * set the isInitialized field to false (show be only for global variables which have not been
     * initialized in the global scope
     */
    public void setUnInitialized() {
        isInitialized = false;
    }


    /**
     * set the isGlobalInitialized field to true ( only when a global variable was not initialized in it's
     * declaration but assign after in the global scope)
     */
    public void setIsGlobalInitialized(){
        isGlobalInitialized = true;
    }
}