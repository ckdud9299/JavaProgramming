package project;

import java.util.*;

public class ShoppingMain {
	
	public static void start() {
		System.out.println("*** 쇼핑몰 프로그램 ***");
	    System.out.println("1. 쇼핑몰");
	    System.out.println("2. 고객 관리");
	    System.out.println("3. 제품 관리");
	    System.out.println("어떤 메뉴를 선택하시겠습니까? ");
	    
	    // 입력받은 조건에 따라 쇼핑몰, 고객관리, 제품관리로 분기 
	    Scanner sc = new Scanner(System.in);
	      int menu = sc.nextInt();
	      
	      switch(menu) {
	         case 1 : 
	        	 break;
	         case 2 : {
	        	 CustomerMain coustormerMain = new CustomerMain();
	        	 coustormerMain.customerMenu();
	            break;
	         }
	         case 3 : 
	        	 break;
	         default : System.out.println("잘못된 입력입니다.");
	      }
	}
	
   public static void main(String[] args) {
      // Main class의 start 메소드 호출
	   start();
    
   	}
}