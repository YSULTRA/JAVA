package org.example;
import java.lang.reflect.Member;
import java.util.*;

import static org.example.Main.printLibraryInterface;
import static org.example.member.*;
import static org.example.Book.*;

public class Librarian {
    static Scanner sc = new Scanner(System.in);

    static ArrayList<member> members_list = new ArrayList<member>();//list to store registered members

    static Map<Long,String> members_login_Hashmap = new HashMap<>();//map to store id with name of user
    public static void printLibrarianInterface(){
        System.out.println(
                "-----------------------------------------------------------\n"+
                "1.Register a member\n" +
                "2.Remove a member\n" +
                "3.Add a book\n" +
                "4.Remove a book\n" +
                "5.View all members along with their books and fines to be paid\n" +
                "6.View all books\n" +
                "7.Back\n"+
                "-----------------------------------------------------------");
    }

    protected static void registerMember() {
        System.out.print("Enter Name: ");
        String member_name = sc.nextLine();
        try{
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Phone no: ");
            long mbnumber = sc.nextLong();
            boolean isNumberUnique = true; // Assume the number is unique initially

            for (member i : members_list) {
                if (i.getMobileNumber() == mbnumber) {
                    System.out.println("Number Already Registered! Please Try Again with a New Number.");
                    isNumberUnique = false;
                    break;
                }
            }

            if (isNumberUnique) {
                System.out.println("Member Successfully Registered!");
                members_list.add(new member(member_name, age, mbnumber));
                members_login_Hashmap.put(mbnumber, member_name);
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Input ");
        }
        sc.nextLine();
    }


    protected static void removeMember() {
        System.out.println("To Remove Member Enter Member ID <phone no>: ");
        try{
            long memberID = sc.nextLong();
            boolean removed = members_list.removeIf(member -> member.getMobileNumber() == memberID);
            members_login_Hashmap.remove(memberID);

            if (removed) {
                System.out.println("Member Deleted Successfully!");
            } else {
                System.out.println("Member with the specified ID was not found.");
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Input");
        }
        sc.nextLine();

    }

    public static void addBookToDatabase(){
        System.out.print("Enter Book Title: ");
        String name = sc.nextLine();
        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();
        System.out.print("No Of Copies: ");
        try{
            int quantity = sc.nextInt();
            addBook(name,author,quantity);
        }catch (InputMismatchException e){
            System.out.println("Invalid Input ");
        }
        sc.nextLine();
//        System.out.print("Enter Unique Book ID: ");
//        int bookid=sc.nextInt();
//        sc.nextLine();

    }
    public static void removeBookFromDatabase(){
        Book.removeBook();
    }
    public static void viewAllMembers() {
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-20s %-10s %-20s %-15s%n", "Name", "Age", "Mobile Number", "Fine To Be Paid");
        System.out.println("--------------------------------------------------------------");

        for (member obj : members_list) {
            System.out.printf("%-20s %-10d %-20s %-10d%n", obj.getName(), obj.getAge(), obj.getMobileNumber(), calMemberTotalFine(obj.booksIssuedtoUser)); // Assuming calMemberTotalFine returns value in cents
        }

        System.out.println("--------------------------------------------------------------");
    }
    public static void viewAllBooks(){
        Book.viewBooks();
    }
    public static long calMemberTotalFine(ArrayList<Book> list){
        long fine = 0;
        for(Book i : list){
            fine +=  calculateFine(i);
        }
        return fine;

    }
}
