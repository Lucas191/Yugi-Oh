package src.yugioh.gui.boardframe;

import javax.swing.JPanel;

import src.yugioh.cards.*;
import src.yugioh.cards.spells.SpellCard;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class HandPanel extends JPanel {
	private ArrayList<SizeLabelLayout.CardButton> handList ;
	private ArrayList<Card> hand;
	private boolean active;

	public HandPanel(boolean active) {
		this.active = active;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(1300,SizeLabelLayout.CardButton.getDimension('H')));
		setOpaque(false);
		updateHand();
		validate();
	}

	public void updateHand() {
		removeAll();
		handList = new ArrayList<SizeLabelLayout.CardButton>();
		if(active){
			hand = Card.getBoard().getActivePlayer().getField().getHand();
			for(int i =0; i< hand.size(); i++){
				if(hand.get(i) instanceof MonsterCard){
					handList.add(new SizeLabelLayout.MonsterButton((MonsterCard)hand.get(i)));
				}
				if(hand.get(i) instanceof SpellCard){
					handList.add(new SizeLabelLayout.SpellButton((SpellCard)hand.get(i)));
				}
			add(handList.get(i));
			}
		}
		else {
			hand = Card.getBoard().getOpponentPlayer().getField().getHand();
			for(int i =0; i< hand.size(); i++){
				handList.add(new SizeLabelLayout.MonsterButton());
				add(handList.get(i));
			}
		}
	}
}