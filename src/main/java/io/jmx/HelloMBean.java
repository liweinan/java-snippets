package io.jmx;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface HelloMBean {
    public void setMessage(String message);
    public String getMessage();
    public void sayHello();
}
