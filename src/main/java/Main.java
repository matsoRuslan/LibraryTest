import entity.Book;
import service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static void mainMenu(BookService bookService) throws IOException{
        System.out.println();
        System.out.println("Choose action:");
        System.out.println("1) Add book");
        System.out.println("2) Remove book");
        System.out.println("3) Edit book");
        System.out.println("4) View all books");
        System.out.println("5) Exit program");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = reader.readLine();
        int input = 0;
        try {
           input = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            Main.mainMenu(bookService);
        }
        switch (input){
            case 1:
                System.out.println("You wish to add book");
                System.out.println();
                System.out.println("Enter book author");
                String author = reader.readLine();
                Main.checkForEmpty(author, bookService);
                System.out.println("Enter book title");
                String title = reader.readLine();
                Main.checkForEmpty(title, bookService);
                Book bookToAdd = new Book();
                bookToAdd.setAuthor(author);
                bookToAdd.setTitle(title);
                bookService.persist(bookToAdd);
                System.out.println("book \""+bookToAdd.toString()+ "\" was successfully added");
                break;
            case 2:
                System.out.println("You wish to remove book");
                System.out.println();
                System.out.println("Enter book name");
                String bookTitle = reader.readLine();
                Main.checkForEmpty(bookTitle, bookService);
                List<Book> booksToDelete = bookService.findByTitle(bookTitle);
                if (booksToDelete.size() == 0){
                    System.out.println("No books with such name found");
                    break;
                }
                else if (booksToDelete.size() > 1) {
                    System.out.println("There are few books with such name please choose one by typing a number of book");
                    int index = 1;
                    for (Book b : booksToDelete) {
                        System.out.println(index +". " + b.toString());
                        index++;
                    }
                    int number = 0;
                    try {
                        number = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong input");
                        Main.mainMenu(bookService);
                    }
                    Book bookToDelete = null;
                    try {
                        bookToDelete = booksToDelete.get(number - 1);
                    }catch (IndexOutOfBoundsException e) {
                        System.out.println("Wrong input");
                        Main.mainMenu(bookService);
                    }
                    bookService.delete(bookToDelete.getId());
                    System.out.println("Book was successfully removed");
                }
                else {
                    Book bookToDelete = booksToDelete.get(0);
                    bookService.delete(bookToDelete.getId());
                    System.out.println("Book was successfully removed");
                }
                break;
            case 3:
                System.out.println("You wish to edit book");
                System.out.println();
                System.out.println("Enter book name");
                String bookToEditTitle = reader.readLine();
                Main.checkForEmpty(bookToEditTitle, bookService);
                List<Book> booksToEdit = bookService.findByTitle(bookToEditTitle);
                if (booksToEdit.size() == 0){
                    System.out.println("No books with such name found");
                    break;
                }
                else if (booksToEdit.size() > 1) {
                    System.out.println("There are few books with such name please choose one by typing a number of book");
                    int index = 1;
                    for (Book b : booksToEdit) {
                        System.out.println(index +". " + b.toString());
                        index++;
                    }
                    int number = 0;
                    try {
                        number = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong input");
                        Main.mainMenu(bookService);}

                    System.out.println("Enter book's new name");
                    Book bookToEdit = null;
                    try {
                        bookToEdit = booksToEdit.get(number - 1);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Wrong input");
                        Main.mainMenu(bookService);
                    }
                    String newBookTitle = reader.readLine();
                    Main.checkForEmpty(newBookTitle, bookService);
                    bookToEdit.setTitle(newBookTitle);
                    bookService.update(bookToEdit);
                    System.out.println("Book was successfully edited");
                }
                else {
                    System.out.println("Enter book's new name");
                    String newBookTitle = reader.readLine();
                    Main.checkForEmpty(newBookTitle, bookService);
                    Book bookToEdit = booksToEdit.get(0);
                    bookToEdit.setTitle(newBookTitle);
                    bookService.update(bookToEdit);
                    System.out.println("Book was successfully edited");
                }
                break;
            case 4:
                List<Book> booksToView = bookService.findAllOrdered();
                if (booksToView.size() == 0){
                    System.out.println("There are no books in the library");
                    break;
                }
                else if (booksToView.size() > 1) {
                    System.out.println("Library's books:");
                    int index = 1;
                    for (Book b : booksToView) {
                        System.out.println(index +". " + b.toString());
                        index++;
                    }
                }
                else {
                    System.out.println("Library has only one book:");
                    Book bookToView = booksToView.get(0);
                    System.out.println(bookToView.toString());
                }
                break;
            case 5: System.exit(0);
            default:
                System.out.println("Wrong input");
        }
        Main.mainMenu(bookService);
    }
    private static void checkForEmpty(String input, BookService bookService) throws IOException{
        if (input.isEmpty()){
            System.out.println("Wrong input");
            Main.mainMenu(bookService);
        }
    }
    public static void main(String[] args) throws IOException{
        Logger log = Logger.getLogger("org.hibernate");
        log.setLevel(Level.OFF);
        BookService bookService = new BookService();
        System.out.println("Welcome to the library");
        Main.mainMenu(bookService);

    }
}
