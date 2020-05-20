package UI;
import library.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class wrong_memo_ui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnNewButton;
	public itemlib memo_itemlib;
	/*
	private static liblist X = new liblist();
	
	public static void main(String[] args) {
		X.load( );
		//X.save( );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wrong_memo_ui frame = new wrong_memo_ui(X.liblist.get(0));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public wrong_memo_ui(ArrayList<item> I, String itemlibname,liblist X,DefaultTableModel Tmodel) {
		memo_itemlib = new itemlib();
		memo_itemlib.lib = new ArrayList<item>(I);
		memo_itemlib.num = memo_itemlib.lib.size();
	    memo_itemlib.name = itemlibname;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(45, 51, 345, 155);
		contentPane.add(scrollPane);
		
		String[ ][ ] infoindex = new String[memo_itemlib.num][2];
		int i=0;
		for(item e: memo_itemlib.lib)
		{
			infoindex[i][0] = e.problem;
			infoindex[i][1] = e.answer;
			i++;
		}
		String[ ] title = {"问题","答案"};
		DefaultTableModel model = new DefaultTableModel(infoindex,title);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("错题列表");
		textField.setBounds(175, 13, 67, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("查看详情");
		btnNewButton.setBounds(23, 231, 117, 29);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ID = table.getSelectedRow( );
				JTextPane textPane = new JTextPane();
				JDialog tip = new JDialog(wrong_memo_ui.this,true);
				tip.getContentPane().setLayout(null);
				tip.setBounds(150,150,350,200);
				textPane.setEditable(false);
				textPane.setBackground(SystemColor.window);
				textPane.setText(memo_itemlib.lib.get(ID).problem + '\n' + memo_itemlib.lib.get(ID).answer);
				textPane.setBounds(20,20,310,160);
				tip.getContentPane( ).add(textPane);
				tip.setVisible(true);
			}
		});
		
		JButton btnNewButton_1 = new JButton("错题存档");
		btnNewButton_1.setBounds(163, 231, 117, 29);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextPane textPane = new JTextPane();
				JTextPane textPane_1 = new JTextPane();
				JDialog tip = new JDialog(wrong_memo_ui.this,true);
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
				textPane.setText("键入保存文件名，可加载为题库");
				textPane.setBounds(75, 50, 300, 16);
				textPane.setVisible(true);
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				textPane_1.setText("错题_" + memo_itemlib.name + "_" + formatter.format(date) + ".txt");
				textPane_1.setBounds(45, 80, 250, 16);
				tip.getContentPane( ).add(textPane);
				tip.getContentPane().add(textPane_1);
				tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								memo_itemlib.name += "错题";
								memo_itemlib.path = "./src/library/" + textPane_1.getText();
								memo_itemlib.save();
								X.liblist.add(memo_itemlib);X.save( );
								String newrow[ ] = new String[2];
								newrow[0]=memo_itemlib.name;
								newrow[1]=memo_itemlib.path;
								Tmodel.addRow(newrow);
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
		
		JButton btnNewButton_2 = new JButton("删除该题");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ID = table.getSelectedRow( );
				JTextPane textPane = new JTextPane();
				JDialog tip = new JDialog(wrong_memo_ui.this,true);
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
				textPane.setText("你确定要删除该错题吗？");
				textPane.setBounds(75, 50, 300, 16);
				tip.getContentPane( ).add(textPane);
				if(ID==-1) return;
				tipA.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								memo_itemlib.lib.remove(ID);
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
		btnNewButton_2.setBounds(302, 231, 117, 29);
		contentPane.add(btnNewButton_2);
	}
}
