package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Food
{
    private Integer count;
    private String type;
    public Food(String type, Integer count) throws IOException {
        this.count = count;
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
    public String setType() {
        return this.type;
    }

    public void setCount(int count) {
        this.count = count;

    }

    public int getCount() {
        return this.count;
    }
}
