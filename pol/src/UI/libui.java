package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import library.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class libui extends JFrame {
	private JTextField textField;
	private JTable table_1;

	/**
	 * 去除按钮上的所有监听器
	 * @param B 对象
	 */
	public void remove_all(JButton B)
	{
		ActionListener S[ ] = B.getActionListeners( );
		for(int i=0;i<S.length;i++) B.removeActionListener(S[i]);
	}
	
	/**
	 * 设置表格底色
	 * @param table 表格
	 */
    public static void tablecolor(JTable table) 
    {
        try
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer()
            {
                private static final long serialVersionUID = 1L;
                public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column){
                    if(row%2 == 0)
                        setBackground(Color.WHITE);//设置奇数行底色
                    else if(row%2 == 1)
                        setBackground(new Color(215,215,215));//设置偶数行底色
                    return super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
                }
            };
            for(int i = 0; i < table.getColumnCount(); i++) 
            {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
            tcr.setHorizontalAlignment(JLabel.CENTER);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
	
	/**
	 * Create the frame.
	 */
	public libui(liblist X,DefaultTableModel model) {
		
		JPanel contentPane;
		JTable table;
		JButton btnNewButton;
		JButton btnNewButton_1;
		JButton btnNewButton_2;
		JButton button;
		
		//setDefaultCloseOperation();
		setBounds(100, 100, 488, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * 文字：题库列表
		 */
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(SystemColor.window);
		textPane.setText("题库列表");
		textPane.setBounds(6, 31, 61, 16);
		contentPane.add(textPane);
		
		/**
		 * 滚动条
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 59, 119, 196);
		contentPane.add(scrollPane);
		
		/**
		 * 显示题库列表的表格
		 */
		/*
		String[ ][ ] infoindex = new String[X.num][2];
		int i=0;
		for(itemlib e:X.liblist)
		{
			infoindex[i][0] = e.name;
			infoindex[i][1] = e.path;
			i++;
		}
		String[ ] title = {"name","path"};
		DefaultTableModel model = new DefaultTableModel(infoindex,title);
		*/
		table = new JTable(model)
				{
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				};
		scrollPane.setViewportView(table);
		tablecolor(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/**
		 * 删除按钮
		 */
		btnNewButton = new JButton("删除");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ID = table.getSelectedRow( );
				JTextPane textPane = new JTextPane();
				JDialog tip = new JDialog(libui.this,true);
				tip.getContentPane().setLayout(null);
				tip.setBounds(100,100,300,200);
				//tip.setVisible(true);
				JButton tipA = new JButton("确认");
				tipA.setBounds(60,100,60,20);
				JButton tipB = new JButton("取消");
				tipB.setBounds(170,100,60,20);
				tip.getContentPane( ).add(tipA);
				tip.getContentPane( ).add(tipB);
				textPane.setEditable(false);
				textPane.setBackground(SystemColor.window);
				textPane.setText("你确定要删除该题库吗？");
				textPane.setBounds(75, 50, 300, 16);
				tip.getContentPane( ).add(textPane);
				if(ID==-1) return;
				tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								X.liblist.remove(ID);
								X.save( );
								model.removeRow(ID);
								tip.setVisible(false);
							}
						});
				tipB.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						tip.setVisible(false);
					}
				});
				tip.setVisible(true);
			}
		});
		btnNewButton.setBounds(18, 257, 61, 29);
		contentPane.add(btnNewButton);
		
		/**
		 * 文件选择器：导入新的题库
		 */
		JFileChooser cho = new JFileChooser("./src");
		
		btnNewButton_1 = new JButton("导入新的题库");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int val = cho.showOpenDialog(libui.this);
				if(val == JFileChooser.APPROVE_OPTION)
				{
					File file = cho.getSelectedFile( );
					String name = file.getName( );
					name = name.substring(0,name.lastIndexOf("."));
					String path = file.getPath( );
					for(int i=0;i<table.getRowCount( );i++)
					{
						if(model.getValueAt(i,1).equals(path))
						{
							JDialog tip = new JDialog(libui.this,true);
							tip.getContentPane().setLayout(null);
							tip.setBounds(100,100,300,200);
							JButton tipA = new JButton("确认");
							tipA.setBounds(120,100,60,20);
							tipA.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									tip.setVisible(false);
								}
							});
							tip.getContentPane( ).add(tipA);
							JTextPane textPane = new JTextPane();
							textPane.setEditable(false);
							textPane.setBackground(SystemColor.window);
							textPane.setText("该题库已存在");
							textPane.setBounds(110, 50, 300, 16);
							tip.getContentPane( ).add(textPane);
							tip.setVisible(true);
							return;
						}
					}
					itemlib R = new itemlib( );
					if(R.setfromfile(new StringBuffer(path+","+name+","))==0)
					{
						X.liblist.add(R);X.save( );
						String newrow[ ] = new String[2];
						newrow[0]=name;
						newrow[1]=path;
						model.addRow(newrow);
						int a=table.getSelectedRow( );
						if(a!=-1) table.removeRowSelectionInterval(a,a);
						int W = table.getRowCount( )-1;
						table.addRowSelectionInterval(W,W);
					}
				}
			}
		});
		btnNewButton_1.setBounds(18, 281, 117, 29);
		contentPane.add(btnNewButton_1);
		
		/**
		 * 新建空白题库
		 */
		button = new JButton("新建空白题库");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog tip = new JDialog(libui.this,true);
				tip.getContentPane().setLayout(null);
				tip.setBounds(100,100,300,200);
				JButton tipA = new JButton("确认");
				tipA.setBounds(120,100,60,20);
				JTextField bk = new JTextField( );
				bk.setBounds(110, 50, 170, 18);
				tip.getContentPane( ).add(bk);
				tipA.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String name = bk.getText( );
						if(name.contentEquals("")) 
							{
								name="未命名";
							}
						itemlib R = new itemlib( );
						if(R.setnull(name)==0)
						{
							X.liblist.add(R);
							X.save( );
							String newrow[ ] = new String[2];
							newrow[0]=R.name;
							newrow[1]=R.path;
							model.addRow(newrow);
							int a=table.getSelectedRow( );
							if(a!=-1) table.removeRowSelectionInterval(a,a);
							int W = table.getRowCount( )-1;
							table.addRowSelectionInterval(W,W);
						}
						tip.setVisible(false);
					}
				});
				tip.getContentPane( ).add(tipA);
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				textPane.setBackground(SystemColor.window);
				textPane.setText("输入题库名称：");
				textPane.setBounds(20, 50, 300, 16);
				tip.getContentPane( ).add(textPane);
				tip.setVisible(true);
				return;
			}
		});
		
		button.setBounds(18, 304, 117, 29);
		contentPane.add(button);
		

		/**
		 * 题目编辑模块
		 */
		
		JPanel panel = new JPanel();
		panel.setBounds(150, 6, 332, 341);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("题库名称：");
		textPane_1.setEditable(false);
		textPane_1.setBackground(SystemColor.window);
		textPane_1.setBounds(6, 6, 66, 16);
		panel.add(textPane_1);
		
		textField = new JTextField();
		textField.setBounds(69, 0, 130, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JTextArea textPane_2 = new JTextArea();
		textPane_2.setRows(1);
		//textPane_2.setText("题库路径：");
		textPane_2.setEditable(false);
		textPane_2.setBackground(SystemColor.window);
		textPane_2.setBounds(6, 29, 308,35);
		textPane_2.setLineWrap(true);
		//textPane_2.setWrapStyleWord(true);
		panel.add(textPane_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 76, 308, 229);
		panel.add(scrollPane_1);
		
		table_1 = new JTable()
				{
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				};
		scrollPane_1.setViewportView(table_1);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		
		JButton btnNewButton_3 = new JButton("删除");
		btnNewButton_3.setBounds(0, 306, 81, 29);
		panel.add(btnNewButton_3);
		
		JButton button_1 = new JButton("编辑");
		button_1.setBounds(79, 306, 81, 29);
		panel.add(button_1);
		
		JButton button_2 = new JButton("新增");
		button_2.setBounds(156, 306, 81, 29);
		panel.add(button_2);
		
		JButton button_3 = new JButton("保存");
		button_3.setBounds(233, 306, 81, 29);
		panel.add(button_3);
		
		JButton button_4 = new JButton("退出编辑");
		button_4.setBounds(197,0,117,29);
		panel.add(button_4);
		
		//btnNewButton_2是题库列表的编辑按钮
		btnNewButton_2 = new JButton("编辑");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove_all(btnNewButton_3);
				remove_all(button_1);
				remove_all(button_2);
				remove_all(button_3);
				remove_all(button_4);
				int ID = table.getSelectedRow( );
				if(ID==-1) return;
				itemlib H = X.liblist.get(ID);
				H.setfromfile(new StringBuffer(H.path+","+H.name+","));
				String[ ][ ] prob = new String[H.num][2];
				int i=0;
				for(item b:H.lib)
				{
					prob[i][0] = b.problem;
					prob[i][1] = b.answer;
					i++;
				}
				String[ ] title = {"题目","答案"};
				DefaultTableModel model = new DefaultTableModel(prob,title);
				table_1.setModel(model);
				tablecolor(table_1);
				textField.setText(H.name);
				textPane_2.setText("题库路径："+H.path);
				
				//table.setEnabled(false);
				
				JTextField Srow = new JTextField( );
				Srow.setText(Integer.toString(table.getSelectedRow( )));
				
				//删除
				btnNewButton_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int ID = table_1.getSelectedRow( );
						if(ID==-1) return;
						H.remove(ID);
						model.removeRow(ID);
					}
				});
				
				//新增
				button_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JDialog tip = new JDialog(libui.this,true);
						tip.getContentPane().setLayout(null);
						tip.setBounds(100,100,300,200);
						
						JTextArea textPane = new JTextArea();
						textPane.setEditable(false);
						textPane.setBackground(SystemColor.window);
						textPane.setText("题目：");
						textPane.setBounds(20, 50, 30, 16);
						tip.getContentPane( ).add(textPane);
						
						JTextArea textPane2 = new JTextArea();
						textPane2.setEditable(false);
						textPane2.setBackground(SystemColor.window);
						textPane2.setText("答案：");
						textPane2.setBounds(20, 70, 30, 16);
						tip.getContentPane( ).add(textPane2);
						
						JTextField A1 = new JTextField();
						A1.setBounds(60, 50, 220, 16);
						tip.getContentPane( ).add(A1);
						JTextField A2 = new JTextField();
						A2.setBounds(60, 70, 220, 16);
						tip.getContentPane( ).add(A2);
						
						JButton tipA = new JButton("确认");
						tipA.setBounds(120,100,60,20);
						tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								int ID = table_1.getSelectedRow( );
								if(ID==-1) ID=table_1.getRowCount( );
								String prob = A1.getText( );
								String ans = A2.getText( );
								if(prob.contentEquals("")) prob="null";
								if(ans.contentEquals("")) ans="null";
								H.add(prob,ans,ID);
								String rowData[ ] = new String[2];
								rowData[0] = prob;
								rowData[1] = ans;
								model.insertRow(ID,rowData);
								if(ID+1!=table_1.getRowCount( ))
									table_1.removeRowSelectionInterval(ID+1,ID+1);
								tip.setVisible(false);
							}
						});
						tip.getContentPane( ).add(tipA);
						
						tip.setVisible(true);
						return;
					}
				});
				
				//编辑
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JDialog tip = new JDialog(libui.this,true);
						tip.getContentPane().setLayout(null);
						tip.setBounds(100,100,300,200);
						
						JTextArea textPane = new JTextArea();
						textPane.setEditable(false);
						textPane.setBackground(SystemColor.window);
						textPane.setText("题目：");
						textPane.setBounds(20, 50, 30, 16);
						tip.getContentPane( ).add(textPane);
						
						JTextArea textPane2 = new JTextArea();
						textPane2.setEditable(false);
						textPane2.setBackground(SystemColor.window);
						textPane2.setText("答案：");
						textPane2.setBounds(20, 70, 30, 16);
						tip.getContentPane( ).add(textPane2);
						
						int RD = table_1.getSelectedRow( );
						JTextField A1 = new JTextField();
						A1.setBounds(60, 50, 220, 16);
						A1.setText((String)model.getValueAt(RD,0));
						tip.getContentPane( ).add(A1);
						JTextField A2 = new JTextField();
						A2.setBounds(60, 70, 220, 16);
						A2.setText((String)model.getValueAt(RD,1));
						tip.getContentPane( ).add(A2);
						
						JButton tipA = new JButton("确认");
						tipA.setBounds(120,100,60,20);
						tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								int ID = table_1.getSelectedRow( );
								if(ID==-1) ID=table_1.getRowCount( );
								String prob = A1.getText( );
								String ans = A2.getText( );
								if(prob.contentEquals("")) prob="null";
								if(ans.contentEquals("")) ans="null";
								H.ch(prob,ans,ID);
								model.setValueAt(prob,ID,0);
								model.setValueAt(ans,ID,1);
								tip.setVisible(false);
							}
						});
						tip.getContentPane( ).add(tipA);
						
						tip.setVisible(true);
						return;
					}
				});
				
				//保存
				button_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JDialog tip = new JDialog(libui.this,true);
						tip.getContentPane().setLayout(null);
						tip.setBounds(100,100,300,200);
						JButton tipA = new JButton("确认");
						tipA.setBounds(120,100,60,20);
						tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								tip.setVisible(false);
							}
						});
						String name = textField.getText( );
						if(name.contentEquals("")) name="未命名";
						H.name = name;
						H.save( );X.save( );
						int ID = Integer.parseInt(Srow.getText( ));
						table.setValueAt(name,ID,0);
						tip.getContentPane( ).add(tipA);
						JTextPane textPane = new JTextPane();
						textPane.setEditable(false);
						textPane.setBackground(SystemColor.window);
						textPane.setText("题库信息已保存");
						textPane.setBounds(110, 50, 300, 16);
						tip.getContentPane( ).add(textPane);
						tip.setVisible(true);
					}
				});
				
				//退出编辑
				button_4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//table.enable( );
						panel.setVisible(false);
					}
				});
				
				panel.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(74, 259, 61, 24);
		contentPane.add(btnNewButton_2);
		

	}
}
