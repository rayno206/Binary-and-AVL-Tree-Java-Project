import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;




import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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


// fix a node's xpos and ypos here, then apply to the Main class

public class BinaryTree<T extends Comparable<T>> extends Application {

	
	private AVLNode<T> root;
	private int size;
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
	
	public BinaryTree() {
		this.root = null;
		this.size=0;
		this.succ = null;
		this.pred = null;
	}
	
	public BinaryTree(T e) {
		this.root = new AVLNode<T>(e);
		this.size=1;
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
			// gui===============================================
			 if (AVLMain.RandomCheck==0){
				AVLMain.SliderValue();	
				cuepoint = 0;
				MainTimeline = new Timeline();
			}
			
			 if (AVLMain.p.lookup("#ball")==null) {
				ball = Animation1.genBall();
				AVLMain.p.getChildren().addAll(ball);
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
				MainTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(AVLTree.startMillisec), "last",
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

		
			//this.displayNodeLine();
			this.displaySceneContents();
			
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
			
			System.out.println(node);
			
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
		
		System.out.println(space+"===>return node="+node.element+"");
		space = space.substring(0, space.length()-10);
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
				
				if (line!=null) {
					
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
		
		ArrayList<KeyFrame> portionOfFrame = new ArrayList<KeyFrame>();
		
		// get original position 
		portionOfFrame.add(new KeyFrame(Duration.millis(AVLTree.startMillisec), ""+cue, 
				new EventHandler<ActionEvent>(){@Override
					public void handle(ActionEvent event) {
					
					if (AVLMain.BT.isSelected()) {
						AVLMain.pb.setProgress(BinaryTree.MainTimeline.getCurrentTime().toMillis()/BinaryTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(BinaryTree.MainTimeline.getCurrentTime().toMillis()/BinaryTree.MainTimeline.getTotalDuration().toMillis());
					}else if (AVLMain.AVL.isSelected()) {
						AVLMain.pb.setProgress(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
					}
				}},
				new KeyValue(nodepane.layoutXProperty(), nodepane.getLayoutX()), 
				new KeyValue(nodepane.layoutYProperty(), nodepane.getLayoutY()), 
				new KeyValue(line.startXProperty(), line.getStartX()), 
				new KeyValue(line.startYProperty(), line.getStartY()),
				new KeyValue(line.endXProperty(), line.getEndX()), 
				new KeyValue(line.endYProperty(), line.getEndY())));
		
		// get original position 
		portionOfFrame.add(new KeyFrame(Duration.millis(AVLTree.startMillisec+=(1*BinaryTree.incrementDuration)), 
				new EventHandler<ActionEvent>(){@Override
					public void handle(ActionEvent event) {
					if (AVLMain.BT.isSelected()) {
						AVLMain.pb.setProgress(BinaryTree.MainTimeline.getCurrentTime().toMillis()/BinaryTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(BinaryTree.MainTimeline.getCurrentTime().toMillis()/BinaryTree.MainTimeline.getTotalDuration().toMillis());
					}else if (AVLMain.AVL.isSelected()) {
						AVLMain.pb.setProgress(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
						System.out.println(AVLTree.MainTimeline.getCurrentTime().toMillis()/AVLTree.MainTimeline.getTotalDuration().toMillis());
					}
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
					MainTimeline.getKeyFrames().addAll(PickUpStackPaneLine(nodepane, line, node, linestartx, linestarty, node.xpos+RightX, node.ypos, cuepoint));
					// check after ====================================================================================================
				}
				else if (leftrightcheck == 1) {
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
				 if (AVLMain.RandomCheck==0){
						AVLMain.SliderValue();	
						cuepoint = 0;
						MainTimeline = new Timeline();
					}
					
					 if (AVLMain.p.lookup("#ball")==null) {
						ball = Animation1.genBall();
						AVLMain.p.getChildren().addAll(ball);
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
					MainTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(AVLTree.startMillisec), "last",
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
					AVLTree.startMillisec = 100.0f;
				}
				cuepoint = 0;		
				// gui===============================================
				
				//this.displayNodeLine();
				this.displaySceneContents();
				
				AVLMain.ta.clear();
				AVLMain.ta.setText("removed "+ sn.sp.element+ " successfully");
				
				this.size--;
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
			
			MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
			
			
			System.out.println(space+"===>add animation traverse line="+line.getId()+"");
		}
		// draw left line ================GUI==============================================
		
		
		// add current node scale to animation ======================GUI=================================
		String nodeEle = ""+node.element;
		StackPane Node = getStackPaneFromNode(node);
		
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
			
			MainTimeline.getKeyFrames().addAll(Animation1.deleteNodeLineFrame(removeNode, removeLine, (++cuepoint)));
			
			
			if (removeLine!=null)
			System.out.println(space+"===>add animation delete node="+removeNode.getId()+" and line="+removeLine.getId()+"");
			
			// delete node and line ========GUI=============================================================
			
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
				
			MainTimeline.getKeyFrames().addAll(Animation1.ExistingLineTraverseFrame(line, ball, (++cuepoint)));
		
			System.out.println(space+"===>add animation  traverse line="+line.getId()+"");
		}
		// draw left line ================GUI==============================================
		
		
		// add current node scale to animation ======================GUI=================================
		String nodeEle = ""+node.element;
		StackPane Node = getStackPaneFromNode(node);
	
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
			//Animation1.PlayAnimation(myanimation);
			
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
			//myanimation.getChildren().addAll(Animation1.ScaleNode(Node));
			MainTimeline.getKeyFrames().addAll(Animation1.ScaleNodeFrame(Node, ++cuepoint));
		
			
			// add current node scale to animation ======================GUI=================================
			
		}
	}
	//based on height;
	public void levelorder() {
		
		if (!this.isEmpty()){
			// gui===============================================
			AVLMain.SliderValue();
			//myanimation = new SequentialTransition();
			MainTimeline = new Timeline();
			
			
			cuepoint = 0;
			// gui===============================================
	
			this.LevelOrder(this.root);
			
			// gui===============================================
				//Animation1.PlayAnimation(myanimation);			
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
	public void start(Stage arg0) throws Exception {
		
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
}
