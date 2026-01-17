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
		if(frame!=null) {
			Container content=frame.getContentPane();
			content.removeAll();
			content.setLayout(new BorderLayout());
			content.add(swingBindings,BorderLayout.CENTER);
			content.revalidate();
			content.repaint();
		} else {
			setLayout(new BorderLayout());
			removeAll();
			add(swingBindings,BorderLayout.CENTER);
		}
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
		if(frame!=null)
			SwingUtilities.invokeLater(this::enableFullScreen);
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
	private void enableFullScreen() {
		if(frame==null) return;
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds=ge.getMaximumWindowBounds();
		int maxWidth=Math.max(1,bounds.width/2);
		int maxHeight=Math.max(1,bounds.height/2);
		double targetAspect=aspectRatio>0?aspectRatio:(maxWidth/(double)maxHeight);
		int width=maxWidth;
		int height=(int)Math.round(width/targetAspect);
		if(height>maxHeight) {
			height=maxHeight;
			width=(int)Math.round(height*targetAspect);
		}
		int x=bounds.x+(bounds.width-width)/2;
		int y=bounds.y+(bounds.height-height)/2;
		frame.setBounds(x,y,width,height);
		frame.validate();
	}
	CommandLineView commandLineView;
	private SwingBindings swingBindings;
	private PokerMachine pokerMachine;
	private CommandLineController controller;
	private final CountDownLatch commandLineReady=new CountDownLatch(1);
	static boolean setPlaf=true;
	static final float aspectRatio=2f;
	private static final long serialVersionUID=1L;
}
