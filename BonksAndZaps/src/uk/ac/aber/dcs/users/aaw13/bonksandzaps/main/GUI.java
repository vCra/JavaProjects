                  package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JSlider; //Currently not used
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Gender;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.State;

public class GUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5960161705333344687L;
	JPanel grid = new JPanel();
	JPanel info = new JPanel();
	JPanel statusPanel = new JPanel();
	Action onButtonClick = new OnButtonClickAction();
	Board board;
	public GUI(){
		super("Bonks and Zaps");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		grid.setLayout(new GridLayout(20,20));
		for (int a = 0; a <20; a++){
			for(int b = 0; b <20 ; b++ ){
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(75, 30));
				button.addActionListener(onButtonClick);
				button.setActionCommand(a +"-" + b);
				button.setToolTipText("Click to view infomation about the beings in this box");
                grid.add(button);
			}
		}
		
		//Time Slider
//		JSlider time = new JSlider();
//		time.setMinimum(10);
//		time.setMaximum(5000);
//		config.add(time);
		
		//Info about the selected square
		
		JTextArea roomInfoBox = new JTextArea();
		roomInfoBox.setLineWrap(true);
		roomInfoBox.setAutoscrolls(isEnabled());
		roomInfoBox.setEditable(false);
		roomInfoBox.setWrapStyleWord(true);
		roomInfoBox.setAlignmentX(1);
		

		info.add(roomInfoBox);
		
		//Status Bar
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		// Add Content to the statusBar
		
		//Round Indicator
		JLabel roundLabel = new JLabel();
		roundLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(roundLabel);
		
		//Death Indicator
		JLabel deathLabel = new JLabel();
		deathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(deathLabel);
		
		JLabel aliveLabel = new JLabel();
		aliveLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(aliveLabel);
		
		JLabel totalLabel = new JLabel();
		totalLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(totalLabel);
		
		JLabel childLabel = new JLabel();
		childLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusPanel.add(childLabel);
		
		JLabel zapsTimLabel = new JLabel();
		zapsTimLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusPanel.add(zapsTimLabel);
		
		JLabel executionTimeLabel = new JLabel();
		executionTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusPanel.add(executionTimeLabel);
		

		
		
		
        this.add(grid, BorderLayout.NORTH);
        this.add(info, BorderLayout.WEST);
		this.add(statusPanel, BorderLayout.SOUTH);

        this.pack();
		this.setVisible(true);
	}
	public void addBoard(Board board){
		this.board = board;
	}
	public void onUpdate(){
		for (int a = 0; a < 20; a++){
			for (int b = 0; b <20; b++){
				
				((JButton) this.grid.getComponent(a+b*20)).setText(genHTML(board.getRoom(a+1, b+1)));
				((JButton) this.grid.getComponent(a+b*20)).setBackground(this.genColor(board.getRoom(a+1, b+1)));
			}
		}
		//Status Bar Lables
		((JLabel) this.statusPanel.getComponent(0)).setText("Round: "+ Application.currentRound);
		((JLabel) this.statusPanel.getComponent(1)).setText("    Deaths: "+ board.getCountDeadBonks());
		((JLabel) this.statusPanel.getComponent(2)).setText("    Bonks Alive: "+ board.getCountAliveBonks());
		((JLabel) this.statusPanel.getComponent(3)).setText("    Total Bonks: "+ board.getCountBonks());
		((JLabel) this.statusPanel.getComponent(4)).setText("    Child Bonka: "+ board.getCountChildBonks());
		((JLabel) this.statusPanel.getComponent(5)).setText("    Total Zaps: "+ board.getCountZaps());
		((JLabel) this.statusPanel.getComponent(6)).setText("    Current Round Timer: "+ Application.getExecutionTime() + " ms");
		
		
		this.repaint();
	}
	private int[] countBeings(Room room){
		int bonksM = 0;
		int bonksF = 0;
		int bonksD = 0;
		int bonksC = 0;
		int zaps = 0;
		for (Creature thing: room){
			if (thing instanceof Bonk){
				if (thing.getCreated() == Application.currentRound){
					// Then the bonk is a child
					//Note that if it is a child it is not counted as a male or a female until the next round
					bonksC++;
				}
				else if (((Bonk) thing).getState() == State.DEAD){
					// Then its dead jim
					bonksD++;
				}
				else if (((Bonk) thing).getGender() == Gender.MALE){
					//Then its a dude
					bonksM++;
				}
				else if(((Bonk) thing).getGender() == Gender.FEMALE){
					// Then its a lass
					bonksF++;
				}
				else{
					//This shouldn't happen
				}
			}
			else if (thing instanceof Zaps){
				//Then its a zap
				zaps++;
			}
		}
		int[] count;
		count = new int[5];
		count[0] = bonksM;
		count[1] = bonksF;
		count[2] = bonksD;
		count[3] = bonksC;
		count[4] = zaps;
		return count;
	}
	private Color genColor(Room room){
		int[] beings = this.countBeings(room);
		if (beings[4] > 0){
			return Color.RED;
		}

		else if (beings[3] >0 ){
			return Color.GREEN;
		}
		else if (beings[0] > 0 || beings[1] >0){
			return Color.LIGHT_GRAY;
		}
		else if (beings[2] >0){
			return Color.BLACK;
		}
		return Color.WHITE;
	}
	private void genRoomInfo(int x,int y){
		String text = board.roomToString(x+1, y+1);
		System.out.println(text);
		((JTextArea) this.info.getComponent(0)).setText(text);
		
	}

	private String genHTML(Room room){
		String HTML = null;
		int[] count = countBeings(room);
		int bonksM = count[0];
		int bonksF = count[1];
		int bonksD = count[2];
		int bonksC = count[3];
		int zaps = count[4];
		//Now generate some HTML Code
		
		HTML = ("<html><span style=\"background-color: #FFFFFF\"><font color = \"blue\">Something</font></span></html>");
		HTML = ("<html><span style=\"background-color: #FFFFFF\"><font color = \"blue\">" + bonksM + "</font><font color = \"red\">" + bonksF + "</font><font color = \"lime\">" + bonksC + "</font><font color = \"black\">" + bonksD + "</font><font color = \"orange\">" + zaps + "</font></span></html>");
		return HTML;
	}	
	
	/**
	 * Contains code that handles what happens
	 * @author awalker
	 *
	 */
	class OnButtonClickAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 108280140085422391L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] xy = this.splitXY(e.getActionCommand());
			genRoomInfo(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
	
		}
		
		private String[] splitXY(String xyString){
			return xyString.split("-");
		}
	}
}
