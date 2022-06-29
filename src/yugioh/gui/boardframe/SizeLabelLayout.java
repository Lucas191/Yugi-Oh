package src.yugioh.gui.boardframe;

import src.yugioh.board.player.Phase;
import src.yugioh.cards.Card;
import src.yugioh.cards.Location;
import src.yugioh.cards.MonsterCard;
import src.yugioh.cards.spells.ChangeOfHeart;
import src.yugioh.cards.spells.MagePower;
import src.yugioh.cards.spells.SpellCard;
import src.yugioh.exceptions.IllegalSpellTargetException;
import src.yugioh.exceptions.WrongPhaseException;
import src.yugioh.gui.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;


public class SizeLabelLayout {
    public static class SpellButton extends CardButton implements ActionListener {
        private SpellCard spell;

        public SpellButton() {
            super(spellIcon);
        }

        public SpellButton(SpellCard spell) {
            super(new ImageIcon("images/"+spell.getName()+".png"));
            this.spell = spell;
            setToolTipText(spell.getName()+"\n"+spell.getDescription());
            setVisible(true);
            addActionListener(this);
            validate();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(spell.getLocation()== Location.HAND)
                new HandOptionsFrame(false,spell);
            else
            if(spell.getLocation()==Location.FIELD)
                spellInFieldAction();
        }

        private void spellInFieldAction() {
            if((spell instanceof ChangeOfHeart || spell instanceof MagePower)){
                GUI.getBoardFrame().setSpellToActivate(spell);
                new ConfirmFrame("Por favor, escolha um monstro para ativar o feitiço");
            }else{
                try {
                    Card.getBoard().getActivePlayer().activateSpell(spell, null);
                } catch (Exception e2) {
                    GUI.errorFrame(e2);
                }
            }
            GUI.getBoardFrame().updateBoardFrame();
        }
    }

    public static class EndTurnButton extends JButton implements ActionListener{

        public EndTurnButton(){
            super("Fim do Turno",new ImageIcon("images/EndTurn.jpg"));
            setPreferredSize(new Dimension(300,190));
            setHorizontalTextPosition(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.BOTTOM);
            setFont(new Font("", Font.ITALIC, 20));
            //setForeground(java.awt.Color.WHITE);
            setForeground(Color.ORANGE);
            setBackground(Color.BLACK);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            Card.getBoard().getActivePlayer().endTurn();
            GUI.getBoardFrame().getFieldPanel().getActivePlayerPanel().getPlayerNamePanel().updateAll();
            GUI.getBoardFrame().getFieldPanel().getOpponentPlayerPanel().getPlayerNamePanel().updateAll();
            GUI.getBoardFrame().getWestImagesPanel().swap();
            GUI.getBoardFrame().updateBoardFrame();
            GUI.getBoardFrame().resetHandlers(); // added
            GUI.getBoardFrame().getActiveHandPanel().repaint();
        }

    }

    public static class DeckButton extends JButton {
        private static ImageIcon deck = new ImageIcon("images/AttackMode.png");
        private boolean active;

        public DeckButton(boolean active) {
            super(deck);
            this.active = active;
            setHorizontalTextPosition(SwingConstants.CENTER);
            setFont(new Font("", Font.ITALIC, 22));
            //setForeground(Color.ORANGE);
            setForeground(java.awt.Color.WHITE);
            setPreferredSize(new Dimension(CardButton.getDimension('W'),150));
            updateDeck();
            validate();
        }

        public void updateDeck(){
            if(active)
                setText(""+Card.getBoard().getActivePlayer().getField().getDeck().getDeck().size());
            else
                setText(""+Card.getBoard().getOpponentPlayer().getField().getDeck().getDeck().size());
        }
    }

    public static class EastButtonsPanel extends JPanel {
        public src.yugioh.gui.boardframe.SizeLabelLayout.NextPhaseButton nextPhaseButton = new src.yugioh.gui.boardframe.SizeLabelLayout.NextPhaseButton();

        private src.yugioh.gui.boardframe.SizeLabelLayout.EndTurnButton endTurnButton = new src.yugioh.gui.boardframe.SizeLabelLayout.EndTurnButton();
        private src.yugioh.gui.boardframe.SizeLabelLayout.SwitchMonsterModeButton switchMonsterModeButton= new src.yugioh.gui.boardframe.SizeLabelLayout.SwitchMonsterModeButton();
        public EastButtonsPanel(){
            setLayout(new GridBagLayout());
            setOpaque(false);
            GridBagConstraints c = new GridBagConstraints();
            c.insets.bottom = 20;
            c.insets.left = 25;
            add(switchMonsterModeButton, c);
            c.gridy = 1;
            add(nextPhaseButton, c);
            c.gridy =2;
            add(endTurnButton, c);
            validate();
        }

        public NextPhaseButton getNextPhaseButton() {
            return nextPhaseButton;
        }
        public void setNextPhaseButton(NextPhaseButton nextPhaseButton) {
            this.nextPhaseButton = nextPhaseButton;
        }
        public EndTurnButton getEndTurnButton() {
            return endTurnButton;
        }
        public void setEndTurnButton(EndTurnButton endTurnButton) {
            this.endTurnButton = endTurnButton;
        }
        public SwitchMonsterModeButton getSwitchMonsterModeButton() {
            return switchMonsterModeButton;
        }
        public void setSwitchMonsterModeButton(
                SwitchMonsterModeButton switchMonsterModeButton) {
            this.switchMonsterModeButton = switchMonsterModeButton;
        }
    }

    public static class NextPhaseButton extends JButton implements ActionListener{

        public NextPhaseButton(){
            super("Proxima Fase", new ImageIcon("images/NextPhase.jpg"));
            setPreferredSize(new Dimension(300,190));
            setHorizontalTextPosition(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.BOTTOM);
            //setFont(new Font("", Font.ITALIC, 18));
            //setForeground(java.awt.Color.WHITE);
            addActionListener(this);
            setFont(new Font("", Font.ITALIC, 20));
            setForeground(Color.ORANGE);
            setBackground(Color.BLACK);
        }
        public void actionPerformed(ActionEvent e) {
            if(Card.getBoard().getActivePlayer().getField().getPhase().equals(Phase.MAIN2))
                GUI.getBoardFrame().getEastButtonsPanel().getEndTurnButton().doClick();
            else{
                Card.getBoard().getActivePlayer().endPhase();
                GUI.getBoardFrame().getFieldPanel().getActivePlayerPanel().getPlayerNamePanel().updatePhase();
                GUI.getBoardFrame().getFieldPanel().getOpponentPlayerPanel().getPlayerNamePanel().updatePhase();
            }
        }
    }

    public static class CardButton extends JButton {
        protected static ImageIcon Attack = new ImageIcon("images/AttackMode.png");
        protected static ImageIcon Defence = new ImageIcon("images/DefenceMode.png");
        protected static ImageIcon spellIcon = new ImageIcon("images/SpellTrapCardZone.png");
        private static int height=150;
        private static int width = 95;

        public CardButton() {
            super();
            construct();
        }

        public CardButton(Icon arg0) {
            super(arg0);
            construct();
        }

        public CardButton(String arg0) {
            super(arg0);
            construct();
        }

        public CardButton(Action arg0) {
            super(arg0);
            construct();
        }

        public CardButton(String arg0, Icon arg1) {
            super(arg0, arg1);
            construct();
        }

        private void construct(){
            setPreferredSize(new Dimension(width,height));
            setBackground(Color.GRAY);
            validate();
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public static int getDimension(char WorH) {
            if(WorH=='w'||WorH=='W')
                return width;
            if(WorH=='h'||WorH=='H')
                return height;
            return 0;
        }
    }

    public static class MonsterButton extends CardButton implements ActionListener {

        private MonsterCard monster;

        public MonsterButton() {
            super(Attack);
            addActionListener(null);
        }

        public MonsterButton(MonsterCard monster) {
            super(Attack);
            ImageIcon icon = new ImageIcon("images/" + monster.getName() + ".jpg");
            if (icon != null) {
                setIcon(icon);
            }
            this.monster = monster;
            setToolTipText(monster.getName() + "\n ATK: " + monster.getAttackPoints() + "\n DEF: " + monster.getDefensePoints() + "\n Level: " + monster.getLevel());
            addActionListener(this);
            validate();
        }

        public void toDefence() {
            setIcon(Defence);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (monster.getLocation() == Location.FIELD) {
                if (Card.getBoard().getActivePlayer().getField().getPhase() == Phase.BATTLE) {
                    battlePhaseActions();
                    return;
                } else {
                    if (GUI.getBoardFrame().isToSwitch()) {
                        switchMonsterModeAction();
                    }
                }
                if (GUI.getBoardFrame().getMonsterToSummon() != null) {
                    sacrificesAction();
                }
                if (GUI.getBoardFrame().getSpellToActivate() != null) {
                    spellActivationWithTargetAction();
                }
                GUI.getBoardFrame().updateBoardFrame();
            }
            if (monster.getLocation() == Location.HAND && Card.getBoard().getActivePlayer().getField().getHand().contains(monster)) { // condition added
                new HandOptionsFrame(true, monster);
            }
        }

        private void switchMonsterModeAction() {
            Card.getBoard().getActivePlayer().switchMonsterMode(monster);
            GUI.getBoardFrame().setToSwitch(false);
        }

        private void spellActivationWithTargetAction() {
            try {
                Card.getBoard().getActivePlayer().activateSpell(GUI.getBoardFrame().getSpellToActivate(), monster);
                GUI.getBoardFrame().setSpellToActivate(null);
            } catch (IllegalSpellTargetException e) {
                GUI.errorFrame(e);
            }
        }

        private void sacrificesAction() {
            if (GUI.getBoardFrame().getSacrificesCount() > 0) {
                if (!GUI.getBoardFrame().getSacrificedMonsters().contains(monster)) {
                    GUI.getBoardFrame().getSacrificedMonsters().add(monster);
                    GUI.getBoardFrame().decrementSacrificesCount();
                } // bug needed brackets
            }

            if (GUI.getBoardFrame().getSacrificesCount() == 0) {
                try {
                    if (GUI.getBoardFrame().isSacrificeAttack())
                        Card.getBoard().getActivePlayer().summonMonster(GUI.getBoardFrame().getMonsterToSummon(), GUI.getBoardFrame().getSacrificedMonsters());
                    else
                        Card.getBoard().getActivePlayer().setMonster(GUI.getBoardFrame().getMonsterToSummon(), GUI.getBoardFrame().getSacrificedMonsters());
                    GUI.getBoardFrame().setMonsterToSummon(null);
                } catch (Exception e) {
                    GUI.errorFrame(e);
                } finally { // added the finally
                    GUI.getBoardFrame().setSacrificedMonsters(new ArrayList<MonsterCard>());
                }
            }
        }

        private void battlePhaseActions() {
            //bug start fix
            if (GUI.getBoardFrame().getSpellToActivate() != null) {
                GUI.getBoardFrame().setSpellToActivate(null);
                GUI.errorFrame(new WrongPhaseException());
            }
            //bug end fix
            if (GUI.getBoardFrame().getAttackingMonster() != null) {
                Card.getBoard().getActivePlayer().declareAttack(GUI.getBoardFrame().getAttackingMonster(), monster);
                GUI.getBoardFrame().setAttackingMonster(null);
            } else {
                try {
                    if (!Card.getBoard().getActivePlayer().declareAttack(monster)) { // attacks life if possible
                        if (Card.getBoard().getActivePlayer().getField().getMonstersArea().contains(monster)) {
                            new ConfirmFrame("Por favor, escolha um monstro para atacar");
                            GUI.getBoardFrame().setAttackingMonster(monster);
                        }
                    }
                } catch (Exception e) {
                    GUI.getBoardFrame().resetHandlers();
                    GUI.errorFrame(e);
                }
            }
            GUI.getBoardFrame().updateBoardFrame();
        }
    }

    public static class GraveyardButton extends JButton {
        private static ImageIcon graveyard = new ImageIcon("images/Graveyard.png");
        private boolean active ;

        public GraveyardButton(boolean active) {
            super(graveyard);
            this.active=active;
            setPreferredSize(new Dimension(CardButton.getDimension('W'),150));
        }

        public void updateGraveyard(){
            ArrayList<Card> graveyardList ;
            if(active)
                graveyardList = Card.getBoard().getActivePlayer().getField().getGraveyard();
            else graveyardList = Card.getBoard().getOpponentPlayer().getField().getGraveyard();
            if(graveyardList.size()>0){
                Card current = graveyardList.get(graveyardList.size()-1);
                if(current instanceof MonsterCard){
                    setIcon(new ImageIcon("images/"+current.getName()+".jpg"));
                    setToolTipText(current.getName()+"\n ATK: "+((MonsterCard)current).getAttackPoints()+"\n DEF: "+((MonsterCard)current).getDefensePoints()+"\n Level: "+((MonsterCard)current).getLevel());
                    //add rodrigo
                    setFont(new Font("", Font.ITALIC, 20));
                    setForeground(Color.ORANGE);
                    setBackground(Color.BLACK);
                }
                else{ setIcon(new ImageIcon("images/"+current.getName()+".png"));
                    setToolTipText(current.getName());
                }
            }
            else setIcon(graveyard);
        }
    }

    public static class SwitchMonsterModeButton extends JButton implements ActionListener{


        public SwitchMonsterModeButton() {
            super("MUDAR MODO CARTA",new ImageIcon("images/SwitchMonsterMode.jpg"));

            setPreferredSize(new Dimension(300,190));
            setHorizontalTextPosition(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.BOTTOM);
            //setFont(new Font("", Font.ITALIC, 18));
            setFont(new Font("", Font.ITALIC, 20));
            //setForeground(java.awt.Color.WHITE);
            setForeground(Color.ORANGE);
            setBackground(Color.BLACK);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            GUI.getBoardFrame().setToSwitch(true);
            new ConfirmFrame("\n" +
                    "Por favor, selecione um monstro para mudar seu modo");
        }
    }
}

