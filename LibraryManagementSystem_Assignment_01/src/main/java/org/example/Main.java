package org.example;

import java.util.Scanner;

import java.util.Scanner;
import org.example.member;
import org.example.Librarian;
import org.example.*;

public class Main {

    public static void printLibraryInterface() {
        System.out.println("----------------------------------------------------------");
        System.out.println("1. Enter as a librarian");
        System.out.println("2. Enter as a Member");
        System.out.println("3. Exit");
        System.out.println("-----------------------------------------------------------");
    }


    public static void main(String[] args) {
        System.out.println("Library Portal Initialized !");
        Scanner sc = new Scanner(System.in);

        while (true) {
            printLibraryInterface();
            int choice = sc.nextInt();
            System.out.println("-----------------------------------------------------------");

            if (choice == 3) {
                System.out.println("Thanks for Visiting!");
                break;
            } else if (choice == 1) {
                System.out.println("You are Librarian");
                while (true) {
                    Librarian.printLibrarianInterface();
                    int librarian_choice = sc.nextInt();
                    // Handle librarian options...
                    if (librarian_choice == 7) {
                        break;
                    } else if (librarian_choice == 1) {
                        Librarian.registerMember();
                    } else if (librarian_choice == 2) {
                        Librarian.removeMember();
                    } else if (librarian_choice == 3) {
                        Librarian.addBookToDatabase();
                    } else if (librarian_choice == 4) {
                        Librarian.removeBookFromDatabase();
                    } else if (librarian_choice == 5) {
                        Librarian.viewAllMembers();
                    } else if (librarian_choice == 6) {
                        Librarian.viewAllBooks();
                    } else {
                        System.out.println("Invalid Input");
                    }
                }
            } else if (choice == 2) {
                System.out.println("You are member");
                String member_name = member.nameInput();
                System.out.print("Enter Phone no: ");
                long number = sc.nextLong();
                sc.nextLine();
                if (member.loginCheck(number,member_name)) {
                    while (true) {
                        member.printMemberInterface();
                        int user_choice = sc.nextInt();
                        // Handle member options...
                        if (user_choice == 6) {
                            break;
                        } else if (user_choice == 4) {
                            Book.returnBook(member_name,number);
                        } else if (user_choice == 5) {
                            Book.payFine(member_name,number);
                        } else if (user_choice==3) {
                            Book.issueBook(member_name,number);
                        } else if (user_choice == 1) {
                            member.printListOfBooks();
                        } else if (user_choice == 2) {
                            member.listMyBooks(member_name,number);
                        } else {
                            System.out.println("Invalid Input");
                        }
                    }
                } else {
                    System.out.println("Invalid login credentials. Returning to library interface.");
                }
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}
