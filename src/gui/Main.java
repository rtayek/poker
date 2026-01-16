package gui;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;
import equipment.Deck;
import poker.machine.*;
import poker.machine.mvc.*;
public class Main extends MainGui {
	Main(MyJApplet applet) {
		super(applet);
		// if(applet==null) setPlaf=false;
		// plaf(this);
		addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
				//System.out.println(getSize());
			}
		});
		Container c=frame.getContentPane();
		//c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	static void plaf(final Container c) {
		if(setPlaf)
			try {
				SwingUtilities.invokeAndWait(() -> {
					try {
						UIManager.setLookAndFeel("HighContrastLAF");
						SwingUtilities.updateComponentTreeUI(c);
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
					} catch(InstantiationException e) {
						e.printStackTrace();
					} catch(IllegalAccessException e) {
						e.printStackTrace();
					} catch(UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				});
			} catch(InterruptedException e1) {
				e1.printStackTrace();
			} catch(InvocationTargetException e1) {
				e1.printStackTrace();
			}
	}
	@Override public void addContent() {
		if(frame!=null)
			frame.setTitle("Video Poker");
		swingBindings=new SwingBindings();
		add(swingBindings);
		try {
			// PokerCardFactory.instance.hack();
			final Deck pokerDeck=new Deck();
			PokerMachine pokerMachine=new PokerMachine(pokerDeck,new HighPayoff());
			pokerMachine.addObserver(swingBindings);
			controller=new CommandLineController(pokerMachine);
			CommandLineView commandLineView=new CommandLineView(pokerMachine,false);
			pokerMachine.addObserver(commandLineView);
			swingBindings.pokerMachine=pokerMachine;
			swingBindings.controller=controller;
			this.commandLineView=commandLineView;
			commandLineReady.countDown();
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println(getSize());
	}
	public static void main(String[] args) {
		Main main=new Main(null);
		main.awaitCommandLineReady();
		main.controller.run();
	}
	private void awaitCommandLineReady() {
		try {
			commandLineReady.await();
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	CommandLineView commandLineView;
	private SwingBindings swingBindings;
	private PokerMachine pokerMachine;
	private CommandLineController controller;
	private final CountDownLatch commandLineReady=new CountDownLatch(1);
	static boolean setPlaf=true;
	private static final long serialVersionUID=1L;
}
