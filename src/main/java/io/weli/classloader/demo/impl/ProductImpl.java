package io.weli.classloader.demo.impl;

import io.weli.classloader.demo.Product;

public class ProductImpl implements Product {

    @Override
    public void show() {
        System.out.println("ProductImpl");
    }
}
