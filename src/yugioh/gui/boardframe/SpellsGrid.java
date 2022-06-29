package src.yugioh.gui.boardframe;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import src.yugioh.cards.Card;
import src.yugioh.cards.spells.SpellCard;

@SuppressWarnings("serial")
public class SpellsGrid extends JPanel {
	private SizeLabelLayout.SpellButton [] spellsButtons;
	private ArrayList<SpellCard> spellsArea;
	private boolean active;
	public SpellsGrid(boolean active) {
		setLayout(new GridLayout(1, 5));
		spellsButtons = new SizeLabelLayout.SpellButton [5];
		this.active = active;
		for(int i = 0; i<5 ; i++){
			spellsButtons[i]= new SizeLabelLayout.SpellButton();
			add(spellsButtons[i]);
		}
		setPreferredSize(new Dimension(475,150));
		validate();
	}
	public void updateSpellsArea() {
		removeAll();
		if(active){
			spellsArea = Card.getBoard().getActivePlayer().getField().getSpellArea();
		
		for (int i = 0; i < 5; i++) {
			if(i<spellsArea.size()){
				spellsButtons[i] = new SizeLabelLayout.SpellButton(spellsArea.get(i));
				add(spellsButtons[i]);
			}else add(new SizeLabelLayout.SpellButton());
		}
		}else{
			spellsArea = Card.getBoard().getOpponentPlayer().getField().getSpellArea();
			for (int i = 0; i < 5; i++) {
				if(i<spellsArea.size()){
					SizeLabelLayout.SpellButton addedSpell = new SizeLabelLayout.SpellButton();
					addedSpell.setIcon(new ImageIcon("images/AttackMode.png"));
					spellsButtons[i] = addedSpell;
					add(spellsButtons[i]);
				}else add(new SizeLabelLayout.SpellButton());
			}
		}
		repaint();
		validate();
	}
	public SizeLabelLayout.SpellButton[] getSpellsButtons() {
		return spellsButtons;
	}
	public void setSpellsButtons(SizeLabelLayout.SpellButton[] spellsButtons) {
		this.spellsButtons = spellsButtons;
	}
}