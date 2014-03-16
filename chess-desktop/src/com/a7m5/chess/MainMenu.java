package com.a7m5.chess;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.prefs.Preferences;

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
import javax.swing.JFileChooser;
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
	private JTextField cacheDirectoryTextField;
	private JRadioButton whiteButton;
	private JRadioButton blackButton;
	private Preferences prefs;
	private String cacheDir;
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
		Preferences prefsRoot = Preferences.userRoot();
		prefs = prefsRoot.node("com.a7m5.chess.MainMenu");
		initialize();

		cacheDir = prefs.get("cacheDir", null);
		if(cacheDir != null) {
			if(prepareCacheDirectory()) {
				cacheDirectoryTextField.setForeground(Color.BLACK);
				cacheDirectoryTextField.setText(cacheDir);
			}
		} else {
			cacheDirectoryTextField.setText("Select a cache directory!!!");
			cacheDirectoryTextField.setForeground(Color.RED);
		}
	}

	public void selectCacheDirectory() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnedValue = chooser.showOpenDialog(frame);
		if(returnedValue == JFileChooser.APPROVE_OPTION) {
			cacheDir = chooser.getSelectedFile().getAbsolutePath();
			if(prepareCacheDirectory()) {
				cacheDirectoryTextField.setForeground(Color.BLACK);
				cacheDirectoryTextField.setText(cacheDir);
			}
		}

	}

	public boolean prepareCacheDirectory() {
		boolean success = true;
		File cacheDirectory = new File(cacheDir);
		try {
			if(!cacheDirectory.exists()) {
				success = cacheDirectory.mkdir();
			}
			if(success) {
				File piecesDir = new File(cacheDir + "/pieces/");
				File boardsDir = new File(cacheDir + "/boards/");
				if(!piecesDir.exists()) {
					success = piecesDir.mkdir();
				}
				if(success && !boardsDir.exists()) {
					success = boardsDir.mkdir();
				}
				if(success) {
					updatePreferences();
					return true;
				}
			}
		} catch(SecurityException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void updatePreferences() {
		prefs.put("cacheDir", cacheDir);
		ChessGame3D.setCacheDirectory(cacheDir);
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
				cfg.useGL30 = false;
				cfg.width = 712;
				cfg.height = 512;
				cfg.samples = 4;
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

		cacheDirectoryTextField = new JTextField();
		cacheDirectoryTextField.setEditable(false);
		frame.getContentPane().add(cacheDirectoryTextField, "2, 14, fill, default");
		cacheDirectoryTextField.setColumns(10);

		JButton selectCacheDirectoryButton = new JButton("Select Cache Directory");
		selectCacheDirectoryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectCacheDirectory();
			}
		});
		frame.getContentPane().add(selectCacheDirectoryButton, "4, 14");

		JButton btnSettings = new JButton("Board Editor.");
		frame.getContentPane().add(btnSettings, "2, 16");
		btnSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				/*
				ResourceGrabber myGrab = new ResourceGrabber("C:\\Users\\Peter\\git\\weird-chess\\chess\\assets\\data");
				ChessPieceSet ourSet = new ChessPieceSet(myGrab.getGrabbedPieces());
				 */
				/*
				ResourceThrower myThrow = new ResourceThrower("C:\\Users\\Peter\\git\\weird-chess\\chess\\assets\\data");
				myThrow.createPieceFile(new Queen(null));
				 */


				JOptionPane myOption = new JOptionPane();

				LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
				cfg.title = "Chess Game Editor";
				cfg.useGL30 = false;
				cfg.width = 512+300;	// Larger sidebar for editor.
				cfg.height = 512;
				new LwjglApplication(new ChessGameEditor(), cfg);



			}
		});

	}

}
