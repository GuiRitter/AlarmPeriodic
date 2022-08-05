package io.github.guiritter.alarm;

import static java.awt.BorderLayout.CENTER;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Alarm {

	private static int compareLast = -1;

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");

	static {
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	public static void main(String args[]) {
		var frame = new JFrame("Alarm");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		frame.getContentPane().add(new JLabel("Alarm"), CENTER);

		frame.setSize(160, 50);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
		
			@Override
			public void run() {
				
				var now = new Time(System.currentTimeMillis());
				// var nowI = Instant.now();

				var compareNow = now.getMinutes() / 20;
				// var compareNowI = nowI.get(MINUTE_OF_HOUR) / 20;

				// out.println("now\t" + now);

				// out.println("compareNow\t" + compareNow);

				// out.println("compareLast\t" + compareLast);

				// out.println("now.getMinutes() % 20\t" + now.getMinutes() % 20);

				// out.println("compareLast != compareNow\t" + (compareLast != compareNow));

				// out.println("(now.getMinutes() % 20) == 0\t" + ((now.getMinutes() % 20) == 0));

				// out.println("(compareLast != compareNow) && ((now.getMinutes() % 20) == 0)\t" + ((compareLast != compareNow) && ((now.getMinutes() % 20) == 0)));

				// out.println();

				if ((compareLast != compareNow) && ((now.getMinutes() % 20) == 0)) {
					
					var string = SIMPLE_DATE_FORMAT.format(now);
					// var stringI = SIMPLE_DATE_FORMAT.format(nowI);

					var frame = new JFrame(string);
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

					var label = new JLabel(string);
					label.addMouseMotionListener(new MouseMotionAdapter(){
		
						@Override
						public void mouseMoved(MouseEvent e) {
							JFrame frame2 = (JFrame) ((JLabel) e.getSource()).getParent().getParent().getParent().getParent();
							frame2.dispatchEvent(new WindowEvent(frame2, WINDOW_CLOSING));
							super.mouseMoved(e);
						}
					});
					frame.getContentPane().add(label, CENTER);
			
					frame.setSize(160, 50);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					compareLast = compareNow;
				}
			}
		}, 0, 5, java.util.concurrent.TimeUnit.SECONDS);
	}
}
