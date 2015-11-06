package gui.old;
import java.awt.*;
import java.net.*;

import equipment.Card;
import equipment.Deck;
import equipment.Rank;
import equipment.Suit;
class CardImage {
	static Image image(int i) {
		return images[i];
	}
	static void loadImages(Deck pokerDeck,Component component) {
		Card[] cards=pokerDeck.cards();
		for(int i=0;i<cards.length;i++) {
			Card card=cards[i];
			int n=cardToImageIndex(card);
			Image image=get(component,toUrl(card.toCharacters()));
			CardImage.images[n]=image;
		}
		Image image=get(component,toUrl("e"));
		CardImage.images[images.length-1]=image;
	}
	static Image cardToImage(Card card) {
		int n=cardToImageIndex(card);
		return images[n];
	}
	static Image backImage() {
		return images[backImageIndex()];
	}
	private static String toFilename(String name) {
		return path+name+".gif";
	}
	private static URL toUrl(String name) {
		return clazz.getResource("big/"+name+".gif");
	}
	private static int cardToImageIndex(Card card) {
		Rank rank=card.rank();
		Suit suit=card.suit();
		int n=rank.ordinal()*4+suit.ordinal()-1;
		return n;
	}
	private static int backImageIndex() {
		return images.length-1;
	}
	private static Image get(Component component,URL url) {
		System.err.println("loading "+url);
		Image image=Toolkit.getDefaultToolkit().getImage(url);
		return load(component,image);
	}
	private static Image get(Component component,String filename) {
		System.err.println("loading "+filename);
		Image image=Toolkit.getDefaultToolkit().getImage(filename);
		return load(component,image);
	}
	private static Image load(Component component,Image image) {
		Image scaled=null;
		MediaTracker tracker=new MediaTracker(component);
		tracker.addImage(image,0);
		boolean trackerError=false;
		try {
			tracker.waitForID(0);
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		if(trackerError=tracker.isErrorAny()) System.err.println("tracker error");
		if(false) return trackerError?null:image;
		int width=image.getWidth(null);
		int height=image.getHeight(null);
		Dimension d=component.getSize();
		scaled=image.getScaledInstance(d.width,d.height,0);
		tracker.addImage(image,1);
		try {
			tracker.waitForID(1);
		} catch(InterruptedException e) {}
		return scaled;
	}
	private static final Class<CardImage> clazz=CardImage.class;
	private static final String path="d:/usr/ray/java/poker/image/big/";
	private static final Image[] images=new Image[60+1]; // magic #!!!!!
}
