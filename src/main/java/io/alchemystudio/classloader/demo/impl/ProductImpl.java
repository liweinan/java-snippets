package io.alchemystudio.classloader.demo.impl;

import io.alchemystudio.classloader.demo.Product;

public class ProductImpl implements Product {

    @Override
    public void show() {
        System.out.println("ProductImpl");
    }
}
