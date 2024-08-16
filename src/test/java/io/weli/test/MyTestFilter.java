package io.weli.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MyTestFilter implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(":::before:::");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(":::after:::");
    }
}
