package main.java;

public class Person {
    private  String name;
    private int flatNumber;
   private int shareInFlat;

    public Person(String name, int flatNumber, int shareInFlat) {
        this.name = name;
        this.flatNumber = flatNumber;
        this.shareInFlat = shareInFlat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public int getShareInFlat() {
        return shareInFlat;
    }

    public void setShareInFlat(int shareInFlat) {
        this.shareInFlat = shareInFlat;
    }
}
