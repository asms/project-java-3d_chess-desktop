// Peter's test comment.

package com.a7m5.chess;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.a7m5.chess.chesspieces.ChessOwner;
import com.a7m5.chess.editor.ChessGameEditor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.apache.commons.validator.routines.InetAddressValidator;

import javax.swing.JRadioButton;

public class MainMenu {

	private JFrame frame;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField serverPortTextField;
	private JRadioButton whiteButton;
	private JRadioButton blackButton;

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


	public MainMenu() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				try {
					int port = Integer.valueOf(serverPortTextField.getText());

					ChessGame3D.startServer(port);

				} catch(NumberFormatException e) {
				}
			}
		});
		frame.getContentPane().add(btnStartServer, "2, 2");

		JButton btnKillServer = new JButton("Kill Server");
		btnKillServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ChessGame3D.killServer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnKillServer, "4, 2, fill, default");

		JLabel label_1 = new JLabel("Port");
		frame.getContentPane().add(label_1, "2, 4");

		serverPortTextField = new JTextField("8082");
		frame.getContentPane().add(serverPortTextField, "4, 4, fill, default");
		serverPortTextField.setColumns(10);
		serverPortTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					Integer.valueOf(serverPortTextField.getText());
					serverPortTextField.setForeground(Color.BLACK);
				} catch(NumberFormatException e) {
					serverPortTextField.setForeground(Color.RED);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});

		JButton btnStartClient = new JButton("Start Client");
		btnStartClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
				cfg.title = "Chess";
				cfg.useGL20 = false;
				cfg.width = 512 + 200;
				cfg.height = 512;

				ChessOwner chessOwner = whiteButton.isSelected() ? ChessOwner.WHITE : ChessOwner.BLACK;

				String address = ipTextField.getText();
				InetAddressValidator v = InetAddressValidator.getInstance();
				if(v.isValid(address)) {
					try {
						int port = Integer.valueOf(portTextField.getText());

						new LwjglApplication(new ChessGame3D(chessOwner, address, port), cfg);

					} catch(NumberFormatException e) {
					}
				}

			}
		});
		frame.getContentPane().add(btnStartClient, "2, 6");

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

		JLabel lblNewLabel = new JLabel("IP Address");
		frame.getContentPane().add(lblNewLabel, "2, 8");

		JLabel lblPort = new JLabel("Port");
		frame.getContentPane().add(lblPort, "4, 8");
		frame.getContentPane().add(ipTextField, "2, 10");

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
		frame.getContentPane().add(portTextField, "4, 10, fill, fill");
		portTextField.setColumns(10);

		whiteButton = new JRadioButton("White");
		whiteButton.setSelected(true);
		frame.getContentPane().add(whiteButton, "2, 12");

		blackButton = new JRadioButton("Black");
		frame.getContentPane().add(blackButton, "4, 12");

		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(whiteButton);
		radioButtonGroup.add(blackButton);

		JButton btnSettings = new JButton("Chess Editor");
		frame.getContentPane().add(btnSettings, "2, 14");
		btnSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane myOption = new JOptionPane();
				try{
					int requestedBoardSize = Integer.parseInt(myOption.showInputDialog("Enter the desired board size. No larger than 32 "));
					if((8 <= requestedBoardSize) && (32 >= requestedBoardSize)){
						LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
						cfg.title = "Chess Game Editor";
						cfg.useGL20 = false;
						cfg.width = 512+400;	// Larger sidebar for editor.
						cfg.height = 512;
						new LwjglApplication(new ChessGameEditor(requestedBoardSize), cfg);
					} else {
						myOption.showMessageDialog(null, "That was bad input. Your requested board size, " + requestedBoardSize + ", is not a number between 8 and 32.");
					}
				} catch (NumberFormatException e1){
					myOption.showMessageDialog(null, "That was bad input. Not a valid number.");
				}

			}
		});

	}

}
