package com.company;

public class Main {

    public static void main(String[] args) {
        try{
            HttpUrl url = new HttpUrl("http", "www.google.com", "44", "e/doc");
            System.out.println(url.getURL());
            System.out.println(url.getStringFromProtocol());
            System.out.println(url.getDomain());
            System.out.println(url.getPort());
            System.out.println(url.getDocument());
        } catch (IllegalArgumentException er) {
            System.out.print(er.getMessage());
        }
    }
}
