package test_mode;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import library.itemlib;
import library.liblist;
import library.item;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;


public class test_ui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private itemlib selected_tiku;
	private Integer ongoing_qa_id;
	private Integer total_current_qa;
	private boolean is_answer_shown;
	public HashSet<item> wrong_memo;
	private boolean has_assign_qas;

	static liblist X = new liblist( );
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		X.load( );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test_ui frame = new test_ui(X);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test_ui(liblist X) {
		wrong_memo = new HashSet<item>();
		ongoing_qa_id = 0;
		total_current_qa = 1;
		has_assign_qas = false;
		
		textField = new JTextField();
		JButton btnNewButton_show_answer = new JButton("显示答案");
		JButton btnNewButton_last = new JButton("下一题");
		JButton btnNewButton_next = new JButton("上一题");
		JButton btnNewButton_addwrong = new JButton("添加错题");
		JTextArea textArea_2 = new JTextArea();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 6, 110, 323);
		contentPane.add(scrollPane);
		
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
		JTable table = new JTable(model)
				{
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				};
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(128, 6, 208, 323);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_question = new JTextArea();
		textArea_question.setWrapStyleWord(true);
		textArea_question.setLineWrap(true);
		textArea_question.setEditable(false);
		scrollPane_1.setViewportView(textArea_question);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(348, 5, 229, 121);
		contentPane.add(scrollPane_2);
		
		
		JTextArea textArea_answer = new JTextArea();
		textArea_answer.setWrapStyleWord(true);
		textArea_answer.setLineWrap(true);
		textArea_answer.setEditable(false);
		scrollPane_2.setViewportView(textArea_answer);
		
		JButton btnNewButton = new JButton("开始测试!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Integer id = table.getSelectedRow();
				selected_tiku = X.liblist.get(id);  // itemlib
				has_assign_qas = true;
				ongoing_qa_id = 0;
				total_current_qa = selected_tiku.lib.size();
				is_answer_shown = false;
				// btnNewButton_show_answer.setText("show answer");
				// 显示题目
				textArea_answer.setText(selected_tiku.lib.get(ongoing_qa_id).problem);	  
				// 每更新一个题目，要修改textfield进度条
				textField.setText(Integer.toString(ongoing_qa_id + 1) + '\\' + Integer.toString(total_current_qa));
				btnNewButton_last.setText("上一题");
				btnNewButton_next.setText("下一题");
				btnNewButton_show_answer.setText("显示答案");
				btnNewButton_addwrong.setText("添加错题");
				textArea_2.setText("在这里试着写答案：");
				return ;
			}
		});
		
		
		btnNewButton.setBounds(6, 363, 117, 29);
		contentPane.add(btnNewButton);
		
		
		btnNewButton_addwrong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (has_assign_qas == false)
				{
					btnNewButton_addwrong.setText("请选择题库");
					return;
				}
			    if(wrong_memo.contains(selected_tiku.lib.get(ongoing_qa_id)))
			    {
			    	btnNewButton_addwrong.setText("已添加过");
					return;	
			    }
				wrong_memo.add(selected_tiku.lib.get(ongoing_qa_id));
				btnNewButton_addwrong.setText("添加成功");
			}
		});
		
		btnNewButton_addwrong.setBounds(460, 341, 117, 29);
		contentPane.add(btnNewButton_addwrong);
		
		btnNewButton_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(has_assign_qas == false)
				{
					btnNewButton_last.setText("请选择题库");
			        return; 
				}
				if(ongoing_qa_id == 0) {
					btnNewButton_last.setText("前面没有了");
				}
				else {
					ongoing_qa_id -= 1;
					textArea_answer.setText(selected_tiku.lib.get(ongoing_qa_id).problem);	  
					textField.setText(Integer.toString(ongoing_qa_id + 1) + '\\' + Integer.toString(total_current_qa));
					if(is_answer_shown)
					{
						is_answer_shown = false;
						textArea_question.setText("");	  
						btnNewButton_show_answer.setText("显示答案");	
					}
					btnNewButton_next.setText("下一题");
					btnNewButton_addwrong.setText("添加错题本");
					textArea_2.setText("在这里试着写答案：");
				}
			}
		});
		
		
		
		btnNewButton_last.setBounds(300, 382, 117, 29);
		contentPane.add(btnNewButton_last);
		
		btnNewButton_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(has_assign_qas == false) {
					btnNewButton_next.setText("请选择题库");
					return;
				}
				if(ongoing_qa_id == total_current_qa - 1)
				{
					btnNewButton_next.setText("测试结束！");
				}
				else {
					ongoing_qa_id += 1;
					textArea_answer.setText(selected_tiku.lib.get(ongoing_qa_id).problem);	  
					textField.setText(Integer.toString(ongoing_qa_id + 1) + '\\' + Integer.toString(total_current_qa));
					if(is_answer_shown)
					{
						is_answer_shown = false;
						textArea_question.setText("");	  
						btnNewButton_show_answer.setText("显示答案");	
					}
					btnNewButton_last.setText("上一题");
					btnNewButton_addwrong.setText("添加错题");
					textArea_2.setText("在这里试着写答案：");
				}
			}
		});
		
		btnNewButton_next.setBounds(460, 382, 117, 29);
		contentPane.add(btnNewButton_next);
		
		btnNewButton_show_answer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(has_assign_qas == false)
				{
					btnNewButton_show_answer.setText("请选择题库");
					return ;
				}
				if(is_answer_shown) {  // answer is shown, hide answer
					is_answer_shown = false;
					textArea_question.setText("");	  
					btnNewButton_show_answer.setText("显示答案");	
				}
				else {  // answer is not shown, show it
				    is_answer_shown = true;
				    textArea_question.setText(selected_tiku.lib.get(ongoing_qa_id).answer);
				    btnNewButton_show_answer.setText("隐藏答案");	
				}
			}
			
		});
		
		
		btnNewButton_show_answer.setBounds(300, 341, 117, 29);
		contentPane.add(btnNewButton_show_answer);
		
		textField.setEditable(false);
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(348, 138, 229, 191);
		contentPane.add(scrollPane_3);
	
		textArea_2.setWrapStyleWord(true);
		textArea_2.setLineWrap(true);
		// textArea_2.setText("try");
		scrollPane_3.setViewportView(textArea_2);
		
		textField.setBounds(128, 345, 72, 17);
		contentPane.add(textField);
		textField.setColumns(8);
		
		
	}
}
