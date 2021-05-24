public class Tester {
    public static void main(String[] args) {

        // initializing books:
        Book book1 = new Book("book1", "author1", 2001,
                3, 7, 2);
        Book book2 = new Book("book2", "author2", 2002,
                7, 1, 4);
        Book book3 = new Book("book3", "author3", 2003,
                0, 2, 3);
        Book book4 = new Book("book4", "author4", 2004,
                2, 4, 5);
        Book book5 = new Book("book4", "author4", 2004,
                2, 4, 5);

        //initializing patrons:
        Patron patron1 = new Patron("first", "patron",
                2, 1, 3, 25);
        Patron patron2 = new Patron("second", "patron",
                4, 1, 5, 16);
        Patron patron3 = new Patron("third", "patron",
                0, 0, 0, 1);
        Patron patron4 = new Patron("fourth", "patron",
                2, 6, 3, 32);
        Patron patron5 = new Patron("fourth", "patron",
                2, 6, 3, 32);

        //initializing libraries:
        Library library1 = new Library(3, 2, 3);
        Library library2 = new Library(5, 1, 5);

        // testing Book methods:
        System.out.println("Book methods tests:");
        System.out.println();
        System.out.println(book1.getCurrentBorrowerId() == -1); //not borrowed
        book1.setBorrowerId(3);
        System.out.println(book1.getCurrentBorrowerId() == 3);
        book1.returnBook();
        System.out.println(book1.getCurrentBorrowerId() == -1); //was returned
        System.out.println(book1.getLiteraryValue() == 3 + 7 + 2);
        System.out.println(book1.stringRepresentation().equals("[book1,author1,2001,12]"));
        System.out.println();

        // testing Patron methods:
        System.out.println("Patron methods tests:");
        System.out.println();
        System.out.println(patron1.stringRepresentation().equals("first patron"));
        System.out.println(patron1.getBookScore(book1) == 2 * 3 + 1 * 7 + 3 * 2);
        System.out.println(!patron1.willEnjoyBook(book1));
        System.out.println(patron1.willEnjoyBook(book2));
        System.out.println(!patron4.willEnjoyBook(book2)); //score == threshold
        System.out.println();

        // testing Library methods:
        System.out.println("Library methods tests:");
        System.out.println();
        int book1Id1 = library1.addBookToLibrary(book1);
        int book2Id1 = library1.addBookToLibrary(book2);
        System.out.println("1: " + (library1.addBookToLibrary(book1) == book1Id1));
        int invalidBookId = 0;
        while ((invalidBookId == book1Id1) || (invalidBookId == book2Id1)) {
            invalidBookId++;
        }
        System.out.println("2: " + (!library1.isBookIdValid(invalidBookId)));
        System.out.println("3: " + (library1.isBookIdValid(book1Id1)));
        System.out.println("4: " + (!library1.isBookAvailable(invalidBookId)));
        int book3Id1 = library1.addBookToLibrary(book3);
        int book4Id1 = library1.addBookToLibrary(book4); //no room for this book
        System.out.println("5: " + (!library1.isBookIdValid(book4Id1)));
        System.out.println("6: " + (book4Id1 == -1));
        System.out.println("7: " + (library1.getBookId(book2) == book2Id1));
        System.out.println("8: " + (library1.getBookId(book4) == -1));
        System.out.println("9: " + (library1.isBookAvailable(book1Id1)));
        int patron1Id1 = library1.registerPatronToLibrary(patron1);
        System.out.println("10: " + (library1.isPatronIdValid(patron1Id1)));
        System.out.println("11: " + (library1.getPatronId(patron1) == patron1Id1));
        System.out.println("12: " + (library1.getPatronId(patron2) == -1)); //not registered
        int invalidPatronId = 0;
        while (invalidPatronId == patron1Id1) {
            invalidPatronId++;
        }
        System.out.println("13: " + (!library1.isPatronIdValid(invalidPatronId)));
        int patron2Id1 = library1.registerPatronToLibrary(patron2);
        System.out.println("14: " + (library1.getPatronId(patron2) == patron2Id1));
        int patron3Id1 = library1.registerPatronToLibrary(patron3);
        int patron4Id1 = library1.registerPatronToLibrary(patron4); //no room for this patron
        System.out.println("15: " + (patron4Id1 == -1));
        System.out.println("16: " + (library1.borrowBook(book2Id1, patron1Id1)));
        System.out.println("17: " + (!library1.borrowBook(book2Id1, patron2Id1))); //book is already borrowed
        System.out.println("18: " + (!library1.borrowBook(-1, patron1Id1))); //bookId is not valid
        System.out.println("19: " + (!library1.borrowBook(book1Id1, -1))); //patronId is not valid
        System.out.println("20: " + (library1.isBookAvailable(book1Id1)));
        System.out.println("21: " + (!library1.isBookAvailable(book2Id1)));
        library1.returnBook(book2Id1);
        System.out.println("21: " + (library1.isBookAvailable(book2Id1)));
        library1.returnBook(book1Id1); //should not have any effect, wasn't borrowed in the first place
        library1.returnBook(-1); //should not have any effect, bookId is not valid

        //next three lines to confirm trying to borrow invalid book does not affect a patron's ability to
        // borrow the maximal number of books:
        library1.borrowBook(book1Id1, patron2Id1);
        library1.borrowBook(-1, patron2Id1);
        System.out.println("22: " + (library1.borrowBook(book2Id1, patron2Id1)));
        library1.returnBook(book1Id1);
        library1.returnBook(book2Id1);

        int testBook1Id1 = library1.addBookToLibrary(book1); //book already exists in library
        System.out.println("23: " + (testBook1Id1 == book1Id1));
        int testPatron1Id1 = library1.registerPatronToLibrary(patron1); //patron is already registered
        System.out.println("24: " + (testPatron1Id1 == patron1Id1));
        library1.borrowBook(book1Id1, patron2Id1);
        library1.borrowBook(book2Id1, patron2Id1);
        System.out.println("25: " + (!library1.borrowBook(book3Id1, patron2Id1))); //reached maximal number
        // of allowed borrowed books
        library1.returnBook(book1Id1);
        System.out.println("26: " + (library1.borrowBook(book3Id1, patron2Id1))); //should be able to
        // borrow since returned one book
        library1.returnBook(book2Id1);
        library1.returnBook(book3Id1);

        System.out.println("27: " + (library1.suggestBookToPatron(patron2Id1).equals(book2)));
        library1.borrowBook(book2Id1, patron1Id1);
        System.out.println("28: " + (library1.suggestBookToPatron(patron2Id1).equals(book1))); //book with
        // highest rank is already borrowed
        System.out.println("29: " + (library1.suggestBookToPatron(patron1Id1) == null)); //the only book
        // that passes the enjoyment threshold is already borrowed
        System.out.println("30: " + (library1.suggestBookToPatron(patron3Id1) == null)); //no book passes
        // the enjoyment threshold
        library1.returnBook(book2Id1);

        //tests with two libraries:
        int book4Id2 = library2.addBookToLibrary(book4);
        int book5Id2 = library2.addBookToLibrary(book5);
        int patron5Id2 = library2.registerPatronToLibrary(patron5);
        int patron4Id2 = library2.registerPatronToLibrary(patron4);
        int patron2Id2 = library2.registerPatronToLibrary(patron2);
        System.out.println("31: " + (!(book4Id2 == book5Id2))); //same data but different objects
        System.out.println("32: " + (!(patron4Id2 == patron5Id2))); //same data but different objects
        library2.borrowBook(book4Id2, patron2Id2);
        System.out.println("33: " + (!library2.borrowBook(book5Id2, patron2Id2))); //reached maximal number
        // of allowed borrowed books
        System.out.println("34: " + (library1.borrowBook(book1Id1, patron2Id1))); //same patron, but
        // borrows from a different library
        System.out.println("35: " + (library1.borrowBook(book3Id1, patron2Id1)));
        System.out.println("36: " + (!library1.borrowBook(book2Id1, patron2Id1))); //reached maximal number
        // of allowed borrowed books
        System.out.println("37: " + (library1.borrowBook(book2Id1, patron1Id1)));
        library2.returnBook(book4Id2);
        library1.returnBook(book1Id1);
        library1.returnBook(book2Id1);
        library1.returnBook(book3Id1);
    }
}
