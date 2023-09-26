package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.Librarian.*;

public class Book {
    static ArrayList<Book> BookList = new ArrayList<>();
    static ArrayList<Book> Issued_Book = new ArrayList<>();

    static HashMap<Long, ArrayList<Book>> memberIssuedBooks = new HashMap<>();
    private String bookTitle;
    private String bookAuthorName;
    private int bookNoCopies;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

    public int getBookNoCopies() {
        return bookNoCopies;
    }

    public void setBookNoCopies(int bookNoCopies) {
        this.bookNoCopies = bookNoCopies;
    }

    public boolean isBookIssued() {
        return bookIssued;
    }

    public void setBookIssued(boolean bookIssued) {
        this.bookIssued = bookIssued;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    int bookID;

    private LocalDateTime issueDate;
    private LocalDateTime returnDate;

    private boolean bookIssued = false;

    static int nextID = 1;

    Book(String bookname, String authorname, int numcopies) {
        this.bookTitle = bookname;
        this.bookAuthorName = authorname;
        this.bookNoCopies = numcopies;
        this.bookID = nextID;
        nextID += 1;
        this.issueDate = null;
        this.returnDate = null;
    }

    public static void addBook(String bookname, String authorname, int numcopies) {
        for (int i = 1; i <= numcopies; i++) {
            BookList.add(new Book(bookname, authorname, numcopies));
        }
        System.out.println(numcopies + " Copies of " + bookname + " is Added Successfully !");
    }

    public static void removeBook() {
        Book.viewBooks();
        System.out.print("Enter Unique Book ID To Delete: ");
        try {
            long uniqueIdToDelete = sc.nextLong();
            sc.nextLine();
            int indexToRemove = -1; // Initialize with an invalid index

            for (int i = 0; i < BookList.size(); i++) {
                if (BookList.get(i).bookID == uniqueIdToDelete) {
                    indexToRemove = i;
                    break; // Exit the loop since we found the book
                }
            }

            if (indexToRemove != -1) {
                if (BookList.get(indexToRemove).bookIssued) {
                    System.out.println("Book Issued To User Can't Be Removed !");
                } else {
                    BookList.remove(indexToRemove);
                    System.out.println("Book Removed Successfully !");
                }
            } else {
                System.out.println("Book with Specified ID Not Found !");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input Type");
            sc.nextLine();
        }
    }


    public static void viewBooks() {
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-20s |\n", "Book ID", "Book Title", "Book Author");
        System.out.println("--------------------------------------------------------------");
        for (Book obj : BookList) {
            System.out.printf("| %-10s | %-30s | %-20s |\n", obj.bookID, obj.bookTitle, obj.bookAuthorName);
        }
        System.out.println("--------------------------------------------------------------");
    }

    public static void issueBook(String user_name, Long number) {
        member.printListOfBooks();
        System.out.println("---------------------------------");
        System.out.print("Book ID: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Book Name: ");
            String name = sc.nextLine();
            System.out.println("---------------------------------");

            boolean bookIssued = false;
            boolean bookFound = false; // Add a flag to track if the book was found

            for (member i : members_list) {
                if (Objects.equals(i.getName(), user_name) && i.getMobileNumber() == number && i.getFine() == 0 && i.booksIssuedtoUser.size() < 2) {
                    if (i.booksIssuedtoUser.isEmpty() || !isBookOverdue(i.booksIssuedtoUser.get(0))) {
                        Iterator<Book> iterator = BookList.iterator();
                        while (iterator.hasNext()) {
                            Book obj = iterator.next();
                            if (obj.bookID == id && !obj.bookIssued && Objects.equals(obj.bookTitle, name)) {
                                i.booksIssuedtoUser.add(obj);
                                obj.bookIssued = true;
                                Issued_Book.add(obj);
                                System.out.println("Book Issued Successfully !");
                                bookIssued = true;
                                obj.getIssueDate();
                                obj.setIssueDate(LocalDateTime.now());
                                obj.setReturnDate(obj.getIssueDate().plusSeconds(10));
                                bookFound = true; // Mark the book as found
                                break;
                            }
                        }
                    } else {
                        System.out.println("The first book issued to you is overdue and has an outstanding penalty. Please settle this before issuing another book.");
                    }
                    break;
                } else {
                    if (i.booksIssuedtoUser.size() >= 2) {
                        System.out.println("Maximum book issuance limit of 2 has been reached.");
                    } else if (i.getFine() != 0) {
                        System.out.println("Kindly note that there is an outstanding penalty of " + i.getFine() + " on your account. Please settle this fine before proceeding with any new book issuance.");
                    } else {
                        System.out.println("We apologize for any inconvenience caused.");
                    }
                }
            }

            // Check if the book was not found and print a message
            if (!bookFound) {
                System.out.println("The book with ID " + id + " is not available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input !");
            sc.nextLine();
        }
    }


    private static boolean isBookOverdue(Book book) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime returnDate = book.getReturnDate();
        return currentDate.isAfter(returnDate);
    }



    public static long returnDuration(Book book){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime returnDate = book.getReturnDate();

        Duration duration = Duration.between(returnDate, currentDate);
        long secondsLate = duration.getSeconds();
        if(secondsLate>=0){
            return secondsLate;
        }
        else {
            return -1*secondsLate;
        }
    }

    public static long calculateFine(Book book) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime returnDate = book.getReturnDate();
        if(currentDate.isAfter(returnDate)){
            Duration duration = Duration.between(returnDate, currentDate);
            long secondsLate = duration.getSeconds();

            // Assuming a fine rate of 3 units per second late
            long fine = secondsLate * 3;

            if(fine>=0){
                return fine;
            }
            else {
                return -1*fine;
            }
        }else{
            return 0;
        }



    }

    public static Book returnBookFromID(int bookid,member i) {
        Book b = null;
        for (Book book : BookList) {
            if (book.bookID == bookid || i.booksIssuedtoUser.contains(book)) {
                b = book;
            }
        }
        return b;
    }


    public static void returnBook(String name, long number) {
        member.listMyBooks(name, number);
        for (member i : members_list) {
            if (Objects.equals(i.getName(), name) && i.getMobileNumber() == number) {
                if (i.booksIssuedtoUser.isEmpty()) {
                    System.out.println("You have zero books issued");
                } else {
                    System.out.println("---------------------------------\n");
                    System.out.print("Enter Book ID: ");
                    try{
                        int bookid = sc.nextInt();
                        System.out.println("---------------------------------\n");
                        Book b = returnBookFromID(bookid,i);
//                    System.out.println(b.bookID+" "+b.bookTitle+" "+b.bookIssued);
                        if(calculateFine(b)==0){
                            removeReturnBook(bookid,i);
                            System.out.println("Book Returned Before Return Date !");
                        }else{
                            i.setFine(i.getFine()+calculateFine(b));
                            removeReturnBook(bookid,i);
                            System.out.println("Book ID: " + bookid + " successfully returned. " + calculateFine(b) + " Rupees has been charged for a delay of " + returnDuration(b)+ " days.");
                        }

                    }catch (InputMismatchException e){
                        System.out.println("Invalid Input Type");
                    }
                    sc.nextLine();
                }
            }
            System.out.println("---------------------------------\n");

        }
    }
    public static void removeReturnBook(int uniqueIdToDelete,member m) {
        int indexToRemove = -1; // Initialize with an invalid index

        for (int i = 0; i < m.booksIssuedtoUser.size(); i++) {
            if (m.booksIssuedtoUser.get(i).bookID == uniqueIdToDelete) {
                indexToRemove = i;
                break; // Exit the loop since we found the book
            }
        }

        if (indexToRemove != -1) {
            m.booksIssuedtoUser.get(indexToRemove).bookIssued=false;
            m.booksIssuedtoUser.remove(indexToRemove);
            System.out.println("Book Removed Successfully !");
        } else {
            System.out.println("Book with the specified ID was not found.");
        }
    }
    public static void payFine (String memberName,long number){
            for (member i : members_list) {
                if (Objects.equals(i.getName(), memberName) && i.getMobileNumber() == number) {
                    System.out.println("---------------------------------\n");
                    System.out.println("You had a total fine of Rs. " + i.getFine() + ". It has been paid successfully!");
                    System.out.println("---------------------------------\n");
                    i.setFine(0);
                }
            }
        }
    }
