package gui.old;
import java.awt.*;
import equipment.Card;
class CardCanvas extends java.awt.Canvas // was inner - vc 2.0 buggy?
	{
	CardCanvas() { }
	public void callRepaint()
		{
		repaint();
		}
	public void repaint()
		{
		//System.err.println("repaint "+this+" "+card+" "+Thread.currentThread()); 
		super.repaint();
		}
	public void update(Graphics g) { paint(g); }
	public void	paint(Graphics g)
		{
		//System.err.println("paint "+this+" "+card+" "+Thread.currentThread()); 
		if(card!=null)
			{
			Image image=CardImage.cardToImage(card);
			//System.err.println("image: "+image); 
			if(image!=null)
				g.drawImage(image,0,0,this);
				else g.drawString(card.toCharacters(),5,96);
			}
			else
			{
			Image image=CardImage.backImage();
			if(image!=null)
				g.drawImage(image,0,0,this);
				else g.drawString("X",5,96);
			}
		}
	public Card getCard() { return card; }
	public void setCard(Card card) { this.card=card; }
	private Card card;
	static final long serialVersionUID=0;
	}

