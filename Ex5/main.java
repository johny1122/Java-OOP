import filesprocessing.Filter;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class main{

	public static void main(String[] args) throws FileNotFoundException {
//		String str = "FILTER\n" +
//					 "all\n" +
//					 "ORDER\n" +
//					 "FILTER\n" +
//					 "smaller_Than\n"+
//					 "ORDER\n"+
//					 "type\n";
//		//Create scanner with the specified String Object
//		Scanner scanner = new Scanner(str);
//		System.out.println("Boolean Result: "+scanner.hasNextBoolean());
//		//Change the delimiter of this scanner
//		scanner.useDelimiter("FILTER\n");
//		//Printing the tokenized Strings
//		System.out.println("---Tokenizes String---");
//		while(scanner.hasNext()){
//			System.out.println(scanner.next());
//		}
//		//Display the new delimiter
//		System.out.println("Delimiter used: " +scanner.delimiter());
//		scanner.close();
//----------------------------------------------------------------
//		File file = new File(args[0]);
//		//Create scanner with the specified String Object
//		Scanner scanner = new Scanner(file);
//		scanner.useDelimiter("FILTER");
//
//		//Printing the tokenized Strings
//		System.out.println("\n---Tokenizes String---");
//		Scanner sectionScanner;
//		while(scanner.hasNext()){
//			System.out.println("\n***************");
//			String section = scanner.next();
//			System.out.println("----"+section+"\n----");
//			sectionScanner = new Scanner(section);
//			while (sectionScanner.hasNext()){
//				System.out.print("line in section: ");
//				System.out.println(sectionScanner.next());
//			}
//


//		File file = new File(args[0]);
//		//Create scanner with the specified String Object
//		Scanner scanner = new Scanner(file);
//
//		while(scanner.hasNext()){
//			System.out.println("* "+scanner.nextLine());
//			}
//
//		scanner = new Scanner(file);
//		System.out.println("-------------");
//
//		while(scanner.hasNext()){
//			System.out.println("* "+scanner.nextLine());
//		}
//		scanner.close();


		File file = new File("C:\\Users\\Lenovo\\Desktop\\EX5-test\\Test directory\\New Text Document.txt");
		System.out.println(file.isHidden());
		System.out.println(file.canExecute());
	}

}