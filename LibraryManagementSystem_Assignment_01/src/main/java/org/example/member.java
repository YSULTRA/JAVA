package org.example;
import java.util.*;

import static org.example.Librarian.members_list;
import static org.example.Librarian.members_login_Hashmap;

public class member {
    private String Name;
    private int Age;

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private long mobileNumber;

    private float fine;

    ArrayList<Book> booksIssuedtoUser;
    member(String new_name,int age,long number){
        this.Name = new_name;
        this.Age = age;
        this.mobileNumber=number;
        this.fine=0;
        this.booksIssuedtoUser= new ArrayList<>();
        System.out.println("Member Successfully Registered with <Member ID = +"+this.mobileNumber+">!");
    }

    static Scanner sc = new Scanner(System.in);
    public static boolean loginCheck(Long number,String member_name){

        if(members_login_Hashmap.containsKey(number) && members_login_Hashmap.containsValue(member_name) && members_login_Hashmap.get(number).equals(member_name) ){
            System.out.println("Welcome "+members_login_Hashmap.get(number)+" Member ID: "+number);
            return true;
        }else {
            System.out.println("Member with Name: "+member_name+" and Phone No: "+number+" doesn't exist !");
//            System.out.print("If You want to go back to previous steps Enter 9 else enter Any Integer: ");
//            int n = sc.nextInt();
//            if(n==9){
//                Main.printLibraryInterface();
//            }
            return false;
        }
    }
    public static void printMemberInterface(){
        System.out.println(
                """
                        -----------------------------------------------------------
                        1.List Available Books
                        2.List My Books
                        3.Issue book
                        4.Return book
                        5.Pay Fine
                        6.Back
                        -----------------------------------------------------------""");
    }
    public static void printListOfBooks(){
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-20s |\n", "Book ID", "Book Title", "Book Author");
        System.out.println("--------------------------------------------------------------");
        for(Book obj : Book.BookList){
            if(!obj.isBookIssued()){
                System.out.printf("| %-10s | %-30s | %-20s |\n", obj.bookID, obj.getBookTitle(), obj.getBookAuthorName());
            }
        }
        System.out.println("--------------------------------------------------------------");
    }
    public static void listMyBooks(String name, Long number) {
        System.out.println("-------------------BOOKS ISSUED TO YOU------------------------");
        System.out.printf("| %-10s | %-30s | %-20s |\n", "Book ID", "Book Title", "Book Author");
        System.out.println("--------------------------------------------------------------");

        for (member obj : members_list) {
            if (obj.getName().equals(name) && obj.getMobileNumber()==number) {
                for (Book i : obj.booksIssuedtoUser) {
                    System.out.printf("| %-10s | %-30s | %-20s |\n", i.bookID, i.getBookTitle(), i.getBookAuthorName());
                }
            }
        }

        System.out.println("--------------------------------------------------------------");
    }

    public static String nameInput(){
        System.out.print("Enter Name :");
        return sc.nextLine();
    }
}
