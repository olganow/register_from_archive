package main.java;

public class Owner {
    private String surname;
    private String name;
    private String patronymic;
    private double shareInFlat;
    private int flatNumber;
    private double area;

    public Owner(String surname, String name, String patronymic, double shareInFlat, int flatNumber, double area) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.shareInFlat = shareInFlat;
        this.flatNumber = flatNumber;
        this.area = area;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setShareInFlat(double shareInFlat) {
        this.shareInFlat = shareInFlat;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public double getShareInFlat() {
        return shareInFlat;
    }


    public int getFlatNumber() {
        return flatNumber;
    }

    public double getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Собственник{" +
                "фамилия: " + getSurname() +
                ", имя: " + getName() +
                ", отчество: " + getPatronymic() +
                ", номер квартиры: " + getFlatNumber() +
                ", доля: " + getShareInFlat() +
                ", площадь_кв: " + getArea() +
                "}\n";
    }
}
