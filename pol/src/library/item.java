package library;
public class item 
{
	public String problem,answer; //问题，答案
	item( ) {}
	item(String A,String B)
	{
		problem=A;answer=B;
	}
	
	/**
	 * 通过条目解析出题目
	 * @param S 条目
	 * @return true:成功，false:失败
	 */
	public boolean parse(StringBuffer S)
	{
		String[ ] U = new String(S).split("@");
		if(U.length!=2) return false;
		problem = U[0].strip( );
		answer = U[1].strip( );
		return true;
	}
}

