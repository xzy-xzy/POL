package library;
import java.io.FileReader;
import java.util.ArrayList;

public class liblist
{
	public String path = "./src/library/liblist.index";
	public ArrayList<itemlib> liblist; //题库列表
	public int num=0; //题库数目
	/**
	 * 从path加载所有的题库
	 * 题库条目格式为 path,name,
	 * @return 0:成功,1:条目解析错误,2:文件异常
	 */
	public int load( )
	{
		liblist = new ArrayList<itemlib>( );
		int _num=0;
		try
		{
			StringBuffer buf = new StringBuffer( );
			FileReader R = new FileReader(path);
			int cnt=0,ch=0;char c;
			while((ch=R.read( ))!=-1)
			{
				c=(char)ch;
				buf.append(c);
				if(c==',')
				{
					cnt++;
					if(cnt==2)
					{
						itemlib X = new itemlib( );
						if(X.setfromfile(buf)!=0) return 1;
						else liblist.add(X);
						cnt=0;_num++;
						buf = new StringBuffer( );
					}
				}
			}
			R.close( );
		}
		catch(Exception e) {return 2;}
		num=_num;
		return 0;
	}
}
