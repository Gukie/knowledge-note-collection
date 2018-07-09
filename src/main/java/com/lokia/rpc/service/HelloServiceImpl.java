package com.lokia.rpc.service;

public class HelloServiceImpl implements  HelloService {
    @Override
    public String sayHello() {
        return "hello, from lokia!";
    }
}
