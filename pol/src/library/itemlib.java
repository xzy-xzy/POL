package library;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class itemlib 
{
	public ArrayList<item> lib; //题目列表
	public int num; //题目数目
	public String name; //题库名称
	public String path; //题库路径
	
	/**
	 * 设置为空题库
	 * @param _name 题库名称
	 * @return 0:成功，1:文件已存在，2:文件异常
	 */
	public int setnull(String _name)
	{
		String _path = "./src/library/"+_name+".txt";
		try
		{
			File file = new File(_path);
			if(!file.exists( )) file.createNewFile( );
			else return 1;
		}
		catch (Exception e) {return 2;}
		name = _name;
		path = _path;
		num = 0;
		lib = new ArrayList<item>( );
		return 0;
	}
	/**
	 * 加载为已有的题库
	 * 题目格式为 problem@answer@
	 * @param gram 信息条目 "path,name,"
	 * @return 0:成功，1:条目解析错误，2:文件不存在，3:题目解析错误，4:文件异常
	 */
	public int setfromfile(StringBuffer gram)
	{
		String[ ] S = new String(gram).split(",");
		if(S.length!=2) return 1;
		String _path = S[0].strip( );
		String _name = S[1].strip( );
		int _num = 0;
		lib = new ArrayList<item>( );
		try
		{
			File file = new File(_path);
			if(!file.exists( )) {return 2;}
			StringBuffer buf = new StringBuffer( );
			FileReader R = new FileReader(_path);
			int cnt=0,ch=0;char c;
			while((ch=R.read( ))!=-1)
			{
				c=(char)ch;
				buf.append(c);
				if(c=='@')
				{
					cnt++;
					if(cnt==2)
					{
						item X = new item( );
						if(!X.parse(buf)) {return 3;}
						else lib.add(X);
						cnt=0;_num++;
						buf = new StringBuffer( );
					}
				}
			}
			R.close( );
		}
		catch(Exception e) {return 4;}
		path = _path;
		name = _name;
		num = _num;
		return 0;
	}
	/**
	 * 添加题目
	 * @param pro 题目
	 * @param ans 答案
	 * @param index 插入位置
	 * @return always true
	 */
	public boolean add(String pro,String ans,int index)
	{
		lib.add(index,new item(pro,ans));
		num++;
		return true;
	}
	/**
	 * 修改题目
	 * @param pro 题目
	 * @param ans 答案
	 * @param index 位置
	 * @return always true
	 */
	public boolean ch(String pro,String ans,int index)
	{
		lib.set(index,new item(pro,ans));
		return true;
	}
	/**
	 * 删除题目
	 * @param index 题目ID
	 * @return always true
	 */
	public boolean remove(int index)
	{
		lib.remove(index);
		num--;
		return true;
	}
	/**
	 * 题库存档
	 * @return 0:成功，1:文件异常
	 */
	public int save( )
	{
		try
		{
			FileWriter W = new FileWriter(path);
			for(item X:lib)
			{
				W.write(X.problem,0,X.problem.length( ));
				W.write("@");
				W.write(X.answer,0,X.answer.length( ));
				W.write("@");
			}
			W.close( );
		}
		catch(Exception e) {return 1;}
		return 0;
	}
	
}


