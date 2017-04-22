package com.example.nikhilr129.forgetitnot.Models;

import io.realm.RealmObject;

/**
 * Created by nikhilr129 on 22/4/17.
 */

public class Attribute extends RealmObject {
    String key;
    String value;

    public Attribute()
    {

    }
    public Attribute(String x,String y)
    {
        key=x;
        value=y;
    }
}
