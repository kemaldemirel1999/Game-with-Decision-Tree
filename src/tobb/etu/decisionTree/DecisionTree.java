package tobb.etu.decisionTree;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DecisionTree {
	
	//Root of the tree
	private Node Root;
	//number of nodes in decision tree
	private int DTsize;
	
	public int size() {
		return DTsize;		
	}
	
	public Node getRoot() {
		return Root;		
	}
	
	//create and return a new node r storing element e and
	//make r the root of the tree.
	public Node addRoot(String s) {
		if(Root == null)
		{
			Node newest_node = new Node(s);
			Root = newest_node;
			DTsize  = 1;
		}
		else
		{
			Node newest_node = new Node(s);
			Root = newest_node;
		}
		
		//IMPLEMENT HERE
		
		return Root;
	}
	
    //create and return a new node w storing element el and 
    //make w the left (yes) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a left child.
	public Node insertYes(Node v, String el) {
		if(v.hasLeft())	throw new RuntimeException();
		Node w = new Node(el);
		v.setLeft(w);
		w.setParent(v);
		DTsize ++;
		
		//IMPLEMENT HERE
		
		return new Node(el);
	}

    //create and return a new node w storing element el and 
    //make w the right (no) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a right child.
	public Node insertNo(Node v, String el) {
		if(v.hasRight())	throw new RuntimeException();
		Node w = new Node(el);
		v.setRight(w);
		w.setParent(v);
		DTsize ++;
		//IMPLEMENT HERE
		
		return new Node(el);
	}
	
    //remove node v, replace it with its child, if any, and return 
    //the element stored at v. make sure to decrement the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if v has two children
	public String remove(Node v) {
		if(v.hasLeft() && v.hasRight())	throw new RuntimeException();
		Node child = v.getLeft()!=null ? v.getLeft() : v.getRight();
		if(child != null)	child.setParent(v.getParent());
		if(v == Root)	Root = child;
		else
		{
			Node parent = v.getParent();
			if(parent.getLeft() == v)	parent.setLeft(child);
			else	parent.setRight(child);
		}
		DTsize --;
		String answer = v.getElement();
		v.setLeft(null);v.setRight(null);v.setElement(null);v.setParent(v);
		return answer;
		
		//IMPLEMENT HERE
	}

    //write to file in PREORDER traversal order
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.	
	public boolean save(String filename) {
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(filename);
			Node current = getRoot();
			Node[] node_array = new Node[DTsize];
			outputStream.println(current.getElement());
			boolean you_can_add_left = true;
			boolean you_can_add_right = true;
			int index = 0;
			node_array[index ++] = current;
			while(node_array[DTsize -1] == null)
			{
				if(current == null)	break;
				you_can_add_left = true;
				you_can_add_right = true;
				if(current.getLeft() == null)	you_can_add_left = false;
				if(current.getRight() == null)	you_can_add_right = false;
				for(int i=0; i<node_array.length && you_can_add_left ; i++)
				{
					if(node_array[i] == current.getLeft())
						you_can_add_left = false;
				}
				for(int i=0; i<node_array.length && you_can_add_right; i++)
				{
					if(node_array[i] == current.getRight())
						you_can_add_right = false;
				}
				if(you_can_add_left)
				{
					current = current.getLeft();
					outputStream.println(current.getElement());
					node_array[index ++] = current;
				}
				else if(you_can_add_right)
				{
					current = current.getRight();
					outputStream.println(current.getElement());
					node_array[index ++] = current;
				}
				else
				{
					current = current.getParent();
				}
			}
		}catch(Exception e)
		{
			outputStream.close();
		//	e.printStackTrace();
			return false;
		}
		outputStream.close();
		//IMPLEMENT HERE

		return true;
	}	
    //load the DT from file that contains the output of PREORDER traversal
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.
    //You can distinguish a leaf node from an internal node as follows: internal nodes always end with
    //a question mark.	
	public boolean load(String filename) {	
		Scanner keyboard = null;
		try
		{
			keyboard = new Scanner(new FileInputStream(filename));
			addRoot(keyboard.nextLine());
			Node current = Root;
			while(keyboard.hasNextLine() )
			{
				Node new_node;
				Node child = null;
				if(!current.hasLeft())
				{
					new_node = new Node(keyboard.nextLine());
					if(!new_node.getElement().contains("?"))
					{
						current.setLeft(new_node);
						child = current.getLeft();
						child.setParent(current);
					}
					else
					{
						current.setLeft(new_node);
						child = current.getLeft();
						child.setParent(current);
						current = current.getLeft();
					}
					DTsize ++;
				}
				else if(!current.hasRight())
				{
					new_node = new Node(keyboard.nextLine());
					if(!new_node.getElement().contains("?"))
					{
						current.setRight(new_node);
						child = current.getRight();
						child.setParent(current);
						if(current.getParent()!=null)
						current = current.getParent();
						else	break;
					}
					else
					{
						current.setRight(new_node);
						child = current.getRight();
						child.setParent(current);
						current = current.getRight();
					}
					DTsize ++;
				}
				else
				{
					if(current.getParent() == null)	break;
					current = current.getParent();
					
				}
			}
		}catch(Exception e)
		{
			keyboard.close();
		//	e.printStackTrace();
			return false;
		}
		keyboard.close();
		//IMPLEMENT HERE

		return true;
	}
}
