package com.a7m5.chess;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.apache.commons.validator.routines.InetAddressValidator;

public class MainMenu {

	private JFrame frame;
	private JTextField ipTextField;
	private JTextField portTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GdxChessGame.startServer();
			}
		});
		frame.getContentPane().add(btnStartServer, "2, 2");
		
		JButton btnKillServer = new JButton("Kill Server");
		btnKillServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					GdxChessGame.killServer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnKillServer, "4, 2, left, default");
		
		JButton btnStartClient = new JButton("Start Client");
		btnStartClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
				cfg.title = "Chess";
				cfg.useGL20 = false;
				cfg.width = 512;
				cfg.height = 512;
				
				String address = ipTextField.getText();
				InetAddressValidator v = InetAddressValidator.getInstance();
				if(v.isValid(address)) {
					try {
						int port = Integer.valueOf(portTextField.getText());

						new LwjglApplication(new GdxChessGame(address, port), cfg);
						
					} catch(NumberFormatException e) {
					}
				}
				
			}
		});
		frame.getContentPane().add(btnStartClient, "2, 4");
		
		JLabel lblIp = new JLabel("IP Address");
		frame.getContentPane().add(lblIp, "2, 6");
		
		JLabel lblPort = new JLabel("Port");
		frame.getContentPane().add(lblPort, "4, 6");
		
		ipTextField = new JTextField("127.0.0.1");
		ipTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				String address = ipTextField.getText();
				InetAddressValidator v = InetAddressValidator.getInstance();
				if(v.isValid(address)) {
					ipTextField.setForeground(Color.BLACK);
				} else {
					ipTextField.setForeground(Color.RED);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
		});
		frame.getContentPane().add(ipTextField, "2, 8, 2, 1, fill, default");
		ipTextField.setColumns(10);
		
		portTextField = new JTextField("8082");
		portTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					Integer.valueOf(portTextField.getText());
					portTextField.setForeground(Color.BLACK);
				} catch(NumberFormatException e) {
					portTextField.setForeground(Color.RED);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
		});
		frame.getContentPane().add(portTextField, "4, 8, left, default");
		portTextField.setColumns(10);
		
		JButton btnSettings = new JButton("Settings");
		frame.getContentPane().add(btnSettings, "2, 10");
	}

}