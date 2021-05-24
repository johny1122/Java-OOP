import java.util.HashMap;

/**
 * Scope class that hold the scope previous scope (parent) and the variables declared in this scope
 */
public class Scope {
    private final Scope parentScope;
    private final HashMap<String, Variable> scopeVariables; // (variable's name, variable)

    /**
     * Basic constructor
     * @param parentScope
     */
    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
        this.scopeVariables = new HashMap<>();
    }

    /**
     * Empty constructor
     */
    public Scope() {
        this.parentScope = null;
        this.scopeVariables = new HashMap<>();
    }

    /**
     * Getting the parent scope
     * @return
     */
    public Scope getParentScope() {
        return parentScope;
    }

    /**
     * Getting the scope hashmap of variables
     * @return
     */
    public HashMap<String, Variable> getScopeVariables() {
        return scopeVariables;
    }

    /**
     * Finding a variable in the current scope or at an ancestor scope up to global
     * @param name
     * @return
     */
    public Variable findVariableInScopes(String name) {
        Scope currScope = this;
        Variable searchedVariable;

        while (currScope != null) {
            searchedVariable = currScope.getScopeVariables().get(name);
            if (searchedVariable != null) {
                return searchedVariable;
            }
            currScope = currScope.getParentScope();
        }
        return null;
    }
}
