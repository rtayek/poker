package gui.charts;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
//import equipment.*;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import equipment.Rank;
import poker.holdem.HoldemHand;
import poker.holdem.HoldemHand.Type;
public class Open extends JFrame {
	public static void main(String[] args) {
		Open Open=new Open();
		Open.build();
		Open.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Open.pack();
		Open.setVisible(true);
	}
	public void build() {
		Container pane=getContentPane();
		pane.setPreferredSize(new Dimension(720, 720));
		pane.setLayout(new GridLayout(n,n));
		if(false) {
			for(HoldemHand h:HoldemHand.values()) {
				JButton button=new JButton(""+h);
				pane.add(button);
			}
			return;
		}
		System.out.println(Arrays.asList(Rank.values()));
		for(int i=0;i<n;i++)
			for(int j=i;j<n;j++) {
				Rank r1=Rank.fromInt(i+2),r2=Rank.fromInt(j+2);
				HoldemHand hand=null;
				if(i==j) {
					hands[i][j]=HoldemHand.from(r1,r2,HoldemHand.Type.pair);
					buttons[i][j]=new JButton(""+hands[i][j]);
				} else {
					hands[j][i]=HoldemHand.from(r2,r1,HoldemHand.Type.suited);
					buttons[j][i]=new JButton(""+hands[j][i]);
					hands[i][j]=HoldemHand.from(r2,r1,HoldemHand.Type.offsuit);
					buttons[i][j]=new JButton(""+hands[i][j]);
				}
			}
		for(int l=0;l<n*n;l++) {
			int i0=l/n,j0=l%n;
			int i=n-i0-1,j=n-j0-1;
			pane.add(buttons[i][j]); // flip both
			double hue=hands[i][j].value()/HoldemHand.aceAce.value();
			hue*=.5;
			if(hue<0) hue=0;
			int rgb=Color.HSBtoRGB((float)hue,(float).8,(float).8);
			Color color=new Color(rgb);
			buttons[i][j].setBackground(color);
			System.out.println(i+" "+j+" "+hands[i][j]+" "+hands[i][j].value()+" "+hue);
		}
		pane.addComponentListener(new ComponentListener() {
			public void componentMoved(ComponentEvent event) {}
			public void componentHidden(ComponentEvent event) {}
			public void componentShown(ComponentEvent event) {}
			@Override public void componentResized(ComponentEvent event) {
				int width=pane.getWidth();
				int height=pane.getHeight();
				if(width==height) {
					return;
				} else if(width==oldWidth) {
					width=height;
				} else if(height==oldHeight) {
					height=width;
				} else {
					int max=Math.max(width,height);
					width=height=max;
				}
				pane.setPreferredSize(new Dimension(width,height));
				pack();
			}
		});
	}
	JButton[][] buttons=new JButton[n][n];
	HoldemHand[][] hands=new HoldemHand[n][n];
	int oldWidth = 720;
	int oldHeight = 720;
	static final int n=13;
}
