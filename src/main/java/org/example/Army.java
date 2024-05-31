package org.example;

abstract class Army
{
    protected String rank;
    protected Integer count;

    public Army(String rank, int count)
    {
        this.rank = rank;
        this.count = count;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    abstract void Work();

}