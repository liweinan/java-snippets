package io.alchemystudio.serviceloader;

public class HelloProviderImpl extends HelloProvider {

    @Override
    public String getMessage() {
        return this.toString();
    }
}
