package CalculatorV2;

import java.util.*;

class Calculatorv2 {
	// 연산자와 숫자를 비교하기 위해 사용할 연산자 배열
	char opArray[] = {'+', '-', '*', '/', '(', ')'};
	
	boolean inputValueCheck(String inputValue) {
		// 연산자 체크
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
		
		// +, - , *, /로 끝날때 검사
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
		
		// ++, --, +-, -+ 유효성 검사	
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
	
		// () 검사
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
			System.out.println("괄호가 일치하지 않습니다");
			return false;
		}
		
		
			
	}
	
	// 중위표현식 -> 후위표현식
	String infixToPostfix(String exp) {
		char ch = 0; // 후위 표현식에 연산자를 저장하기 위해 임시로 사용
		boolean check = true; // 식의 유효성 검사 
		
		check = inputValueCheck(exp);
		if(check == false){ // 식이 올바르지 않을경우
			return null; // null값 반환
		}
		
		// 연산자를 저장하기 위한 스택
		Stack op = new Stack();
		String postfixExp = ""; // 후위 표현식
		
		for(int i = 0 ; i < exp.length() ; i++){
			switch(exp.charAt(i)){
			// 숫자일 경우
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
			 		// 숫자를 후위표현식에 추가
			 		postfixExp += exp.charAt(i);
			 		// 두자리 이상일 경우 " "로 띄어쓰기를 하여 구분
					if(i != exp.length() - 1){
						for(int j = 0 ; j < opArray.length ; j++ ){
							if(exp.charAt(i + 1) == opArray[j])
								postfixExp += " ";
						}
					}
					break;
			 	}
			 	case ')' : {
					// 닫는 괄호가 나올경우 stack의 연산자들을 (가 나올때까지 pop
					ch = (char)op.pop();
					
					while(ch != '(') {
						postfixExp += ch;
						ch = (char)op.pop();
					}
					break;
				}
				case '(' : {
					// 여는 괄호가 들어갈 경우 (괄호를 push)
					op.push(exp.charAt(i));
					break;
				}
				// 연산자일 경우
				case '+':
				case '-': 
				case '*': 
				case '/' : {
					// 우선순위를 비교
					while(!op.isEmpty() && (prioOp(exp.charAt(i)) <= prioOp((char)op.peek()))){
						// 우선순위가 작거나 같을 경우 pop	
						postfixExp += op.pop();
							break;
					}
					// 우선순위 비교 후 push
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
			// 남은 연산자들을 pop해서 식에 저장
			postfixExp += op.pop();
		}
		// 후위표기식 반환
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
		// 우선순위
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
		System.out.print("식을 입력하세요 : ");
		Scanner inputValue = new Scanner(System.in);
		
		tempOp = inputValue.nextLine();
		
		inputOp = tempOp.replaceAll(" ", "");
		
		result = a.infixToPostfix(inputOp);
		if(result == null){
			System.out.println("잘못된 식입니다");
		}
		else{
			System.out.println("올바른 식입니다");
			System.out.println("후위 연산 결과 : " + result);
			a.postfixCal(result);
		
		}
	}
	
}
