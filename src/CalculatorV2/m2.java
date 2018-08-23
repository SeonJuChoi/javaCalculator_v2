package CalculatorV2;

import java.util.*;
// stack calculator Simple ver...
public class m2 {
	public static void main(String[] args) {
		Stack st = new Stack();
		
		System.out.print("입력 : ");
		Scanner scn = new Scanner(System.in);
		char ch = 0;
		double num2 = 0.0;
		double num1 = 0.0;
		String numToTemp = "";
		String exp = scn.nextLine();
		
		char temp[] = exp.toCharArray();
	
		try{
		for(int i = 0 ; i < temp.length ; i++) {
			ch = temp[i];
		
			if(ch == '+' || ch == '-' || ch == '*' ||  ch == '/' ) {
				num2 = (double)st.pop();
				num1 = (double)st.pop();
				switch (ch){
					case '+':
						st.push(num1 + num2);
						break;
					case '-':
						st.push(num1 - num2);
						break;
					case '*' :
						st.push(num1 * num2);
						break;
					case '/' :
						st.push(num1 / num2);
						break;			
				}
			}
			else{	
				if(ch != ' ') {
					numToTemp += ch;
				}
				else if(numToTemp != ""){
				st.push((Double.valueOf(numToTemp)));
				numToTemp = "";
				}
			}
			
		}
		
		System.out.println(st.pop());
	}catch(Exception EmptyStackException){
		System.out.println("잘못된 식입니다.");
	}
	
		
	}

}
