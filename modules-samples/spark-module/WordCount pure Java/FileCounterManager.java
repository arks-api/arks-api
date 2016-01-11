import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class FileCounterManager 
{
	
	public static String getCounter() throws IOException
	{
		File file = new File("/home/rahul/DATASET/counter.txt");
		Scanner sc = new Scanner(new FileInputStream(file));
		int count = sc.nextInt();
		return String.valueOf(count);
	}
	
	public static void incCounter() throws IOException
	{
		File file = new File("/home/rahul/DATASET/counter.txt");
		Scanner sc = new Scanner(new FileInputStream(file));
		int count = sc.nextInt();
		count=count+1;
		Writer wr = new FileWriter(file);
		wr.write(String.valueOf(count));
		wr.close();
		System.out.println("Counter Incremented");
	}
}
