
public class RedBlackTree {
	private boolean isBlack;
	private RedBlackTree leftChild, rightChild, parent;
	private int key; //key by which things are ordered, ftw
	private String data; //actual data!??!?!!?!?!?

	private RedBlackTree(int start_key, String start_data, RedBlackTree start_parent){
		isBlack = false;
		leftChild = null;
		rightChild = null;
		parent = start_parent;
		key = start_key;
		data = start_data;
	}
	public RedBlackTree(int start_key, String start_data){
		isBlack = true;
		leftChild = null;
		rightChild = null;
		parent = null;
		key = start_key;
		data = start_data;
	}
	public String getData(){
		return data;
	}
	public String getData(int data_key){
		if (data_key < key){
			if (leftChild == null){
				this.notFound(data_key);
				return null;
			}
			return leftChild.getData(data_key);
		}
		if (data_key > key){
			if (rightChild == null){
				this.notFound(data_key);
				return null;
			}
			return rightChild.getData(data_key);
		}
		return data;
	}
	
	public String printWholeTree(){
		String printable = data;
		if (isBlack){
			printable = printable + " is black;";
		}
		else{
			printable = printable + " is red;";
		}
		if (leftChild != null){
			printable = leftChild.printWholeTree() + printable;
		}
		if (rightChild != null){
			printable = printable + rightChild.printWholeTree();
		}
		return printable;
	}
	
	public RedBlackTree insert(int new_key, String new_data){
		//returns new root node, which you want to keep!
		if (new_key < key){
			if (leftChild == null){
				leftChild = new RedBlackTree(new_key, new_data, this);
				leftChild.fixRedBlack();
				return this.getRoot();
			}else{
				return leftChild.insert(new_key, new_data);
			}
		}else if (new_key > key){
			if (rightChild == null){
				rightChild = new RedBlackTree(new_key, new_data, this);
				rightChild.fixRedBlack();
				return this.getRoot();
			}else{
				return rightChild.insert(new_key, new_data);
			}			
		}
		data = data + new_data;
		dataAdded();
		return this.getRoot();
	}
	
	private RedBlackTree getRoot(){
		if (this.parent == null){
			return this;
		}
		return parent.getRoot();
	}
	private void fixRedBlack(){
		if (parent == null){
			isBlack = true;
		}else{
			fixPart2();
		}
	}
	private void fixPart2(){
		if (parent.isBlack){
			return;
		}else{
			fixPart3();
		}
	}
	private void fixPart3(){
		RedBlackTree uncle = getUncle();
		if (uncle == null || (uncle.isBlack)){
			fixPart4();
		}else{
			parent.isBlack = true;
			uncle.isBlack = true;
			RedBlackTree gramps = getGrandparent();
			gramps.isBlack = false;
			gramps.fixRedBlack();
		}
	}
	private void fixPart4(){
		RedBlackTree gramps = getGrandparent();
		if ((parent.rightChild == this) && (parent == gramps.leftChild)){
			parent.rotateLeft();
			leftChild.fixPart5();
		}else if ((parent.leftChild == this) && (parent == gramps.rightChild)){
			parent.rotateRight();
			rightChild.fixPart5();			
		}else{
			fixPart5();
		}
	}
	private void fixPart5(){
		RedBlackTree gramps = getGrandparent();
		parent.isBlack = true;
		gramps.isBlack = false;
		if (this == parent.leftChild){
			gramps.rotateRight();
		}else{
			gramps.rotateLeft();
		}
	}
	private void rotateRight(){
		RedBlackTree original_leftChild = leftChild;
		leftChild = original_leftChild.rightChild;
		if (leftChild != null){
			leftChild.parent = this;			
		}
		original_leftChild.rightChild = this;
		original_leftChild.parent = parent;
		parent = original_leftChild;	
	}
	private void rotateLeft(){
		RedBlackTree original_rightChild = rightChild;
		rightChild = original_rightChild.leftChild;
		if (rightChild != null){
			rightChild.parent = this;			
		}
		original_rightChild.rightChild = this;
		original_rightChild.parent = parent;
		parent = original_rightChild;
	}
	
	private RedBlackTree getGrandparent(){
		if (parent != null){
			return parent.parent;
		}
		return null;
	}
	
	private RedBlackTree getUncle(){
		RedBlackTree gramps = getGrandparent();
		if (gramps == null){
			return null;
		}
		if (gramps.leftChild == parent){
			return gramps.rightChild;
		}
		return gramps.leftChild;
	}
	
	private void notFound(int data_key){
		System.out.print("Key ");
		System.out.print(data_key);
		System.out.println(" not found in tree!");
	}
	
	private void dataAdded(){
		System.out.print("Key ");
		System.out.print(key);
		System.out.println(" already exists in tree!");
		System.out.println("Data appended!");
		System.out.print("Data now reads:");
		System.out.println(data);
	}
}
