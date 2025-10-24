package librarymanagement;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class LibraryManagementSystem
{
    static Scanner input = new Scanner(System.in);
    static Member loggedInMember = null;
    static final String adminUsername = "admin";
    static final String adminPassword = "password";
    static List<Member> members = new ArrayList<>();
    static List<Book> books = new ArrayList<>();

    public static void main(String[] args)
    {
        Book book = new Book("Science", "John Doe", "Educational");
        books.add(book);
        Book book1 = new Book("Social Studies", "Jane Smith", "Educational");
        books.add(book1);
        while(true)
        {
            System.out.print("Enter role (admin/user): ");
            String role = input.nextLine();
            if(role.equals("admin"))
            {
                loginAsAdmin();
            }
            else if(role.equals("user"))
            {
                loginAsUser();
            }
            else
            {
                System.out.println("Invalid role. Please try again.");
            }
        }
    }

    private static void loginAsAdmin()
    {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword))
        {
            System.out.println("Logged in as admin.");
            adminMenu();
        }
        else
        {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void loginAsUser()
    {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        loggedInMember = findOrCreateMember(username);
        System.out.println("Logged in as user.");
        userMenu();
    }

    private static Member findOrCreateMember(String username)
    {
        for (Member member : members)
        {
            if (member.username.equals(username)) {
                return member;
            }
        }
        Member newMember = new Member(username);
        members.add(newMember);
        return newMember;
    }

    private static void adminMenu()
    {
        while (true)
        {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Member");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Members");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();  // consume the newline character

            switch (choice)
            {
                case 1: addBook(); break;
                case 2: updateBook(); break;
                case 3: removeBook(); break;
                case 4: addMember(); break;
                case 5: displayAllBooks(); break;
                case 6: displayAllMembers(); break;
                case 7: return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu()
    {
        while (true)
        {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Display All Members");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();  // consume the newline character

            switch (choice)
            {
                case 1: borrowBook(); break;
                case 2: returnBook(); break;
                case 3: displayAllBooks(); break;
                case 4: displayAllMembers(); break;
                case 5: return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook()
    {
        System.out.print("Enter book title: ");
        String title = input.nextLine();
        System.out.print("Enter book author: ");
        String author = input.nextLine();
        System.out.print("Enter book genre: ");
        String genre = input.nextLine();
        books.add(new Book(title, author, genre));
        System.out.println("Book added successfully.");
    }

    private static void updateBook()
    {
        System.out.print("Enter book title to update: ");
        String title = input.nextLine();

        Book book = findBookByTitle(title);
        if (book != null)
        {
            System.out.print("Enter new author: ");
            book.author = input.nextLine();
            System.out.print("Enter new genre: ");
            book.genre = input.nextLine();
            System.out.println("Book updated successfully.");
        }
        else
        {
            System.out.println("Book not found.");
        }
    }

    private static void removeBook()
    {
        System.out.print("Enter book title to remove: ");
        String title = input.nextLine();

        Book book = findBookByTitle(title);
        if (book != null)
        {
            books.remove(book);
            System.out.println("Book removed successfully.");
        } else
        {
            System.out.println("Book not found.");
        }
    }

    private static Book findBookByTitle(String title)
    {
        for (Book book : books)
        {
            if (book.title.equalsIgnoreCase(title))
            {
                return book;
            }
        }
        return null;
    }

    private static void addMember()
    {
        System.out.print("Enter member username: ");
        String username = input.nextLine();
        members.add(new Member(username));
        System.out.println("Member added successfully.");
    }

    private static void borrowBook()
    {
        System.out.print("Enter book title to borrow: ");
        String title = input.nextLine();

        Book book = findBookByTitle(title);
        if (book != null && loggedInMember.borrowBook(book))
        {
            System.out.println("Book borrowed successfully.");
        } else
        {
            System.out.println("Book is either unavailable or borrow limit reached.");
        }
    }

    private static void returnBook()
    {
        System.out.print("Enter book title to return: ");
        String title = input.nextLine();

        Book book = findBookByTitle(title);
        if (book != null && !book.isAvailable)
        {
            loggedInMember.returnBook(book);
            System.out.println("Book returned successfully.");
        } else
        {
            System.out.println("Book not found or it wasn't borrowed.");
        }
    }

    private static void displayAllBooks()
    {
        if(books.isEmpty())
        {
            System.out.println("No books available");
        }else
        {
            System.out.println("\nList of Books:");
            for (Book book : books)
            {
                System.out.println("Title: " + book.title + ", Author: " + book.author + ", Genre: " + book.genre + ", Available: " + book.isAvailable);
            }
        }
    }

    private static void displayAllMembers()
    {
        if(members.isEmpty())
        {
            System.out.println("No memebers exist");
        }else
        {
            System.out.println("\nList of Members:");
            for (Member member : members)
            {
                System.out.println("Username: " + member.username + ", Borrowed Books: " + member.borrowedBooks.size());
            }
        }
    }
}




