package src.yugioh.gui.boardframe;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

//import javafx.scene.control.Label;
//
// import javafx.scene.image.Image;
import src.yugioh.cards.MonsterCard;
import src.yugioh.cards.spells.SpellCard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BoardFrame extends JFrame implements ActionListener{
	private FieldPanel fieldPanel;
	private HandPanel opponentHandPanel;
	private HandPanel activeHandPanel;
	private WestImagesPanel westImagesPanel;
	private SizeLabelLayout.EastButtonsPanel eastButtonsPanel;
	
	private MonsterCard attackingMonster;
	private boolean toSwitch = false;
	private SpellCard spellToActivate;
	private boolean sacrificeAttack ;
	private ArrayList<MonsterCard> sacrificedMonsters = new ArrayList<MonsterCard>();
	private int sacrificesCount;
	private MonsterCard monsterToSummon;
	
	public BoardFrame(){
		super("Yu-Gi-Oh!");
		setFramePrefrences();
		initializeAttributes();
		addPanels();
		validate();
	}

	private void setFramePrefrences(){
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 850);//1367, 792 //1500, 950
//		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
//		setUndecorated(true);
		setVisible(true);	
		setResizable(false);
	}

	private void initializeAttributes(){
		fieldPanel = new FieldPanel();
		activeHandPanel = new HandPanel(true);
		opponentHandPanel = new HandPanel(false);
		opponentHandPanel.setPreferredSize(new Dimension(activeHandPanel.getPreferredSize().width,15));
		westImagesPanel = new WestImagesPanel();
		eastButtonsPanel =new SizeLabelLayout.EastButtonsPanel();
	}

	private void addPanels(){//java.awt.Image.SCALE_SMOOTH
		setContentPane(new JLabel(new ImageIcon("images/background.png"))); //.setSize(1450, 800)
		
		//ImageIcon.setSize(1450, 900)	;
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		dataPanel.setOpaque(false);
		dataPanel.setSize(1450, 800);//1366,820
		dataPanel.add(opponentHandPanel,BorderLayout.NORTH);
		dataPanel.add(fieldPanel, BorderLayout.CENTER);
		dataPanel.add(activeHandPanel,BorderLayout.SOUTH);
		dataPanel.add(eastButtonsPanel,BorderLayout.EAST);
		dataPanel.add(westImagesPanel,BorderLayout.WEST);//image Players
		add(dataPanel);
	}
	
	public FieldPanel getFieldPanel() {
		return fieldPanel;
	}

	public void setFieldPanel(FieldPanel fieldPanel) {
		this.fieldPanel = fieldPanel;
	}

	public HandPanel getOpponentHandPanel() {
		return opponentHandPanel;
	}

	public void setOpponentHandPanel(HandPanel opponentHandPanel) {
		this.opponentHandPanel = opponentHandPanel;
	}

	public HandPanel getActiveHandPanel() {
		return activeHandPanel;
	}

	public void setActiveHandPanel(HandPanel activeHandPanel) {
		this.activeHandPanel = activeHandPanel;
	}

	public WestImagesPanel getWestImagesPanel() {
		return westImagesPanel;
	}

	public void setWestImagesPanel(WestImagesPanel westImagesPanel) {
		this.westImagesPanel = westImagesPanel;
	}

	public SizeLabelLayout.EastButtonsPanel getEastButtonsPanel() {
		return eastButtonsPanel;
	}

	public void setEastButtonsPanel(SizeLabelLayout.EastButtonsPanel eastButtonsPanel) {
		this.eastButtonsPanel = eastButtonsPanel;
	}

	public void updateBoardFrame() {
			activeHandPanel.updateHand();
			opponentHandPanel.updateHand();
			fieldPanel.getActivePlayerPanel().getDeckGraveyardPanel().getDeck().updateDeck();
			fieldPanel.getOpponentPlayerPanel().getDeckGraveyardPanel().getDeck().updateDeck();
			fieldPanel.getActivePlayerPanel().getDeckGraveyardPanel().getGraveyard().updateGraveyard();
			fieldPanel.getOpponentPlayerPanel().getDeckGraveyardPanel().getGraveyard().updateGraveyard();
			fieldPanel.getActivePlayerPanel().getMonsterSpellPanel().getMonstersGrid().updateMonstersArea();
			fieldPanel.getOpponentPlayerPanel().getMonsterSpellPanel().getMonstersGrid().updateMonstersArea();
			fieldPanel.getActivePlayerPanel().getMonsterSpellPanel().getSpellsGrid().updateSpellsArea();
			fieldPanel.getOpponentPlayerPanel().getMonsterSpellPanel().getSpellsGrid().updateSpellsArea();
			fieldPanel.getActivePlayerPanel().getPlayerNamePanel().updateLifePoints();
			fieldPanel.getOpponentPlayerPanel().getPlayerNamePanel().updateLifePoints();
			repaint();
			validate();
	}

	public boolean isToSwitch() {
		return toSwitch;
	}

	public void setToSwitch(boolean toSwitch) {
		this.toSwitch = toSwitch;
	}

	public SpellCard getSpellToActivate() {
		return spellToActivate;
	}

	public void setSpellToActivate(SpellCard spellToActivate) {
		this.spellToActivate = spellToActivate;
	}

	
	public int getSacrificesCount() {
		return sacrificesCount;
	}

	public void setSacrificesCount(int sacrificesCount) {
		this.sacrificesCount = sacrificesCount;
	}
	
	public void decrementSacrificesCount() {
		this.sacrificesCount--;
	}

	public boolean isSacrificeAttack() {
		return sacrificeAttack;
	}

	public void setSacrificeAttack(boolean sacrificeAttack) {
		this.sacrificeAttack = sacrificeAttack;
	}

	public ArrayList<MonsterCard> getSacrificedMonsters() {
		return sacrificedMonsters;
	}

	public void setSacrificedMonsters(ArrayList<MonsterCard> sacrificedMonsters) {
		this.sacrificedMonsters = sacrificedMonsters;
	}

	public MonsterCard getMonsterToSummon() {
		return monsterToSummon;
	}

	public void setMonsterToSummon(MonsterCard monsterToSummon) {
		this.monsterToSummon = monsterToSummon;
	}

	public MonsterCard getAttackingMonster() {
		return attackingMonster;
	}

	public void setAttackingMonster(MonsterCard attackingMonster) {
		this.attackingMonster = attackingMonster;
	}

	public void resetHandlers() {
		attackingMonster = null;
		toSwitch = false;
		spellToActivate = null;
		sacrificeAttack = false;
		sacrificedMonsters = new ArrayList<MonsterCard>();
		sacrificesCount=0;
		monsterToSummon=null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.toBack();
	}
}