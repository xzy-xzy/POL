package UI;
import java.awt.EventQueue;
import java.io.File;

import library.*;

public class test
{
		static liblist X = new liblist( );
		public static void main(String[] args) {
			X.load( );
			//X.save( );
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						libui frame = new libui(X);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
