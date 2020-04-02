import java.io.*;
import java.lang.Math; 

/**
 * The PositfixReader class used to turn Postfix to Infix and calculate the result.
 * @author natalie WONG Seung Yee
 * uid: 3035563453
 */
public class PostfixReader {
	
	
	// temperate string to hold the result (a + b)
	String[] temp_str = new String [10];
	
	 //result_str for calculation
	//out_str for output
	String result_str, out_str;
	
	//number flag to store 1 if it's computed result
	// store 0 if it's read from input to stack
	int[] num_flag = new int[100];
	
	//operand counter
	int operand_count = 0;
	
	//flag of string1 and string2
	int str1_flag = 0, str2_flag = 0;
	
	public static void main(String[] args) { 
		PostfixReader myAnswer = new PostfixReader();
		myAnswer.doConversion();
	}
	
	
	/**
	 * the doConversion object reads Postfix from input using readPostfix(), then convert it to infix
	 * result is also printed
	 */
	public void doConversion() {
	
		String[] input_str = readPostfix();
		
		//initialize the number flag to be zero
		for (int i = 0; i < 100; i++) {
			num_flag[i] = 0;
		}
		
	
		 // create a stack for calculation 
		Stack stack = new Stack(input_str.length);
		
	
		// create a stack for string output
		 
		Stack stack_out = new Stack (1000);
		
		for (int i = 0; i < input_str.length; i++) {
			
			//push the item to stack if it is an operand
				if (isOperand(input_str[i])) {
					stack.push(input_str[i]);
					stack_out.push(input_str[i]);
					operand_count++;
				}
				
				else if (isOperator(input_str[i])) {
					try {
						
						//pop the stack  if it is an operator 
						String str2 = stack.pop();
						String str2_out = stack_out.pop();
						
						//update the flag
						str2_flag = num_flag[operand_count - 1];
						// minus the operand counter
						operand_count--;
						
						String str1 = stack.pop();
						String str1_out = stack_out.pop();
						str1_flag = num_flag[operand_count - 1];
						operand_count--;
						
						String operator = input_str[i];
						
						//if both operands are read from output
						//generate result of the 2 items and store in the temp_str
						if (str1_flag == 0 && str2_flag == 0) {
							temp_str[0] = "(";
							temp_str[1] = str1;
							temp_str[2] = operator;
							temp_str[3] = str2;
							temp_str[4] = ")";
						}
						
						//the strings passed are turn into integers for calculation
						result_str = evalInfix(Integer.parseInt(str1), Integer.parseInt(str2), operator);
						
						//push back the calculated result and push back into the stack
						//since its calculated, the num_flag = 1
						stack.push(result_str);
						operand_count++;
						
						//Concatenate the output string
						out_str = "(" + " " + str1_out + " " + operator + " " + str2_out + " " + ")";
						
						stack_out.push(out_str);
						num_flag[operand_count - 1] = 1;
					}
					
					catch(Exception nullstack) {
						System.out.println("Error: Invalid postfix");
						return;
					}
				}
				else {
					System.out.println("Error: Invalid postfix");
					return;
				}
		}
		
		// print out the Infix and result
		System.out.println("Infix: " + out_str);
		System.out.println("Result: " + result_str);
		
		}

	
	
	/**
	 * The isOperand object used to checks to see if the item is an operand
	 * @param tempstr :string to be passed to check if it is an operand
	 */
	private boolean isOperand(String tempstr) {
		if (tempstr == null) {
			return false;
		}
		try {
			int dec = Integer.parseInt(tempstr);
		}	catch (NumberFormatException nfe) {
			return false;
		}
		return true;

	}
	
	/** 
	 * The isOperator object used to check to see if the item is an operator
	 * @isOperator tempstr: string to be passed to check if it is an operator
	 * @return boolean
	 */
	private boolean isOperator(String tempstr) {
		//check to see if the item is an operator
		if (tempstr == null) {
			return false;
		}
		if (tempstr.equals("+") || tempstr.equals("-") || tempstr.equals("*")|| tempstr.equals("/") || tempstr.equals("^")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * evaluate the result of the infix by calculation
	 * @param i1: numerical value of the first string
	 * @param i2: numerical value of the second string
	 * @param op: string of operator
	 * @return the result string
	 */
	public String evalInfix(int i1, int i2, String op) {
		int temp = 0;
		String temp_str;
		//compare the string and perform the corresponding arithmetic operation
		if (op.equals("+")) {
			temp = i1 + i2;
		}
		else if (op.equals("-")) {
			temp = i1 - i2;
		}
		else if (op.equals("*")) {
			temp = i1 * i2;
		}
		else if (op.equals("/")) {
			temp = i1 / i2;
		}
		else if (op.equals("^")) {
			temp = (int) Math.pow(i1, i2);
		}
		else {
			System.out.println("Invalid operator.");
		}
		//convert the result from integer back to string
		temp_str = Integer.toString(temp);
		return temp_str;
	}
	
	/**
	 * read from input from a line and store a string array
	 * @return the input string array
	 */
	public String[] readPostfix() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try {
			System.out.print("Input Postfix: ");
			inputLine = input.readLine();
			return inputLine.split(" ");
		} catch (IOException e) {
			System.err.println("Input ERROR.");
		}

		// return empty array if error occurs
		return new String[] {};
	}
	
}

/**
 * The Stack used to implement the stack with multiple functions (pop, push and isEmpty)
 * @author natalie
 *
 */
class Stack {
	private int stack_size;
	private String [] stack_arr;
	private int top;
	
	/**
	 * The isEmpty object used to check if the stack is empty or not
	 * @return the boolean value if the stack is empty or not
	 */
	public boolean isEmpty() {
		//check if the stack is empty or not
		return ( top < 0);
	}
	/**
	 * initiate the size of the stack
	 * @param size: size of the stack
	 */
	public Stack(int size) {
		stack_size = size;
		stack_arr = new String[size];
		top = -1;
	}
	
	/**
	 * push the string into stack
	 * @param sta: string to be pushed into the stack
	 */
	public void push(String sta) {
		// stack overflow if the top exceeds the size of the stack
		if (top >= stack_size - 1) {
			System.out.println("Stack Overflow");
		}
		else {
			//push the item at the top of the stack
			stack_arr[++top] = sta;
		}
	}
	
	/**
	 * pop the top item in the stack
	 * @return the top item of the stack
	 * @throws Exception of an empty stack
	 */
	public String pop()throws Exception {
		//the item cannot be popped if the stack is empty
		if (isEmpty()) {
			throw new Exception("Stack Underflow.");
		}
		else {
			//relocate the top
			//return the top item
			String stack_top = stack_arr[top];
			stack_arr[top] = "";
			top--;
			return stack_top;
		}
	}
}

