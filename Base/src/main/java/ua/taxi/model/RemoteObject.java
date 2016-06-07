package ua.taxi.model;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteObject {

    private String methodName;
    private Object object;

    public RemoteObject() {
    }

    public RemoteObject(String methodName, Object object) {
        this.methodName = methodName;
        this.object = object;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
