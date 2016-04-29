

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.InsufficientResourcesException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AVLTree<T extends Comparable<T>> extends Application {
	private AVLNode<T> root;
	private int size;
	private ArrayList<T> mylist;
	private AVLNode<T> succ;
	private AVLNode<T> pred;
	private double n=1f;
	private String space = "";
	private Circle ball;
	
	static double startX = 0f;
	static double startY = 0f;
	
	public static double RightX = 30f;
	public static double RightY = 30f;
	public static double LeftY = 30f;
	
	private double previousLineStartPointX = 0f;
	private double previousLineStartPointY = 0f;
	
	public static double startMillisec = 100.0f;
	public static double incrementDuration = 100.0f;
	public static int cuepoint = 0;
	
	public static Timeline MainTimeline;
	
	AVLNode<T> tarNode = null;
	
	double tarXpos = 0f;
	double tarYpos = 0f;
	
	public AVLTree() {
		this.root = null;
		this.size=0;
		this.mylist = new ArrayList<T>();
		this.succ = null;
		this.pred = null;
	}
	
	public AVLTree(T e) {
		this.root = new AVLNode<T>(e);
		this.size=1;
		this.mylist = new ArrayList<T>();
		this.succ = null;
		this.pred = null;
		
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	public boolean add(T e){ 
		
		if (!search(e)){
			
			// gui===new time line version========================
			if (AVLMain.RandomCheck==0) {
			
				AVLMain.SliderValue();
				
				cuepoint = 0;
				
				MainTimeline = new Timeline();
					
				if (AVLMain.p.lookup("#ball")==null) {
					ball = Animation1.genBall();
					AVLMain.p.getChildren().addAll(ball);
				}
			}
			// gui===============================================
			
			this.n =1.0f;
			previousLineStartPointX = 0f;
			previousLineStartPointY = 0f;
			
			space = "";
			System.out.println(space+"===>public boolean add(int e="+e+")");
			
			space += "          ";
			root = add(this.root, e, startX, startY);
			this.size++;
			
			// gui===============================================
			if (AVLMain.RandomCheck==0) {
				MainTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(startMillisec), "last",
						new EventHandler<ActionEvent>() {
							public void handle(ActionEvent arg0) {
								AVLMain.pb.setProgress(MainTimeline.getCurrentTime().toMillis()/MainTimeline.getCycleDuration().toMillis());
								System.out.println(MainTimeline.getCurrentTime().toMillis());
								
								MainTimeline.pause();
								AVLMain.instxt.setDisable(false);
								AVLMain.instxt.requestFocus();
							}
						},
						(KeyValue) null
						));
				
				MainTimeline.setCycleCount(MainTimeline.INDEFINITE);
				MainTimeline.setAutoReverse(false);
				MainTimeline.play();
				AVLMain.instxt.setDisable(true);
				
			}
			cuepoint = 0;
			// gui===============================================

		
			this.displayNodePosition();
			
			
			return true;
		}
		return false;
	}
	
	private AVLNode<T> add(AVLNode<T> node, T e, double xpos, double ypos){
		
		if (node != null) {
			System.out.println(space+"===>AVLNode add(AVLNode node="+node.element+", int e="+e+", double xpos="+xpos+", double ypos="+ypos+")");
		}else {
			System.out.println(space+"===>AVLNode add(AVLNode node=null, int e="+e+", double xpos=null, double ypos=null)");
		}
		
		if (node == null) {
			System.out.println(space+"===>if (node == null)");
			
			System.out.println(space+"==>if (node == this.root)");
			
			AVLNode<T> newNode = new AVLNode<T>(e, xpos, ypos); 
			System.out.println(space+"==>AVLNode<T> newNode = new AVLNode<T>(e="+e+", xpos="+xpos+", ypos="+ypos+");");
			
			String value = ""+e;
			
			if (node != this.root) {	
				// insert new line and ball ===new version=====GUI======================================================
				if (AVLMain.RandomCheck==0) {
				
					Line line = null;
					
					if (previousLineStartPointX<xpos) {
						System.out.println(space+"==>if (previousLineStartPointX<xpos)");
						
						line = Animation1.genRightLine(previousLineStartPointX, previousLineStartPointY, value);
						
						System.out.println(space+"==>MainTimeline.getKeyFrames().addAll(Animation1.NewLineTraverseFrame(line, ball, xpos, ypos, (startMillisec+=incrementDuration), (++cuepoint)));");
						MainTimeline.getKeyFrames().addAll(Animation1.NewLineTraverseFrame(line, ball, xpos, ypos, (++cuepoint)));
						
						AVLMain.p.getChildren().addAll(line);
					}else if ((previousLineStartPointX>xpos)){
						System.out.println("else if ((previousLineStartPointX="+previousLineStartPointX+">xpos="+xpos+"))");
						line =  Animation1.genLeftLine(previousLineStartPointX, previousLineStartPointY, value);
						
						System.out.println(space+"==>MainTimeline.getKeyFrames().addAll(Animation1.NewLineTraverseFrame(line, ball, xpos+RightX, ypos, (startMillisec+=incrementDuration), (++cuepoint)))");
						MainTimeline.getKeyFrames().addAll(Animation1.NewLineTraverseFrame(line, ball, xpos+RightX, ypos, (++cuepoint))); 
						
						AVLMain.p.getChildren().addAll(line);
					}
				}
				// insert new line and ball =================GUI======================================================	

			}
			
			
			System.out.println(space+"==>myanimation.getChildren().addAll(Animation1.InsertNewNode(RootNode));");
			
			// insert new node ==new version=======GUI================================================================
			if (AVLMain.RandomCheck==0) {
				
				StackPane RootNode = Animation1.genNode(value, xpos, ypos);
				
				System.out.println(space+"==>MainTimeline.getKeyFrames().addAll(Animation1.InsertNewNodeFrame(RootNode, (startMillisec+=incrementDuration), (++cuepoint)))");			
				MainTimeline.getKeyFrames().addAll(Animation1.InsertNewNodeFrame(RootNode, (++cuepoint)));
				
				AVLMain.p.getChildren().add(RootNode);
			}
			// insert root node =========================================================================
			
			
			System.out.println(space+"==>return newNode");
			space = space.substring(0,space.length()-10);
			return newNode;
		
		}
		
		// draw left line ================GUI==============================================
		if (AVLMain.RandomCheck==0) {
			String lineId = "line"+node.element;
			
			if (lineId.contains(".")) {
				 lineId = lineId.replace(".", "dot");
			 }
			
			if (AVLMain.p.lookup("#"+lineId)==null) {
				
			}
			else {
				Line line = (Line) AVLMain.p.lookup("#"+lineId);
				System.out.println(space+"===>if (AVLMain.p.lookup(#lineId)="+line.getId()+"!=null)");
				
				//myanimation.getChildren().add(Animation1.ExistingLineTraverse(line, ball));
				System.out.println(space+"===>MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (startMillisec+=incrementDuration), (++cuepoint)));");
				MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
			}
			
			previousLineStartPointX = xpos;
			previousLineStartPointY = ypos;
		}
		// draw left line ================GUI==============================================
		
		
		// add current node scale to animation ======================GUI=================================
		if (AVLMain.RandomCheck==0) {
			String nodeEle = ""+node.element;
			StackPane Node = getStackPaneFromNode(node);
			
			System.out.println(space+"===>MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, (startMillisec+=incrementDuration), (++cuepoint)))");
			MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, (++cuepoint)));
		}
		// add current node scale to animation ======================GUI=================================
			
		if (e.compareTo(node.element)<0) { // e<node
			System.out.println(space+"===>if (e="+e+"<node.element="+node.element+")");
			
			if (node.left != null) {
				System.out.println(space+"===>node.left="+node.left.element+" = add(node.left="+node.left.element+", e="+e+");");
			}else {
				System.out.println(space+"===>node.left=null = add(node.left=null, e="+e+");");
			}
			
			this.n+=1.5;
			space += "          ";
			node.left = add(node.left, e, (xpos-(2000*(1/(Math.pow(n, 2))))), (ypos+100.00));
			
			this.n-=1.5;
		}
		else if (e.compareTo(node.element)>0) { // e > node
			System.out.println(space+"===>else if (e="+e+">node.element="+node.element+")");
			
			this.n+=1.5;
			
			if (node.right != null) {
				System.out.println(space+"===>node.right="+node.right.element+" = add(node.right="+node.right.element+", e="+e+")");
			}else {
				System.out.println(space+"===>node.right=null = add(node.right=null, e="+e+")");
			}
		
			space += "          ";
			node.right = add(node.right, e,(xpos+(2000*(1/(Math.pow(n, 2))))), (ypos+100.00));
			
			this.n-=1.5;
		}
		
		// else if (e.compareTo(node.element)==0)
		System.out.println("n : "+n);
		
		
		System.out.println(space+"===>node = AVLchange(node)");
		space += "          ";
		node = AVLchange(node, n);
		
		System.out.println(space+"===>return node="+node.element+"");
		space = space.substring(0, space.length()-10);
		return node;
	}
	
	private AVLNode<T> AVLchange(AVLNode<T> node, double n) {	
		if (node != null) {
			System.out.println(space+"===>node="+node.element+" = AVLchange(node)");
			int leftHeight = getHeight(node.left);
			int rightHeight = getHeight(node.right);
			
			System.out.println(space+"===>int leftHeight = getHeight(node.left)="+leftHeight+"");
			System.out.println(space+"===>int rightHeight = getHeight(node.right)="+rightHeight+"");
			
			if (Math.abs(leftHeight-rightHeight)>1) {
				System.out.println(space+"===>if (Math.abs(leftHeight-rightHeight)="+Math.abs(leftHeight-rightHeight)+">1)");
				
				
				System.out.println(space+"===>AVLNode temp = FixUnbalance(node="+node.element+")");
				space += "          ";
				node = FixUnbalance(node, n);
			}
				System.out.println(space+"===>node.height="+node.height+" = 1+Math.max(leftHeight, rightHeight)="+(1+Math.max(leftHeight, rightHeight))+"");
				node.height = ReAssignHeight(node);	
				
				System.out.println(space+"===>return node="+node.element+"");
				space = space.substring(0, space.length()-10);
				return node;
		}
		return null;
	}
	
	private int getHeight(AVLNode<T> node) {
		
		if (node!=null) {
			return node.height;
		}
		else {
			return -1;
		}
			
	}
	
	private int ReAssignHeight(AVLNode<T> node) {
		
		if (node != null) {
			
			int leftHeight = getHeight(node.left);
			int rightHeight = getHeight(node.right);
			
			return 1+Math.max(leftHeight, rightHeight);
		}
		return -1;
	}
	
	private AVLNode<T> FixUnbalance(AVLNode<T> node, double n) {
		System.out.println(space+"===>private AVLNode FixUnbalance(AVLNode node="+node.element+")");
		
		if (getHeight(node.left)>getHeight(node.right)) {
			System.out.println(space+"===>if (getHeight(node.left)="+getHeight(node.left)+">getHeight(node.right)="+getHeight(node.right)+")");
			
			if (getHeight(node.left.left)>=getHeight(node.left.right)) {
				System.out.println(space+"===>if (getHeight(node.left.left)="+getHeight(node.left.left)+">getHeight(node.left.right)="+getHeight(node.left.right)+")");

				
				System.out.println(space+"===>return RightRotate(node)");
				space = space.substring(0, space.length()-10);
				return RightRotate(node, n);
			}
			else if (getHeight(node.left.left)<getHeight(node.left.right)) {
				System.out.println(space+"===>else if (getHeight(node.left.left)="+getHeight(node.left.left)+"<getHeight(node.left.right)="+getHeight(node.left.right)+")");
				
				System.out.println(space+"===>return MiddleLeftRotate(node)");
				space = space.substring(0, space.length()-10);
				return MiddleLeftRotate(node, n);
			}
		}
		else if (getHeight(node.left)<getHeight(node.right)) {
			System.out.println(space+"===>else if (getHeight(node.left)="+getHeight(node.left)+"<getHeight(node.right)="+getHeight(node.right)+")");

			if (getHeight(node.right.left)<=getHeight(node.right.right)) {
				System.out.println(space+"===>if (getHeight(node.right.left)="+getHeight(node.right.left)+"<getHeight(node.right.right)="+getHeight(node.right.right)+")");
				
				System.out.println(space+"===>return LeftRotate(node)");
				space = space.substring(0, space.length()-10);
				return LeftRotate(node, n);
			}
			else if (getHeight(node.right.left)>getHeight(node.right.right)) {
				System.out.println(space+"===>else if (getHeight(node.right.left)="+getHeight(node.right.left)+">getHeight(node.right.right)="+getHeight(node.right.right)+")");
				
				System.out.println(space+"===>return middlerightRotate(node)");
				space = space.substring(0, space.length()-10);
				return MiddleRightRotate(node, n);
			}
		}
		return node;
	}
	
	public StackPane getStackPaneFromNode(AVLNode<T> node) {
		
		if (node != null) {
			
			String e = ""+node.element;
			
			if (e.contains(".")) {
				 e = e.replace(".", "dot");
			 }
			
			StackPane sp = (StackPane) AVLMain.p.lookup("#node"+e);
			return sp;
		}
		return null;
	}
	
	public Line getLineFromNode(AVLNode<T> node) {
		
		if (node != null) {
			
			String e = ""+node.element;
			
			if (e.contains(".")) {
				 e = e.replace(".", "dot");
			}
			
			
			Line line = (Line) AVLMain.p.lookup("#line"+e);
			return line;
		}
		return null;
	}
	
	public void changeLineID(AVLNode<T> oldnode, AVLNode<T> newnode) {
		
		if (oldnode != null) {
			if (newnode != null) {
				
				String e = ""+oldnode.element;
				
				if (e.contains(".")) {
					 e = e.replace(".", "dot");
				 }
				
				
				Line line = (Line) AVLMain.p.lookup("#line"+e);
				
				if (line!=null)  {
					
					String e1 = ""+newnode.element;
					
					if (e1.contains(".")) {
						 e1 = e1.replace(".", "dot");
					 }
					
					line.setId("line"+e1);					
				}
				
			}
		}
	}

	public ArrayList<KeyFrame> PickUpStackPaneLine(StackPane nodepane, Line line, AVLNode<T> node, double startx, double starty, double endx, double endy, int cue) {
		
		//KeyFrame[] portionOfFrame = new KeyFrame[2];
		
		ArrayList<KeyFrame> portionOfFrame = new ArrayList<KeyFrame>();
		
		// get original position 
		portionOfFrame.add(new KeyFrame(Duration.millis(startMillisec), ""+cue, 
				new EventHandler<ActionEvent>(){@Override
					public void handle(ActionEvent event) {
						AVLMain.pb.setProgress(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
				}},
				new KeyValue(nodepane.layoutXProperty(), nodepane.getLayoutX()), 
				new KeyValue(nodepane.layoutYProperty(), nodepane.getLayoutY()), 
				new KeyValue(line.startXProperty(), line.getStartX()), 
				new KeyValue(line.startYProperty(), line.getStartY()),
				new KeyValue(line.endXProperty(), nodepane.getLayoutX()), 
				new KeyValue(line.endYProperty(), nodepane.getLayoutY())));
		
		// get original position 
		portionOfFrame.add(new KeyFrame(Duration.millis(AVLTree.startMillisec+=(1*AVLTree.incrementDuration)), 
				new EventHandler<ActionEvent>(){@Override
					public void handle(ActionEvent event) {
						AVLMain.pb.setProgress(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
				}},
				new KeyValue(nodepane.layoutXProperty(), node.xpos), 
				new KeyValue(nodepane.layoutYProperty(), node.ypos), 
				new KeyValue(line.startXProperty(), startx), 
				new KeyValue(line.startYProperty(), starty),
				new KeyValue(line.endXProperty(), endx), 
				new KeyValue(line.endYProperty(), endy)));
		
		return portionOfFrame;
	}
	
	private void RepositionNodeLine(AVLNode<T> node, double n, double xpos, double ypos, 
			double linestartx, double linestarty, int leftrightcheck) {
		int rightleftcheck = 0; // 0 : default, -1 : left, 1 : right
		
		RepositionNodeLineRecursion(node, n, xpos, ypos, linestartx, linestarty, leftrightcheck);
	}
	
	private void RepositionNodeLineRecursion(AVLNode<T> node, double n, double xpos, double ypos, 
			double linestartx, double linestarty, int leftrightcheck) {
		
		if (node != null) {
			
			node.xpos = xpos;
			node.ypos = ypos;
			
			if (AVLMain.RandomCheck==0) {
				StackPane nodepane = getStackPaneFromNode(node);
				Line line = getLineFromNode(node);
				
				if (leftrightcheck == -1) {
					//paralleltreemovement.getChildren().add(PickUpStackPaneLine(nodepane, line, node, linestartx, linestarty, node.xpos+RightX, node.ypos));
					MainTimeline.getKeyFrames().addAll(PickUpStackPaneLine(nodepane, line, node, linestartx, linestarty, node.xpos+RightX, node.ypos, cuepoint));
					// check after ====================================================================================================
				}
				else if (leftrightcheck == 1) {
					//paralleltreemovement.getChildren().add(PickUpStackPaneLine(nodepane, line, node, linestartx, linestarty, node.xpos, node.ypos));
					MainTimeline.getKeyFrames().addAll(PickUpStackPaneLine(nodepane, line, node, linestartx, linestarty, node.xpos, node.ypos, cuepoint));
					// check after ====================================================================================================
				}
			}
			
			if (node.left!=null) {
				RepositionNodeLineRecursion(node.left, n+1.5f, xpos-(2000*(1/(Math.pow((n+1.5), 2)))), (ypos+100.00f), node.xpos, node.ypos+LeftY, -1);
			}
			
			if (node.right != null) {
				RepositionNodeLineRecursion(node.right, n+1.5f, xpos+(2000*(1/(Math.pow((n+1.5), 2)))), (ypos+100.00f), node.xpos+RightX, node.ypos+RightY, 1);
			}
		}
	}
	
	private AVLNode<T> RightRotate(AVLNode<T> node, double n) {
		// Reposition GUI ==============================================================================================		
		
			// keep old node left left position for line end x and y;===========================================
			double endx = node.left.left.xpos;
			double endy = node.left.left.ypos;
			// keep old node left left position for line end x and y;===========================================
			
			double[][] newpos = new double[3][2];
			
			/*
			 *  new position | 0 (xpos)  |  1 (ypos)
			 * 0 (node.r)	    |			 |
			 * 1 (node)   |			 |
			 * 2 (node.l)  |			 |
			 */

			newpos[0][0] = node.xpos+(2000*(1/(Math.pow((n+1.5), 2))));
			newpos[0][1]= node.ypos+(100.00f);
			newpos[1][0] = node.xpos;
			newpos[1][1] = node.ypos;
			newpos[2][0] = node.left.xpos;
			newpos[2][1] = node.left.ypos;
			
		if (AVLMain.RandomCheck==0) {	
			double[][] linepos = new double[2][4];
			
			linepos[0][0] = newpos[1][0]+RightX; // startx
			linepos[0][1] = newpos[1][1]+RightY; // starty
			linepos[0][2] = newpos[0][0]; // endx
			linepos[0][3] = newpos[0][1]; // endy
			linepos[1][0] = newpos[1][0]; // startx
			linepos[1][1] = newpos[1][1]+LeftY; // starty
			linepos[1][2] = newpos[2][0]+RightX; // endx
			linepos[1][3] = newpos[2][1]; // endy
			
			System.out.println(space+"===>Animation rightRotation moveNodeFrame");
			MainTimeline.getKeyFrames().addAll(Animation1.moveNodeFrame(getStackPaneFromNode(node), newpos[0][0], newpos[0][1],
					getStackPaneFromNode(node.left), newpos[1][0], newpos[1][1],
					getStackPaneFromNode(node.left.left), newpos[2][0], newpos[2][1],
					getLineFromNode(node.left), linepos[0][0], linepos[0][1], linepos[0][2], linepos[0][3],
					getLineFromNode(node.left.left), linepos[1][0], linepos[1][1], linepos[1][2], linepos[1][3], endx+RightX, endy, (++cuepoint)));
			
			
			changeLineID(node.left, node);
			
			if (node != this.root) {
				changeLineID(node, node.left);
			}
		}
		
// Reposition GUI ==============================================================================================
		AVLNode<T>	CNL = node.left;
		AVLNode<T>	CNLR = CNL.right;

		if (node==this.root) 
			this.root = CNL;
		
		node.left = CNLR;
		CNL.right = node;
		CNL.right.height = ReAssignHeight(CNL.right);		
// reassign each node position====================================================
		CNL.right.xpos = newpos[0][0];
		CNL.right.ypos = newpos[0][1];
		
		CNL.xpos = newpos[1][0];
		CNL.ypos = newpos[1][1];
				
		CNL.left.xpos = newpos[2][0];
		CNL.left.ypos = newpos[2][1];
	// reassign each node position====================================================
		
		//repositioning children tree (other node)======================================================
		if (CNL.left.left!=null) {
			RepositionNodeLine(CNL.left.left,  n+3.0f,  CNL.left.xpos-(2000*(1/(Math.pow((n+3.0), 2)))), CNL.left.ypos+100.00f, CNL.left.xpos, CNL.left.ypos+LeftY, -1);
		}
		
		if (CNL.left.right!=null) {
			RepositionNodeLine(CNL.left.right,  n+3.0f,  CNL.left.xpos+(2000*(1/(Math.pow((n+3.0), 2)))), CNL.left.ypos+100.00f, CNL.left.xpos+RightX, CNL.left.ypos+RightY, 1);
		}
		
		if (CNL.right.left!=null) {
			RepositionNodeLine(CNL.right.left,  n+3.0f,  CNL.right.xpos-(2000*(1/(Math.pow((n+3.0), 2)))), CNL.right.ypos+100.00f, CNL.right.xpos, CNL.right.ypos+LeftY, -1);
		}
		
		if (CNL.right.right != null) {
			RepositionNodeLine(CNL.right.right,  n+3.0f,  CNL.right.xpos+(2000*(1/(Math.pow((n+3.0), 2)))), CNL.right.ypos+100.00f, CNL.right.xpos+RightX, CNL.right.ypos+RightY, 1);
		}
		
		
		//repositioning children tree (other node)======================================================

		return CNL;
	}
	
	private AVLNode<T> LeftRotate(AVLNode<T> node, double n) {
		// Reposition GUI ==============================================================================================		
		double[][] newpos = new double[3][2];
		
		// keep old node left left position for line end x and y;===========================================
		double endx = node.right.right.xpos;
		double endy = node.right.right.ypos;
		// keep old node left left position for line end x and y;===========================================
				
		
		/*
		 *  new position | 0 (xpos)  |  1 (ypos)
		 * 0 (node.l)	    |			 |
		 * 1 (node)   |			 |
		 * 2 (node.r)  |			 |
		 */
		
		
			newpos[0][0] = node.xpos-(2000*(1/(Math.pow((n+1.5), 2))));
			newpos[0][1]= node.ypos+(100.00f);
			newpos[1][0] = node.xpos;
			newpos[1][1] = node.ypos;
			newpos[2][0] = node.right.xpos;
			newpos[2][1] = node.right.ypos;
		
		if (AVLMain.RandomCheck==0) {	
			
			double[][] linepos = new double[2][4];
			
			linepos[0][0] = newpos[1][0]; // startx
			linepos[0][1] = newpos[1][1]+LeftY; // starty
			linepos[0][2] = newpos[0][0]+RightX; // endx
			linepos[0][3] = newpos[0][1]; // endy
			linepos[1][0] = newpos[1][0]+RightX; // startx
			linepos[1][1] = newpos[1][1]+RightY; // starty
			linepos[1][2] = newpos[2][0]; // endx
			linepos[1][3] = newpos[2][1]; // endy
			
			System.out.println(space+"===>Animation Leftrotation moveNodeFrame");
			MainTimeline.getKeyFrames().addAll(Animation1.moveNodeFrame(getStackPaneFromNode(node), newpos[0][0], newpos[0][1],
					getStackPaneFromNode(node.right),  newpos[1][0], newpos[1][1],
					getStackPaneFromNode(node.right.right), newpos[2][0], newpos[2][1],
					getLineFromNode(node.right), linepos[0][0], linepos[0][1], linepos[0][2], linepos[0][3],
					getLineFromNode(node.right.right), linepos[1][0], linepos[1][1], linepos[1][2], linepos[1][3], endx , endy, (++cuepoint)));
			
			
			changeLineID(node.right, node);
			
			if (node != this.root) {
				changeLineID(node, node.right);
			}
		}
		
// Reposition GUI ==============================================================================================
		
		AVLNode<T>	CNR = node.right;
		AVLNode<T>	CNRL = CNR.left;

		if (node==this.root) 
			this.root = CNR;

		node.right = CNRL;
		CNR.left = node;
		CNR.left.height = ReAssignHeight(CNR.left);

		// reassign each node position====================================================
		CNR.left.xpos = newpos[0][0];
		CNR.left.ypos = newpos[0][1];
		
		CNR.xpos = newpos[1][0];
		CNR.ypos = newpos[1][1];
				
		CNR.right.xpos = newpos[2][0];
		CNR.right.ypos = newpos[2][1];
		// reassign each node position====================================================
		
		//repositioning children tree (other node)======================================================
		if (CNR.right.right!=null) {
			RepositionNodeLine(CNR.right.right,  n+3.0f,  CNR.right.xpos+(2000*(1/(Math.pow((n+3.0), 2)))), CNR.right.ypos+100.00f, CNR.right.xpos+RightX, CNR.right.ypos+RightY, 1);
		}
		
		if (CNR.right.left!=null) {
			RepositionNodeLine(CNR.right.left,  n+3.0f,  CNR.right.xpos-(2000*(1/(Math.pow((n+3.0), 2)))), CNR.right.ypos+100.00f, CNR.right.xpos, CNR.right.ypos+LeftY, -1);
		}
		
		if (CNR.left.right!=null) {
			RepositionNodeLine(CNR.left.right,  n+3.0f,  CNR.left.xpos+(2000*(1/(Math.pow((n+3.0), 2)))), CNR.left.ypos+100.00f, CNR.left.xpos+RightX, CNR.left.ypos+RightY, 1);
		}
		
		if (CNR.left.left != null) {
			RepositionNodeLine(CNR.left.left,  n+3.0f,  CNR.left.xpos-(2000*(1/(Math.pow((n+3.0), 2)))), CNR.left.ypos+100.00f, CNR.left.xpos, CNR.left.ypos+LeftY, -1);
		}
		
		
		//repositioning children tree (other node)======================================================
		
		return CNR;
	}

	private AVLNode<T> MiddleRightRotate(AVLNode<T> node, double n) {
		
		// Reposition GUI ==============================================================================================		
		
		// keep old node left left position for line end x and y;===========================================
		double endx = node.right.left.xpos;
		double endy = node.right.left.ypos;
		// keep old node left left position for line end x and y;===========================================
		
		
		
		double[][] newpos = new double[3][2];
		
		/*
		 *  new position | 0 (xpos)  |  1 (ypos)
		 * 0 (node)	    |			 |
		 * 1 (node.r)   |			 |
		 * 2 (node.rr)  |			 |
		 */

		newpos[0][0] = node.xpos;
		newpos[0][1]= node.ypos;
		newpos[1][0] = node.right.xpos;
		newpos[1][1] = node.right.ypos;
		newpos[2][0] = node.right.xpos+(2000*(1/(Math.pow((n+3.0), 2)))); // n+=1.5 * 2
		newpos[2][1] = node.right.ypos+(100.00f);

		if (AVLMain.RandomCheck==0) {		
			double[][] linepos = new double[2][4];
			
			linepos[0][0] = newpos[1][0]+RightX; // startx
			linepos[0][1] = newpos[1][1]+RightY; // starty
			linepos[0][2] = newpos[2][0]; // endx
			linepos[0][3] = newpos[2][1]; // endy
			linepos[1][0] = newpos[0][0]+RightX; // startx
			linepos[1][1] = newpos[0][1]+RightY; // starty
			linepos[1][2] = newpos[1][0]; // endx
			linepos[1][3] = newpos[1][1]; // endy
			
			System.out.println(space+"===>Animation middleRightRotation moveNodeFrame");
			MainTimeline.getKeyFrames().addAll(Animation1.moveNodeFrame(getStackPaneFromNode(node), newpos[0][0], newpos[0][1],
					getStackPaneFromNode(node.right.left), newpos[1][0], newpos[1][1],
					getStackPaneFromNode(node.right), newpos[2][0], newpos[2][1],
					getLineFromNode(node.right), linepos[0][0], linepos[0][1], linepos[0][2], linepos[0][3],
					getLineFromNode(node.right.left), linepos[1][0], linepos[1][1], linepos[1][2], linepos[1][3], endx+RightX, endy, (++cuepoint)));
		}
						
		// Reposition GUI ==============================================================================================
				
		
		
		AVLNode<T> CopyofNRL = node.right.left;
		
		node.right.left = CopyofNRL.right;
		CopyofNRL.right = node.right;
		node.right = CopyofNRL;
		
		node.right.right.height = ReAssignHeight(node.right.right);
		node.right.height = ReAssignHeight(node.right);
		
		// reassign each node position====================================================
		
		node.xpos = newpos[0][0];
		node.ypos = newpos[0][1];
		
		node.right.xpos = newpos[1][0];
		node.right.ypos = newpos[1][1];
		
		node.right.right.xpos = newpos[2][0];
		node.right.right.ypos = newpos[2][1];
		
		// reassign each node position====================================================

		//repositioning children tree (other node)======================================================
		if (node.right.left!=null) {
			RepositionNodeLine(node.right.left,  n+3.0f,  node.right.xpos-(2000*(1/(Math.pow((n+3.0), 2)))), node.right.ypos+100.00f, node.right.xpos, node.right.ypos+LeftY, -1);
		}
		
		if (node.right.right.right!=null) {
			RepositionNodeLine(node.right.right.right,  n+4.5f,  node.right.right.xpos+(2000*(1/(Math.pow((n+4.5f), 2)))), node.right.right.ypos+100.00f, node.right.right.xpos+RightX, node.right.right.ypos+RightY, 1);
		}
		
		if (node.right.right.left!= null) {
			RepositionNodeLine(node.right.right.left,  n+4.5f,  node.right.right.xpos-(2000*(1/(Math.pow((n+4.5f), 2)))), node.right.right.ypos+100.00f, node.right.right.xpos, node.right.right.ypos+LeftY , -1);
		}
		
		
		//repositioning children tree (other node)======================================================
		
		return LeftRotate(node, n);

	}
	
	private AVLNode<T> MiddleLeftRotate(AVLNode<T> node, double n) {
		
		// Reposition GUI ==============================================================================================	
		
		// keep old node left left position for line end x and y;===========================================
		double endx = node.left.right.xpos;
		double endy = node.left.right.ypos;
		// keep old node left left position for line end x and y;===========================================
		
		
		double[][] newpos = new double[3][2];
		
		/*
		 *  new position | 0 (xpos)  |  1 (ypos)
		 * 0 (node)	    |			 |
		 * 1 (node.r)   |			 |
		 * 2 (node.rr)  |			 |
		 */

		newpos[0][0] = node.xpos;
		newpos[0][1]= node.ypos;
		newpos[1][0] = node.left.xpos;
		newpos[1][1] = node.left.ypos;
		newpos[2][0] = node.left.xpos-(2000*(1/(Math.pow((n+3.0), 2)))); // n+=1.5 * 2
		newpos[2][1] = node.left.ypos+(100.00f);
		
		if (AVLMain.RandomCheck==0) {
			double[][] linepos = new double[2][4];
			
			linepos[0][0] = newpos[1][0]; // startx
			linepos[0][1] = newpos[1][1]+LeftY; // starty
			linepos[0][2] = newpos[2][0]+RightX; // endx
			linepos[0][3] = newpos[2][1]; // endy
			linepos[1][0] = newpos[0][0]; // startx
			linepos[1][1] = newpos[0][1]+LeftY; // starty
			linepos[1][2] = newpos[1][0]+RightX; // endx
			linepos[1][3] = newpos[1][1]; // endy
			
			System.out.println(space+"===>Animation middleRightRotation moveNodeFrame");
			MainTimeline.getKeyFrames().addAll(Animation1.moveNodeFrame(getStackPaneFromNode(node), newpos[0][0], newpos[0][1],
					getStackPaneFromNode(node.left.right),  newpos[1][0], newpos[1][1],
					getStackPaneFromNode(node.left), newpos[1][0], newpos[2][1],
					getLineFromNode(node.left), linepos[0][0], linepos[0][1], linepos[0][2], linepos[0][3],
					getLineFromNode(node.left.right), linepos[1][0], linepos[1][1], linepos[1][2], linepos[1][3], endx, endy, (++cuepoint)));
		}
		
// Reposition GUI ==============================================================================================
	
		AVLNode<T> CopyofNLR = node.left.right;

		node.left.right = CopyofNLR.left;
		CopyofNLR.left = node.left;
		node.left = CopyofNLR;
		
		node.left.left.height = ReAssignHeight(node.left.left);
		node.left.height = ReAssignHeight(node.left);
		
		// reassign each node position====================================================
		
		node.xpos = newpos[0][0];
		node.ypos = newpos[0][1];
		
		node.left.xpos = newpos[1][0];
		node.left.ypos = newpos[1][1];
		
		node.left.left.xpos = newpos[2][0];
		node.left.left.ypos = newpos[2][1];
		
		// reassign each node position====================================================
		
		//repositioning children tree (other node)======================================================
		if (node.left.right!=null) {
			RepositionNodeLine(node.left.right,  n+3.0f,  node.left.xpos+(2000*(1/(Math.pow((n+3.0), 2)))), node.left.ypos+100.00f, node.left.xpos+RightX, node.left.ypos+RightY, 1);
		}
		
		if (node.left.left.left!=null) {
			RepositionNodeLine(node.left.left.left,  n+4.5f,  node.left.left.xpos-(2000*(1/(Math.pow((n+4.5), 2)))), node.left.left.ypos+100.00f, node.left.left.xpos, node.left.left.ypos+LeftY, -1);
		}
		
		if (node.left.left.right!= null) {
			RepositionNodeLine(node.left.left.right,  n+4.5f,  node.left.left.xpos+(2000*(1/(Math.pow((n+4.5), 2)))), node.left.left.ypos+100.00f, node.left.left.xpos+RightX, node.left.left.ypos+RightY, 1);
		}
		//repositioning children tree (other node)======================================================
		
		
		//System.out.println("(middleleftrotate)node="+node.element);
		return RightRotate(node, n);
	}
	
	public AVLNode<T> getNode(T e) {
		
		if (!this.isEmpty()) {
			if (this.search(e)) {
				return getNode(this.root, e);
			}
		}
		return null;
	}
	
	private AVLNode<T> getNode(AVLNode<T> node, T e) {
		
			if (e.compareTo(node.element)>0) {
				return getNode(node.right, e);
			}
			else if (e.compareTo(node.element)<0) {
				return getNode(node.left, e);
			}  
			else if (e.compareTo(node.element)==0) {
				return node;
			}
		
		return node;
	}
	
	public boolean search(T e) {
		if (!this.isEmpty()) {
			
			boolean be = false;
			
			be = search(this.root, e, be);
			
			return be;
		}
		else  {
			System.out.println("binary search tree is empty");
			return false;
		}	
	}
	// return boolean (true : found, false: no found)
	private boolean search(AVLNode<T> node,T e, boolean be) {
		if (node == null) {
			be = false;
			return be;
		}
		else {
			if (e.compareTo(node.element)>0) {
				be = search(node.right, e, be);
			}
			else if (e.compareTo(node.element)<0) {
				be = search(node.left, e, be);
			}  
			else if (e.compareTo(node.element)==0) {
				be = true;
				return be;
			}
		}
		return be;
	}
	
	public T MaxElement() {
		return MaxRecursion(this.root).element;
	}
	
	private AVLNode<T> MaxRecursion(AVLNode<T> node) {
		if(node.right == null) {
			return node;
		}
		else 
			return MaxRecursion(node.right);
	}
	
	public T MinElement() {
		return MinRecursion(this.root).element;
	}
	
	private AVLNode<T> MinRecursion(AVLNode<T> node) {
		if (node.left==null) {
			return node;
		}
		else 
			return MinRecursion(node.left);
	}
	
	public T Predecessor(AVLNode<T> node, T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				if (e.compareTo(this.MinElement())!=0) {
					
					AVLNode<T> pd = PredRec(node, e);
					
					if (pd == null) {
						return null;
					}
					else 
						return pd.element;
					
				}
			}
		}
		return null;
	}
	
	public T predecessor(T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				if (e.compareTo(this.MinElement())!=0) {
					
					AVLNode<T> pd = PredRec(this.root, e);
					
					if (pd == null) {
						return null;
					}
					else 
						return pd.element;
				}
			}
		}
		return null; 
	}
	
	private AVLNode<T> PredRec(AVLNode<T> node, T e) {
		//node>e
		if (node.element.compareTo(e)>0) {
			return PredRec(node.left, e);
		}//node<e
		else if (node.element.compareTo(e)<0) {
			this.pred = node;
			return PredRec(node.right, e);
		}
		else if (node.element.compareTo(e)==0) {
			if (node.left!=null) {
				return PredRightRecursion(node.left, e);
			}
			else 
				return this.pred;
		}
		return node;
	}
	
	private AVLNode<T> PredRightRecursion(AVLNode<T> node, T e) {
		
		if (node.right == null) {
			return node;
		}
		else {
			node = PredRightRecursion(node.right, e);
		}
		return node;
	}
	
	public T Successor(AVLNode<T> node, T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				if (e.compareTo(this.MaxElement())!=0) {
					
					AVLNode<T> sc = SuccRec(node, e);
					
					if (sc == null) {
						return null;
					}
					else 
						return sc.element;
				}
			}
		}
		return null; 
	}
	
	public T successor(T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				if (e.compareTo(this.MaxElement())!=0) {
					
					AVLNode<T> sc = SuccRec(this.root, e);
					
					if (sc == null) {
						return null;
					}
					else 
						return sc.element;
				}
			}
		}
		return null; 
	}
	
	private AVLNode<T> SuccRec(AVLNode<T> node, T e) {
		//node>e
		if (node.element.compareTo(e)>0) {
			this.succ = node;
			return SuccRec(node.left, e);
		}//node<e
		else if (node.element.compareTo(e)<0) {
			return SuccRec(node.right, e);
		}
		else if (node.element.compareTo(e)==0) {
			if (node.right!=null) {
				return succLeftRecursion(node.right, e);
			}
			else 
				return this.succ;
		}
		return node;
	}
	
	private AVLNode<T> succLeftRecursion(AVLNode<T> node, T e) {
		
		if (node.left == null) {
			return node;
		}
		else {
			node = succLeftRecursion(node.left, e);
		}
		return node;
	}
	
	private class SubAVLNode<T extends Comparable<T>> {
		AVLNode<T> sp; // successor or predecessor 
		AVLNode<T> tree; // tree that successor or predecessor was removed
		
		public SubAVLNode(AVLNode<T> s) {
			this.sp = s;
			this.tree = null;
		}
		
		public SubAVLNode(AVLNode<T> s, AVLNode<T> t) {
			this.sp = s;
			this.tree = t;

		}
	}
	
	public T Rm(T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				
				// gui===============================================
				if (AVLMain.RandomCheck==0) {
					
					AVLMain.SliderValue();
					
					cuepoint = 0;
					
					MainTimeline = new Timeline();
						
					if (AVLMain.p.lookup("#ball")==null) {
						ball = Animation1.genBall();
						AVLMain.p.getChildren().addAll(ball);
					}
				}
				// gui===============================================
				
				
				System.out.println(space+"===>SubNode<T> sn = RR(this.root, e="+e+")");
				SubAVLNode<T> sn = RR(this.root, e, 1.0f);
				
				if (sn.tree!=null) 
					System.out.println(space+"===>this.root = sn.tree="+sn.tree.element+"");
				else 
					System.out.println(space+"===>this.root = sn.tree=null");
				this.root = sn.tree;
				
				System.out.println(space+"<===return sn.sp.element="+sn.sp.element+"");
				
				// gui===============================================
				if (AVLMain.RandomCheck==0) {
					MainTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(startMillisec), "last",
							new EventHandler<ActionEvent>() {
								public void handle(ActionEvent arg0) {
									AVLMain.pb.setProgress(MainTimeline.getCurrentTime().toMillis()/MainTimeline.getCycleDuration().toMillis());
									System.out.println(MainTimeline.getCurrentTime().toMillis());
									
									MainTimeline.pause();	
								}
							},
							(KeyValue) null
							));
					
					MainTimeline.setCycleCount(MainTimeline.INDEFINITE);
					MainTimeline.setAutoReverse(false);
					MainTimeline.play();
					startMillisec = 100.0f;
				}
				cuepoint = 0;			
				// gui===============================================
				
				
				//this.displayNodeLine();
				this.displaySceneContents();
				
				this.size--;
				
				AVLMain.ta.clear();
				AVLMain.ta.setText("removed "+ sn.sp.element+ " successfully");
								
				return sn.sp.element;
			}
			AVLMain.ta.clear();
			AVLMain.ta.setText("invalid value");
		}
		AVLMain.ta.clear();
		AVLMain.ta.setText("tree is empty!");
		return null; 
	}
	
	private SubAVLNode<T> RR(AVLNode<T> node, T e, double num) {
		
		
		space += "          "; //10
		
		if (node != null)
			System.out.println(space+"===>RR(Node<T> node="+node.element+", T e="+e+")");
		else 
			System.out.println(space+"===>RR(Node<T> node=null, T e="+e+")");
		
		// draw left line ================GUI==============================================
		if (node != this.root) {
			String lineId = "line"+node.element;
			
			if (lineId.contains(".")) {
				 lineId = lineId.replace(".", "dot");
			 }
			
			Line line = (Line) AVLMain.p.lookup("#"+lineId);			
			//myanimation.getChildren().add(Animation1.ExistingLineTraverse(line, ball));
			
			MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
			
			
			System.out.println(space+"===>add animation traverse line="+line.getId()+"");
		}
		// draw left line ================GUI==============================================
		
		
		// add current node scale to animation ======================GUI=================================
		String nodeEle = ""+node.element;
		StackPane Node = getStackPaneFromNode(node);
		//myanimation.getChildren().addAll(Animation1.ScaleNode(Node));
		
		MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, (++cuepoint)));
		
		
		System.out.println(space+"===>add animation scale node="+Node.getId()+"");
		// add current node scale to animation ======================GUI=================================

		
		
		if (node.element.compareTo(e)>0) {
			System.out.println(space+"===>node.element="+node.element+".compareTo(e="+e+")>0");
			
			System.out.println(space+"===>SubNode<T> sn = RR(node.left="+node.left.element+", e="+e+");");
			SubAVLNode<T> sn = RR(node.left, e, (num+1.5f));//===============================================
			
			if (sn.tree!=null)
				System.out.println(space+"===>node.left="+node.left.element+" = sn.tree="+sn.tree.element+"");
			else 
				System.out.println(space+"===>node.left="+node.left.element+" = sn.tree=null");
			
			node.left = sn.tree;//===============================================
			
			if (sn.tree!=null)
				System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
			else 
				System.out.println(space+"===>sn.tree=null = node="+node.element+"");			
			
			sn.tree = node;//===============================================
			
			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			
			System.out.println(space+"<===return sn : sn.sp ="+sn.sp.element+", sn.tree = "+sn.tree.element+"");
			space = space.substring(0, space.length()-10);
			return sn;
			
		}
		
		if (node.element.compareTo(e)<0) {
			System.out.println(space+"===>node.element="+node.element+".compareTo(e="+e+")<0");
			
			
			System.out.println(space+"===>SubNode<T> sn = RR(node.right="+node.right.element+", e="+e+")");
			SubAVLNode<T> sn = RR(node.right, e, (num+1.5f));//===============================================
			
			if (sn.tree!=null)
				System.out.println(space+"===>node.right="+node.right.element+" = sn.tree="+sn.tree.element+"");
			else 
				System.out.println(space+"===>node.right="+node.right.element+" = sn.tree=null");
			node.right = sn.tree;//===============================================
			
			if (sn.tree!=null)
				System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
			else 
				System.out.println(space+"===>sn.tree=null = node="+node.element+"");			
			sn.tree = node;//===============================================
			
			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			System.out.println(space+"<===return sn : sn.sp ="+sn.sp.element+", sn.tree = "+sn.tree.element+"");
			space = space.substring(0, space.length()-10);
			return sn;
			
		}
		
		if (this.NumChild(node.element)==0) {
			System.out.println(space+"===>this.NumChild(node.element="+node.element+")==0");
			
			SubAVLNode<T> sn = new SubAVLNode(node, null);
			System.out.println(space+"===>SubNode<T> sn = new SubNode(node="+node.element+", null)");
			
			// delete node and line ========GUI=============================================================			
			
			StackPane removeNode = getStackPaneFromNode(node);
			Line removeLine = getLineFromNode(node);
			
			//myanimation.getChildren().add(Animation1.deleteNodeLine(removeNode, removeLine));
			
			MainTimeline.getKeyFrames().addAll(Animation1.deleteNodeLineFrame(removeNode, removeLine, (++cuepoint)));
			
			
			if (removeLine!=null)
			System.out.println(space+"===>add animation delete node="+removeNode.getId()+" and line="+removeLine.getId()+"");
			
			// delete node and line ========GUI=============================================================
			
			//==========AVL=============================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			System.out.println(space+"<===return sn : sn.sp = "+sn.sp.element+", sn.tree = null");
			space = space.substring(0, space.length()-10);
			return sn;
			
		}
				
		if (this.NumChild(node.element)>=1)	{
			System.out.println(space+"===>this.NumChild(node.element="+node.element+")>=1");
			
			//=========hold up Target node until you find out successor or predecessor======GUI==========
			StackPane tarPane = getStackPaneFromNode(node);
			//myanimation.getChildren().add(Animation1.holdUpTargetNode(tarPane));
			
			MainTimeline.getKeyFrames().addAll(Animation1.holdUpTargetNodeFrame(tarPane, (++cuepoint)));
			
			
			//=========hold up Target node until you find out successor or predecessor======GUI==========
			
			// ========keep target node position======GUI===============
			tarXpos = node.xpos;
			tarYpos = node.ypos;
			
			System.out.println(space+"===>add animation tarXpos =   node.xpos="+node.xpos+"");
			System.out.println(space+"===>add animation tarYpos =   node.ypos="+node.ypos+"");
			
			
			tarNode = new AVLNode(node.element, node.xpos, node.ypos);
			System.out.println(space+"===>add animation keep target node="+tarNode.element+")");
			// ========keep target node position======GUI===============

			SubAVLNode<T> sn = null;
			if (node.right!= null) {
				System.out.println(space+"===>node.right="+node.right.element+"!= null");
				
				System.out.println(space+"===>sn = RRS(node.right="+node.right.element+", e="+e+")");
				sn = RRS(node.right, e, (num+1.5f));
				
				System.out.println(space+"===>Node<T> temp = node="+node.element+"");
				
				AVLNode<T> temp = node;
				
				System.out.println(space+"===>node"+node.element+" = sn.sp="+sn.sp.element+"");
				node = sn.sp;
				
				if (temp.left!=null) {
					System.out.println(space+"===>node.left = temp.left="+temp.left.element+"");
					
				}
				else  {
					System.out.println(space+"===>node.left = temp.left=null");
				}
					
				node.left = temp.left;
								
				System.out.println(space+"===>sn.sp="+sn.sp.element+" = temp");
				sn.sp = temp;
				
				if (node.right != null) {
					if (sn.tree!=null) {
						System.out.println(space+"===>node.right="+node.right.element+" = sn.tree="+sn.tree.element+"");
						
					}
					else  {
						System.out.println(space+"===>node.right="+node.right.element+" = sn.tree=null");
						
					}
				}
				else {
					if (sn.tree!=null) {
						System.out.println(space+"===>node.right=null = sn.tree="+sn.tree.element+"");
						
					}
					else {
						System.out.println(space+"===>node.right=null = sn.tree=null");
						
					} 
				}
				
				node.right = sn.tree;
				
				
				
				
				System.out.println("====================================================num : "+num);
				
				// let's move portion tree affected by sop movement and remove target node=============================
				if (node.right != null) {
					RepositionNodeLine(node.right, (num+1.5f),  tarNode.xpos+(2000*(1/(Math.pow((num+1.5f), 2)))), tarNode.ypos+100.00f, tarNode.xpos+RightX, tarNode.ypos+RightY, 1);
					System.out.println(space+"===>add animation reposition of node="+node.right.element+" taht is affected removal");
				}
				
				StackPane removeNode = getStackPaneFromNode(tarNode);
				
				//myanimation.getChildren().add(Animation1.deleteOnlyNode(removeNode));
				
				MainTimeline.getKeyFrames().addAll(Animation1.deleteOnlyNodeFrame(removeNode, (++cuepoint)));
				
				
				//===============================================================================
				
				if (sn.tree!=null)
					System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
				else 
					System.out.println(space+"===>sn.tree=null = node="+node.element+"");	
				sn.tree = node;
	
				//=======================================================================================================			
				System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
				space += "          ";
				sn.tree = AVLchange(sn.tree, num);
				//=======================================================================================================
				
				
				System.out.println(space+"<===return sn.tree=null = node="+node.element+"");
				space = space.substring(0, space.length()-10);
				return sn;
			}
			else if (node.left != null) {
				System.out.println(space+"===>node.left="+node.left.element+"!= null");
				
				System.out.println(space+"===>sn = RRP(node.left="+node.left.element+", e="+e+")");
				sn = RRP(node.left, e, (num+1.5f));
				
				
				System.out.println(space+"===>Node<T> temp = node="+node.element+"");
				
				AVLNode<T> temp = node;
				
				System.out.println(space+"===>node"+node.element+" = sn.sp="+sn.sp.element+"");
				node = sn.sp;
				
				
				if (temp.right!=null) {
					System.out.println(space+"===>node.right = temp.right="+temp.right.element+"");
					
				}
				else {
					System.out.println(space+"===>node.right = temp.right=null");
					
				}
					
				node.right = temp.right;
								
				System.out.println(space+"===>sn.sp="+sn.sp.element+" = temp");
				sn.sp = temp;
				
				if (node.left != null) {
					if (sn.tree!=null) {
						System.out.println(space+"===>node.left="+node.left.element+" = sn.tree="+sn.tree.element+"");
					}
					else {
						System.out.println(space+"===>node.left="+node.left.element+" = sn.tree=null");
					} 
				}
				else {
					if (sn.tree!=null) {
						System.out.println(space+"===>node.left=null = sn.tree="+sn.tree.element+"");
					}
					else {
						System.out.println(space+"===>node.left=null = sn.tree=null");
					}
				}
				
				
				node.left = sn.tree;
				
				System.out.println("====================================================num : "+num);

				
				// let's move portion tree affected by sop movement and remove target node=============================
				if (node.left != null) {
					RepositionNodeLine(node.left, (num+1.5f),  tarNode.xpos-(2000*(1/(Math.pow((num+1.5f), 2)))), tarNode.ypos+100.00f, tarNode.xpos, tarNode.ypos+LeftY, -1);
					System.out.println(space+"===>add animation reposition of node="+node.left.element+" taht is affected removal");
				}
				
				StackPane removeNode = getStackPaneFromNode(tarNode);
				
				//myanimation.getChildren().add(Animation1.deleteOnlyNode(removeNode));
				
				MainTimeline.getKeyFrames().addAll(Animation1.deleteOnlyNodeFrame(removeNode, (++cuepoint)));
				
				//===============================================================================
				
				if (sn.tree!=null)
					System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
				else 
					System.out.println(space+"===>sn.tree=null = node="+node.element+"");	
				
				sn.tree = node;
				
				//=======================================================================================================			
				System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
				space += "          ";
				sn.tree = AVLchange(sn.tree, num);
				//=======================================================================================================
				
				System.out.println(space+"<===return sn.tree=null = node="+node.element+"");
				space = space.substring(0, space.length()-10);
				return sn;
				
				
			}
		}	
		
		return null;
	}

	private SubAVLNode<T> RRS(AVLNode<T> node, T e, double num) {
		
		space += "          "; //10
		
		if (node != null)
			System.out.println(space+"===>RRS(Node<T> node="+node.element+", T e="+e+")");
		else 
			System.out.println(space+"===>RRS(Node<T> node=null, T e="+e+")");
		
		if (node == null) {
			System.out.println(space+"===>node is null. return null");
			
			space = space.substring(0, space.length()-10);
			return null;
		}
		
		// draw left line ================GUI==============================================
		if (node != this.root) {
			String lineId = "line"+node.element;
			if (lineId.contains(".")) {
				 lineId = lineId.replace(".", "dot");
			 }
			
			Line line = (Line) AVLMain.p.lookup("#"+lineId);			
			//myanimation.getChildren().add(Animation1.ExistingLineTraverse(line, ball));
			
			MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
			
			
			System.out.println(space+"===>add animation  traverse line="+line.getId()+"");
		}
		// draw left line ================GUI==============================================
		/*
				^-----^
			   ( - _ - )
			   | |   | | 
		*/
		// add current node scale to animation ======================GUI=================================
		String nodeEle = ""+node.element;
		StackPane Node = getStackPaneFromNode(node);
		//myanimation.getChildren().addAll(Animation1.ScaleNode(Node));
		
		MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, (++cuepoint)));
		
		System.out.println(space+"===>add animation scale node="+Node.getId()+"");
		// add current node scale to animation ======================GUI=================================

		if (node.left==null) {
			System.out.println(space+"===>node="+node.element+".left == null");
			
			SubAVLNode<T> sn = new SubAVLNode(node, node.right);
			
			if (node.right != null)
				System.out.println(space+"===>SubNode<T> sn = new SubNode(node="+node.element+", node.right="+node.right+")");
			else 
				System.out.println(space+"===>SubNode<T> sn = new SubNode(node="+node.element+", node.right=null)");

			// let's move sop (successor or predecessor) to the target position and delete sop line=========================
			StackPane SopNode = getStackPaneFromNode(sn.sp);
			Line removeLine = getLineFromNode(sn.sp);
			StackPane scaleDownNode = getStackPaneFromNode(tarNode);
			
			//myanimation.getChildren().add(Animation1.moveSopAndDeleteLine(SopNode, removeLine, tarNode.xpos, tarNode.ypos, scaleDownNode));
			
			MainTimeline.getKeyFrames().addAll(Animation1.moveSopAndDeleteLineFrame(SopNode, removeLine, tarNode.xpos, tarNode.ypos, scaleDownNode, (++cuepoint)));
			
			
			System.out.println(space+"===>add animation move sop="+scaleDownNode.getId()+" to target position and remove line="+removeLine.getId()+"");
			
			node.xpos = tarNode.xpos;
			node.ypos = tarNode.ypos; // reassign sop node position to target node position (fields of position)
			
			changeLineID(tarNode, sn.sp);
			//=================================
			
			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			System.out.println(space+"<===return sn.sp = "+sn.sp.element+" sn.tree=null ");
			space = space.substring(0, space.length()-10);
			return sn;
		}
		
		if (node.left != null) {
			System.out.println(space+"===>node="+node.element+".left != null");
			
			System.out.println(space+"SubNode<T> sn = RRS(node.left="+node.left.element+", e="+e+")");
			SubAVLNode<T> sn = RRS(node.left, e, (num+1.5f));
			
			if (sn.tree!=null)
				System.out.println(space+"===>node.left="+node.left.element+" = sn.tree="+sn.tree.element+"");
			else 
				System.out.println(space+"===>node.left="+node.left.element+" = sn.tree=null");
			
			node.left = sn.tree;
			
			if (sn.tree!=null)
				System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
			else 
				System.out.println(space+"===>sn.tree=null = node="+node.element+"");

			sn.tree = node;
			
			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			System.out.println(space+"<===return sn.sp = "+sn.sp.element+" sn.tree=null ");
			space = space.substring(0, space.length()-10);
			return sn;
		}
		
		return null;
	}
	
	private SubAVLNode<T> RRP(AVLNode<T> node, T e, double num) {
		space += "          "; //10
		
		if (node != null)
			System.out.println(space+"===>RRP(Node<T> node="+node.element+", T e="+e+")");
		else 
			System.out.println(space+"===>RRP(Node<T> node=null, T e="+e+")");
		
		if (node == null) {
			System.out.println(space+"===>node is null. return null");
			
			space = space.substring(0, space.length()-10);
			return null;
		}
		
		// draw left line ================GUI==============================================
		if (node != this.root) {
			String lineId = "line"+node.element;
			if (lineId.contains(".")) {
				 lineId = lineId.replace(".", "dot");
			 }
			
			Line line = (Line) AVLMain.p.lookup("#"+lineId);			
			//myanimation.getChildren().add(Animation1.ExistingLineTraverse(line, ball));
			
			MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
		
			
			System.out.println(space+"===>add animation  traverse line="+line.getId()+"");
		}
		// draw left line ================GUI==============================================
		
		
		// add current node scale to animation ======================GUI=================================
		String nodeEle = ""+node.element;
		StackPane Node = getStackPaneFromNode(node);
		//myanimation.getChildren().addAll(Animation1.ScaleNode(Node));
		
		MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, (++cuepoint)));
		
		
		System.out.println(space+"===>add animation scale node="+Node.getId()+"");
		// add current node scale to animation ======================GUI=================================

		
		
		if (node.right==null) {
			System.out.println(space+"===>node="+node.element+".left == null");
			
			SubAVLNode<T> sn = new SubAVLNode(node, node.left);
			
			if (node.left != null)
				System.out.println(space+"===>SubNode<T> sn = new SubNode(node="+node.element+", node.right="+node.left+")");
			else 
				System.out.println(space+"===>SubNode<T> sn = new SubNode(node="+node.element+", node.right=null)");

			// let's move sop (successor or predecessor) to the target position and delete sop line=========================
			StackPane SopNode = getStackPaneFromNode(sn.sp);
			Line removeLine = getLineFromNode(sn.sp);
			StackPane scaleDownNode = getStackPaneFromNode(tarNode);
			
			//myanimation.getChildren().add(Animation1.moveSopAndDeleteLine(SopNode, removeLine, tarNode.xpos, tarNode.ypos, scaleDownNode));
			MainTimeline.getKeyFrames().addAll(Animation1.moveSopAndDeleteLineFrame(SopNode, removeLine, tarNode.xpos, tarNode.ypos, scaleDownNode, (++cuepoint)));
		
			
			System.out.println(space+"===>add animation move sop="+scaleDownNode.getId()+" to target position and remove line="+removeLine.getId()+"");
			
			node.xpos = tarNode.xpos;
			node.ypos = tarNode.ypos; // reassign sop node position to target node position (fields of position)

			
			changeLineID(tarNode, sn.sp);
			//=====and change target line id with sop line id============================

			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
						
			
			System.out.println(space+"<===return sn.sp= "+sn.sp.element+" sn.tree=null ");
			space = space.substring(0, space.length()-10);
			return sn;
		}
		
		if (node.right != null) {
			System.out.println(space+"===>node="+node.element+".right != null");
			
			System.out.println(space+"SubNode<T> sn = RRP(node.right="+node.right.element+", e="+e+")");
			SubAVLNode<T> sn = RRP(node.right, e, (num+1.5f));
			
			if (sn.tree!=null)
				System.out.println(space+"===>node.right="+node.right.element+" = sn.tree="+sn.tree.element+"");
			else 
				System.out.println(space+"===>node.right="+node.right.element+" = sn.tree=null");
			
			node.right = sn.tree;
			
			if (sn.tree!=null)
				System.out.println(space+"===>sn.tree="+sn.tree.element+" = node="+node.element+"");
			else 
				System.out.println(space+"===>sn.tree=null = node="+node.element+"");

			sn.tree = node;
			
			//=======================================================================================================			
			System.out.println(space+"===>sn.tree = AVLchange(sn.tree)");
			space += "          ";
			sn.tree = AVLchange(sn.tree, num);
			//=======================================================================================================
			
			System.out.println(space+"<===return sn.sp="+sn.sp.element+" sn.tree=null ");
			space = space.substring(0, space.length()-10);
			return sn;
		}
		
		return null;
	}
	
	public int NumChild(T e) {
		if (!this.isEmpty()) {
			if (this.search(e)) {
				return NumChild(this.root, e);
			}
		}
		return -1;
	}
	
	public int NumChild(AVLNode<T> node,T e) {
		
		if (e.compareTo(node.element)>0) {
			return NumChild(node.right, e);
		}
		else if (e.compareTo(node.element)<0) {
			return NumChild(node.left, e);
		}  
		else if (e.compareTo(node.element)==0) {
			if (node.right == null && node.left == null) {
				return 0;
			}
			else if (node.right == null|| node.left == null) {
				return 1;
			}
			else if (node.right!= null && node.left!=null) {
				return 2;
			}
		}
		return -1;
	}
	
	public static void displaySceneContents() {
		
		System.out.println("p (group) contents : ");
		
		for (int i = 0;i<AVLMain.p.getChildren().size();i++) {
			System.out.println(i+". "+AVLMain.p.getChildren().get(i).getId());
		}
	}
	
	public void preorder() {
		
		if (!this.isEmpty()){
			// gui===============================================
			AVLMain.SliderValue();
			MainTimeline = new Timeline();
			cuepoint = 0;
			// gui===============================================
		
			preorder(this.root);
			
			// gui===============================================
			MainTimeline.play();
			// gui===============================================
		}
	}
	
	// element, left, then right
	private void preorder(AVLNode<T> node)
	{
		if (node!=null) {
			System.out.println(node.element+" (x : "+node.xpos+" , y: "+node.ypos+" )");
			AVLMain.ta.appendText(String.valueOf(node.element) + " -> ");
			
			// add current node scale to animation ======================GUI=================================
			String nodeEle = ""+node.element;
			StackPane Node = getStackPaneFromNode(node);
			MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, ++cuepoint));
			
			// add current node scale to animation ======================GUI=================================
			
			preorder(node.left);
			preorder(node.right);
		}
	}
	
	public void inorder() { 
		
		if (!this.isEmpty()){
			// gui===============================================
			AVLMain.SliderValue();
			MainTimeline = new Timeline();
			cuepoint = 0;
			// gui===============================================
			
			inorder(this.root);
			
			// gui===============================================
			MainTimeline.play();
			// gui===============================================
		}
		
	}
	// left, element, then right
	private void inorder(AVLNode<T> node)
	{
		if (node!=null) {
			inorder(node.left);
			System.out.println(node.element+" (x : "+node.xpos+" , y: "+node.ypos+" )");
			AVLMain.ta.appendText(String.valueOf(node.element) + " -> ");
			
			// add current node scale to animation ======================GUI=================================
			String nodeEle = ""+node.element;
			StackPane Node = getStackPaneFromNode(node);
			MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, ++cuepoint));
			
			// add current node scale to animation ======================GUI=================================
			
			
			inorder(node.right);
		}
	}
	
	public void postorder() { 
		
		if (!this.isEmpty()){
			// gui===============================================
			AVLMain.SliderValue();
			MainTimeline = new Timeline();
			cuepoint = 0;
			// gui===============================================
		
			postorder(this.root);
			
			// gui===============================================		
			MainTimeline.play();
			// gui===============================================
		}
	}
	// left, right, then element;
	public void postorder(AVLNode<T> node)
	{
		if (node!=null) {
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.element+" ");
			AVLMain.ta.appendText(String.valueOf(node.element) + " -> ");
			
			// add current node scale to animation ======================GUI=================================
			String nodeEle = ""+node.element;
			StackPane Node = getStackPaneFromNode(node);
			MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, ++cuepoint));	
			// add current node scale to animation ======================GUI=================================
			
		}
	}
	//based on height;
	public void levelorder() {
		
		if (!this.isEmpty()){
			// gui===============================================
			AVLMain.SliderValue();
			MainTimeline = new Timeline();
			cuepoint = 0;
			// gui===============================================
	
			this.LevelOrder(this.root);
			
			// gui===============================================			
			MainTimeline.play();
			// gui===============================================
		}
	}
	
	private void LevelOrder(AVLNode<T> node) {
		String result = "";
		Queue<AVLNode<T>> nodeQ = new LinkedList<AVLNode<T>>();
		
		if (node!=null) {
			nodeQ.add(node);
			while(!nodeQ.isEmpty()){
				AVLNode<T> next = nodeQ.remove();
				System.out.println(next.element + " (x : "+next.xpos+" , y: "+next.ypos+" )");
				result += (String.valueOf(next.element) + " -> ");
				
				// add current node scale to animation ======================GUI=================================
				String nodeEle = ""+next.element;
				StackPane Node = getStackPaneFromNode(next);
				
				MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, ++cuepoint));
				// add current node scale to animation ======================GUI=================================
				
				if(next.left != null){
					nodeQ.add(next.left);
				}
				if(next.right != null){
					nodeQ.add(next.right);
				}
			}
			AVLMain.ta.appendText(result.substring(0,result.length() - 4));
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void DrawLineForRandom() {
		int leftrightCheck = 0; // 0 : default , -1 : left, 1 : right
		

		DrawLineForRandomRecursion(this.root, this.root.xpos, this.root.ypos, leftrightCheck);
	
	}
	
	public void DrawLineForRandomRecursion(AVLNode<T> node, double parentxpos, double parentypos, int check) {
		System.out.println(space+"===>public void DrawLineForRandomRecursion(AVLNode<T> node="+node.element+", double parentxpos="+parentxpos+", double parentypos="+parentypos+", int check"+check+")");
		
		if (node!=null) {
			System.out.println(space+"===>if (node!=null)");
			
			if (check==-1){
				System.out.println(space+"===>if (check==-1)");
				
				if (node!=this.root) {
					System.out.println(space+"===>if (node!=this.root)");
					
					System.out.println(space+"===>AVLMain.p.getChildren().add(Animation1.genLineForRandom(node.element"+node.element+", parentxpos="+parentxpos+" ,parentypos="+parentypos+"+LeftY, node.xpos="+node.xpos+"+RightX, node.ypos="+node.ypos+"))");
					AVLMain.p.getChildren().add(Animation1.genLineForRandom(""+node.element, parentxpos ,parentypos+LeftY, node.xpos+RightX, node.ypos));
				}
				
			}else if (check==1) {
				System.out.println(space+"===>else if (check==1)");
				
				if (node != this.root) {
					System.out.println(space+"===>if (node!=this.root)");

					System.out.println(space+"===>AVLMain.p.getChildren().add(Animation1.genLineForRandom(node.element="+node.element+", parentxpos="+parentxpos+"+RightX, parentypos"+parentypos+"+RightY, node.xpos="+node.xpos+", node.ypos="+node.ypos+"))");
					AVLMain.p.getChildren().add(Animation1.genLineForRandom(""+node.element, parentxpos+RightX, parentypos+RightY, node.xpos, node.ypos));
				}
			}
			
			if (node.left!=null) {
				System.out.println(space+"===>if (node.left="+node.left.element+"!=null)");
				
				System.out.println(space+"===>DrawLineForRandomRecursion(node.left="+node.left.element+", node.xpos="+node.xpos+", node.ypos="+node.ypos+", check="+check+")");
				space += "          ";
				DrawLineForRandomRecursion(node.left, node.xpos, node.ypos, -1);
				space = space.substring(0, space.length()-10);	
			}
			
			if (node.right!=null) {
				System.out.println(space+"===>if (node.right="+node.right.element+"!=null)");
				check = 1;
				System.out.println(space+"===>DrawLineForRandomRecursion(node.right="+node.right.element+", node.xpos="+node.xpos+", node.ypos="+node.ypos+", check="+check+")");
				space += "          ";
				DrawLineForRandomRecursion(node.right, node.xpos, node.ypos, 1);
				space = space.substring(0, space.length()-10);
			}

			
			
			
		}
	}
	
	public void displayNodePosition() {
		displayNodePositionRecursion(this.root);
	}
	
	public void displayNodePositionRecursion(AVLNode<T> node) {
		
		if (node!=null) {
			
			if (node== this.root) {
				System.out.println("this is root : ");
			}
			
			System.out.println(node.element+" (x : "+node.xpos+" , y: "+node.ypos+" )");
			
			if (node.left!=null) {
				displayNodePositionRecursion(node.left);
			}
			
			
			
			if (node.right!=null) {
				displayNodePositionRecursion(node.right);
			}
			
		}
	}
	
}
