package com.hakimbooks.hakimbooks.utility;

public class Message {
    private static final StringBuilder builder=new StringBuilder();

    public static String getMessage(){
        return builder.toString();
    }

    public static void addMessage(String msg){
        builder.append(msg);
    }

    public static void clear(){
        builder.replace(0,builder.length(),"");
    }
}
