package symboltable;import syntaxtree.IdentifierType;import syntaxtree.Type;import java.util.Hashtable;import java.util.Enumeration;public class RamClass {    private String id;    private Hashtable<String, RamMethod> methods;    private Hashtable<String, RamVariable> globals;    private Type type;    public RamClass(String id) {        this.id = id;        type = new IdentifierType(id);        methods = new Hashtable<String, RamMethod>();        globals = new Hashtable<String, RamVariable>();    }    public RamClass() { }    public String getId() {        return id;    }    public Type type() {        return type;    }    public boolean addMethod(String id, Type type) {        if (containsMethod(id)) {            return false;        } else {            methods.put(id, new RamMethod(id, type));            return true;        }    }    public Enumeration getMethods() {        return methods.keys();    }    public RamMethod getMethod(String id) {        if (containsMethod(id)) {            return methods.get(id);        } else {            return null;        }    }        public int numMethods() {        return methods.size();    }    public boolean addVar(String id, Type type) {        if (globals.containsKey(id)) {            return false;        } else {            globals.put(id, new RamVariable(id, type));            return true;        }    }    public RamVariable getVar(String id) {        if (containsVar(id)) {            return globals.get(id);        } else {            return null;        }    }    public boolean containsVar(String id) {        return globals.containsKey(id);    }    public boolean containsMethod(String id) {        return methods.containsKey(id);    }   } // Class