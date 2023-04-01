package main.java;

public class Person {
    private String surname;
    private String name;
    private String patronymic;
    private String shareInFlat;
    private int flatNumber;

    public Person(String surname, String name, String patronymic, String shareInFlat, int flatNumber) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.shareInFlat = shareInFlat;
        this.flatNumber = flatNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getShareInFlat() {
        return shareInFlat;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    @Override
    public String toString() {
        return "Собственник{" +
                "фамилия: " + getSurname() +
                ", имя: " + getName() +
                ", отчество: " + getPatronymic() +
                ", доля: " + getShareInFlat() +
                ", номер квартиры: " + getFlatNumber() +
                "}\n";
    }
}
