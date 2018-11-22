package developGame;

import java.util.Scanner;

public class test {

    int rollno;//instance variable  
    String name;
    static String college = "ITS";//static variable  
    //constructor  

    test(int r, String n) {
        rollno = r;
        name = n;
    }
    //method to display the values  

    void display() {
        System.out.println(rollno + " " + name + " " + college);
    }
}
