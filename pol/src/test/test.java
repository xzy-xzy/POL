package test;
import java.io.File;
import library.*;

public class test
{
	public static void main(String[] args) 
	{
		liblist X = new liblist( );
		X.load( );
		int num=0;
		try
		{
			for(itemlib c:X.liblist)
			{
				c.path = "./src/test/"+Integer.toString(num)+".txt";
				File file = new File(c.path);
				if(!file.exists( )) file.createNewFile( );
				c.add("TESTPROBLEM","TESTANSWER");
				c.remove(0);
				c.save( );num++;
			}
		}
		catch (Exception e) {}
	}
}
