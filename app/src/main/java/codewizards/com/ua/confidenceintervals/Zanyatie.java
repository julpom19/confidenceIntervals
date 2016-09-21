package codewizards.com.ua.confidenceintervals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Интернет on 06.09.2016.
 */

public class Zanyatie {
    private String name;
    private String auditor;
    private String teacher;
    private String type;

    public Zanyatie(String name, String auditor, String teacher, String type) {
        this.name = name;
        this.auditor = auditor;
        this.teacher = teacher;
        this.type = type;
    }
}

class PoProsbe {
    public List<Zanyatie> getList(Day day, int numOfWeek) {
        List<Zanyatie> list = new ArrayList<>();
        if(day == Day.MO) {
            if(numOfWeek == 1) {
                list.add(new Zanyatie("Управління IT-проектами", "Струкова А.С.", "2-309", "практика"));
                list.add(new Zanyatie("Проектування інформаційних систем", "Давиденко Є.О.", "2-309", "практика"));
                list.add(new Zanyatie("Геоінформаційні системи", "Донченко М.В.", "2-406", "лекція"));
            } else {
                list.add(new Zanyatie("Управління IT-проектами", "Струкова А.С.", "2-309", "практика"));
                list.add(new Zanyatie("Проектування інформаційних систем", "Давиденко Є.О.", "2-309", "практика"));
                list.add(new Zanyatie("Управління IT-проектами", "Кошовий В.В.", "2-406", "лекція"));
                list.add(new Zanyatie("Геоінформаційні системи", "Донченко М.В.", "2-308", "практика"));
            }
        }
        if(day == Day.TU) {
            if(numOfWeek == 1) {
                list.add(new Zanyatie("Моделі і методи НЛ/Теорія керування", "Сіденко Є.В./Кондратенко Г.В.", "1-313а/1-404", "практика"));
                list.add(new Zanyatie("Моделювання систем", "Мещанінов О.П.", "2-201", "практика"));
                list.add(new Zanyatie("Іноземна мова", "Кулік В.С./Колотій Н.В.", "11-105/11-401", "практика"));
                list.add(new Zanyatie("/Об'єктні БД", "Горбань Г.В.", "2-306", "практика"));
            } else {
                list.add(new Zanyatie("Теорія керування/Моделі і методи НЛ", "Кондратенко Г.В./Сіденко Є.В.", "10П-305/10П-307", "практика"));
                list.add(new Zanyatie("Моделювання систем", "Мещанінов О.П.", "2-201", "практика"));
                list.add(new Zanyatie("Іноземна мова", "Кулік В.С./Колотій Н.В.", "11-105/11-401", "практика"));
                list.add(new Zanyatie("/Об'єктні БД", "Горбань Г.В.", "2-306", "практика"));
            }
        }
        if(day == Day.WE) {
            list.add(new Zanyatie("Об'єктні БД/", "Горбань Г.В.", "1-312", "практика"));
            list.add(new Zanyatie("Кросплатформене програмування", "Нездолій Ю.О.", "2-406", "лекція"));
            list.add(new Zanyatie("ФВС", "", "", "практика"));
            list.add(new Zanyatie("ФВС", "", "", "практика"));
            list.add(new Zanyatie("Кросплатформене програмування", "Нездолій Ю.О.", "2-201", "практика"));
        }
        if(day == Day.TH) {
            list.add(new Zanyatie("/Кросплатформене програмування", "Горбань Г.В.", "2-201", "практика"));
            list.add(new Zanyatie("Об'єктні БД", "Горбань Г.В.", "2-407", "лекція"));
            list.add(new Zanyatie("Моделювання систем", "Мещанінов О.П.", "2-407", "лекція"));
            list.add(new Zanyatie("Прикладна Статистика", "Варшамов А.В.", "2-407", "лекція"));
        }
        if(day == Day.FR) {
            if(numOfWeek == 1) {
                list.add(new Zanyatie("Моделі і методи НЛ", "Сіденко Є.В.", "2-503", "практика"));
                list.add(new Zanyatie("Проектування інформаційних систем", "Давиденко Є.О.", "2-406", "лекція"));
                list.add(new Zanyatie("Геоінформаційні системи", "Донченко М.В.", "2-308", "практика"));
                list.add(new Zanyatie("Теорія керування", "Кондратенко Г.В.", "2-407", "лекція"));
            } else {
                list.add(new Zanyatie("Управління IT-проектами", "Кошовий В.В.", "2-406", "лекція"));
                list.add(new Zanyatie("Проектування інформаційних систем", "Давиденко Є.О.", "2-406", "лекція"));
                list.add(new Zanyatie("Прикладна Статистика", "Варшамов А.В.", "2-503", "практика"));
                list.add(new Zanyatie("Моделі і методи нечіткої логіки", "Кондратенко Ю.П.", "2-407", "лекція"));
            }
        }
        return list;
    }
}

enum Day {
    MO, TU, WE, TH, FR
}


