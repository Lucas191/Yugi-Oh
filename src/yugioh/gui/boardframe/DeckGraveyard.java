package src.yugioh.gui.boardframe;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DeckGraveyard extends JPanel {
	private SizeLabelLayout.GraveyardButton graveyard;
	private SizeLabelLayout.DeckButton deck;

	public DeckGraveyard(boolean active){
		setLayout(new BorderLayout());
		graveyard = new SizeLabelLayout.GraveyardButton(active);
		deck = new SizeLabelLayout.DeckButton(active);
		if(!active){
			add(graveyard, BorderLayout.SOUTH);
			add(deck, BorderLayout.NORTH);
		}else{
			add(graveyard, BorderLayout.NORTH);
			add(deck, BorderLayout.SOUTH);
		}
		setPreferredSize(new Dimension(SizeLabelLayout.CardButton.getDimension('W'),300));
		validate();
	}

	public SizeLabelLayout.GraveyardButton getGraveyard() {
		return graveyard;
	}

	public void setGraveyard(SizeLabelLayout.GraveyardButton graveyard) {
		this.graveyard = graveyard;
	}

	public SizeLabelLayout.DeckButton getDeck() {
		return deck;
	}

	public void setDeck(SizeLabelLayout.DeckButton deck) {
		this.deck = deck;
	}
}