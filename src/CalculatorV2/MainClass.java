package CalculatorV2;

import java.util.*;

class Calculatorv2 {
	// �����ڿ� ���ڸ� ���ϱ� ���� ����� ������ �迭
	char opArray[] = {'+', '-', '*', '/', '(', ')'};
	
	boolean inputValueCheck(String inputValue) {
		// ������ üũ
		boolean opPlusCheck = false; 
		boolean opMinusCheck = false;
		boolean opMultiplyCheck = false;
		boolean opDivisionCheck = false;
		boolean firstDivide = true;
		boolean negativeNumCheck = false;
		
		
		char opCheckArr[] = inputValue.toCharArray();
		
		if(inputValue.equals("")){
			return false;
		}
		
		// +, - , *, /�� ������ �˻�
		for(int i = 0 ; i < opCheckArr.length ; i++){
			if(i == 0){
				if(opCheckArr[i] == '+'|| opCheckArr[i] == '-' || opCheckArr[i] == '*' || opCheckArr[i] == '/'){
					return false;
				}
			}
			if(i == (opCheckArr.length - 1 )){
				if(opCheckArr[i] == '+'|| opCheckArr[i] == '-' || opCheckArr[i] == '*' || opCheckArr[i] == '/'){
					return false;
				}
			}
		}
		
		// ++, --, +-, -+ ��ȿ�� �˻�	
		for(int i = 0 ; i < opCheckArr.length ; i++){
			if(opCheckArr[i] == '+' || opCheckArr[i] == '-' ){
				if(i == 0){
					return false;
				}
				i++;
				if(opCheckArr[i] == '+' || opCheckArr[i] == '-'){
					return false;
				}
			}
			else if(opCheckArr[i] == '*' || opCheckArr[i] == '/' ){
				i++;
				if(opCheckArr[i] == '*' || opCheckArr[i] == '/'){
					return false;
				}
			}
			
			
		}
		int numCheck = 0;
	
		// () �˻�
		char ch = 0;
		Stack checkSt = new Stack();
		
		try{
			for(int i = 0 ; i < inputValue.length() ; i++ ){
				ch = inputValue.charAt(i);
				if(ch == '(')
					checkSt.push(ch);
				else if(ch == ')')
					checkSt.pop();
			}
			if(checkSt.isEmpty()){
				return true;
			}
			else{
				return false;
			}
		} catch (EmptyStackException e){
			System.out.println("��ȣ�� ��ġ���� �ʽ��ϴ�");
			return false;
		}
		
		
			
	}
	
	// ����ǥ���� -> ����ǥ����
	String infixToPostfix(String exp) {
		char ch = 0; // ���� ǥ���Ŀ� �����ڸ� �����ϱ� ���� �ӽ÷� ���
		boolean check = true; // ���� ��ȿ�� �˻� 
		
		check = inputValueCheck(exp);
		if(check == false){ // ���� �ùٸ��� �������
			return null; // null�� ��ȯ
		}
		
		// �����ڸ� �����ϱ� ���� ����
		Stack op = new Stack();
		String postfixExp = ""; // ���� ǥ����
		
		for(int i = 0 ; i < exp.length() ; i++){
			switch(exp.charAt(i)){
			// ������ ���
			 	case '0':
			 	case '1':
			 	case '2':
			 	case '3':
			 	case '4':
			 	case '5':
			 	case '6':
			 	case '7':
			 	case '8':
			 	case '9': {
			 		// ���ڸ� ����ǥ���Ŀ� �߰�
			 		postfixExp += exp.charAt(i);
			 		// ���ڸ� �̻��� ��� " "�� ���⸦ �Ͽ� ����
					if(i != exp.length() - 1){
						for(int j = 0 ; j < opArray.length ; j++ ){
							if(exp.charAt(i + 1) == opArray[j])
								postfixExp += " ";
						}
					}
					break;
			 	}
			 	case ')' : {
					// �ݴ� ��ȣ�� ���ð�� stack�� �����ڵ��� (�� ���ö����� pop
					ch = (char)op.pop();
					
					while(ch != '(') {
						postfixExp += ch;
						ch = (char)op.pop();
					}
					break;
				}
				case '(' : {
					// ���� ��ȣ�� �� ��� (��ȣ�� push)
					op.push(exp.charAt(i));
					break;
				}
				// �������� ���
				case '+':
				case '-': 
				case '*': 
				case '/' : {
					// �켱������ ��
					while(!op.isEmpty() && (prioOp(exp.charAt(i)) <= prioOp((char)op.peek()))){
						// �켱������ �۰ų� ���� ��� pop	
						postfixExp += op.pop();
							break;
					}
					// �켱���� �� �� push
					op.push(exp.charAt(i));
					break;
				}
				
				default : {
					postfixExp += exp.charAt(i);
					break;
				}		
			}
		}
		
		while(!op.isEmpty()){
			// ���� �����ڵ��� pop�ؼ� �Ŀ� ����
			postfixExp += op.pop();
		}
		// ����ǥ��� ��ȯ
		return postfixExp;
	 }
	
	void postfixCal(String postfix) {
		int pfSize = postfix.length();
		char ch = 0;
		String temp = "";
		double num1 = 0;
		double num2 = 0;
		boolean flag = true;
		
		Stack calStack = new Stack();
		
		for(int i = 0 ; i < pfSize ; i++){
			ch = postfix.charAt(i);
			
				if(ch == '+' || ch == '-' || ch == '*' ||  ch == '/' ){
					num2 = (double)calStack.pop();
					num1 = (double)calStack.pop();
					switch (ch){
						case '+':
							calStack.push(num1 + num2);
							break;
						case '-':
							calStack.push(num1 - num2);
							break;
						case '*' :
							calStack.push(num1 * num2);
							break;
						case '/' :
							calStack.push(num1 / num2);
							break;			
					}
				}
				else{
					while(flag == true && (postfix.charAt(i) != '+' && postfix.charAt(i) != '-' && postfix.charAt(i) != '*' 
							&& postfix.charAt(i) != '/') && postfix.charAt(i) != ' '){
						temp += postfix.charAt(i);
						i++;
					}
						if(postfix.charAt(i) != ' ' || i == pfSize - 1){
							i--;
							flag = false;
						}
					
					if(temp != "")
						calStack.push((Double.valueOf(temp)));
					temp = "";
				
				}
				
			}
		
		
		System.out.println(calStack.pop());
		
	}
	
	int prioOp (char op) {
		// �켱����
		switch(op) {
			case '(' :
			case ')' :
				return 0;
			case '+' :
			case '-' : 
				return 1;
			case '*' :
			case '/' : 
				return 2;
			default :
				return -1;
		}
	}

}


public class MainClass {
	
	public static void main(String[] args) {
		Calculatorv2 a = new Calculatorv2();
		String result = "";
		String tempOp = "";
		String inputOp = "";
		System.out.print("���� �Է��ϼ��� : ");
		Scanner inputValue = new Scanner(System.in);
		
		tempOp = inputValue.nextLine();
		
		inputOp = tempOp.replaceAll(" ", "");
		
		result = a.infixToPostfix(inputOp);
		if(result == null){
			System.out.println("�߸��� ���Դϴ�");
		}
		else{
			System.out.println("�ùٸ� ���Դϴ�");
			System.out.println("���� ���� ��� : " + result);
			a.postfixCal(result);
		
		}
	}
	
}
