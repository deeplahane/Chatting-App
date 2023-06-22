package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar; 

public class Client implements ActionListener{
	
	JTextField textpad;
	static JPanel a1;
	static DataOutputStream dout;
	//vertically align the message one after another
	static Box vertical = Box.createVerticalBox();
	
	static JFrame f = new JFrame();
	
	Client(){
		
		f.setLayout(null);
		
		//----------header part start---------
		//creating the header
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		p1.setLayout(null);
		f.add(p1);
		
		//for back button
		ImageIcon i1 = new ImageIcon("C:/Users/deepl/eclipse-workspace/ChattingApplication/src/icons/3.png");
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5,20,25,25);
		p1.add(back);
		
		//for profile image
		ImageIcon i4 = new ImageIcon("C:/Users/deepl/eclipse-workspace/ChattingApplication/src/icons/2.png");
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40,10,50,50);
		p1.add(profile);
		
		//for video image
		ImageIcon i7 = new ImageIcon("C:/Users/deepl/eclipse-workspace/ChattingApplication/src/icons/video.png");
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(310,23,30,30);
		p1.add(video);
		
		//for phone image
		ImageIcon i10 = new ImageIcon("C:/Users/deepl/eclipse-workspace/ChattingApplication/src/icons/phone.png");
		Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(360,23,30,30);
		p1.add(phone);
		
		//for morevert Icon
		ImageIcon i13 = new ImageIcon("C:/Users/deepl/eclipse-workspace/ChattingApplication/src/icons/3icon.png");
		Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel morevert = new JLabel(i15);
		morevert.setBounds(410,25,10,25);
		p1.add(morevert);
		
		//for user1 name
		JLabel name  = new JLabel("Bunty");
		name.setBounds(105,20,100,18);
		name.setFont(new Font("Aerial",Font.BOLD,20));
		name.setForeground(Color.WHITE);
		p1.add(name);
		
		//for active status 
		JLabel status  = new JLabel("Active Now");
		status.setBounds(110,40,100,18);
		status.setFont(new Font("Aerial",Font.BOLD,11));
		status.setForeground(Color.WHITE);
		p1.add(status);
		
		//Adding close functionality to back button.
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		//----------header part complete---------
		
		//----------main screen part start--------
		//creating main screen
		a1 = new JPanel();
		a1.setBounds(0,70,450,570);
		f.add(a1);
		//----------main screen part complete--------
		
		//----------text pad part start--------
		//creating textpad
		textpad = new JTextField();
		textpad.setBounds(0,640,350,63);
		textpad.setFont(new Font("Aerial",Font.BOLD,20));
		f.add(textpad);		
		
		//creating send btn
		JButton send = new JButton("Send");
		send.setBounds(350,640,100,63);
		send.setBackground(new Color(7,94,84));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("Aerial",Font.BOLD,17));
		send.addActionListener(this);
		f.add(send);
		//----------text pad part complete--------
		
		//creating the frame
		f.setSize(450,700);
		f.setLocation(800,5);
		f.getContentPane().setBackground(Color.WHITE);
		f.setUndecorated(true);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	try {
		//sending message
		String out = textpad.getText();
		
		
		JPanel p2 = formatLabel(out);

		//positioning the senders message to right
		JPanel right = new JPanel(new BorderLayout());
		right.add(p2,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
//		a1.setLayout(new BorderLayout());
		a1.add(vertical,BorderLayout.PAGE_START);
		
		dout.writeUTF(out);
		textpad.setText("");
		f.repaint();
		f.invalidate();
		f.validate();
	
	}catch(Exception i) {
		i.getStackTrace();
	}
	}
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JLabel output = new JLabel("<html><p style=\"width:150px\">"+out+"</p><html/>");	
		output.setBackground(new Color(37,211,102));
		output.setFont(new Font("Aerial",Font.BOLD,18));
		output.setBorder(new EmptyBorder(15,15,15,50));
		output.setOpaque(true);
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time  = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
	}
	
	public static void main(String[] args) {
		 new Client();//anonymous object
		 
		 try {
			 Socket s = new Socket("127.0.0.1",6001);
			 DataInputStream din  = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while(true) {
					a1.setLayout(new BorderLayout());																							
					String msg = din.readUTF();
					JPanel panel = formatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel,BorderLayout.LINE_START);
					vertical.add(left);
					vertical.add(Box.createVerticalStrut(15));
					a1.add(vertical,BorderLayout.LINE_START);
					
					f.validate();
				}
				
		 }catch(Exception e){
			 e.getStackTrace();
		 }
	}
}



