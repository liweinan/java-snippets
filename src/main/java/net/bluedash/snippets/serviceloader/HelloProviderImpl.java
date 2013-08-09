package net.bluedash.snippets.serviceloader;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 8/8/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloProviderImpl extends HelloProvider {

    @Override
    public String getMessage() {
        return this.toString();
    }
}
