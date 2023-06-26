package project;

import java.util.*;
import java.io.*;

public class Shopping {
	
	public static void start() {
		System.out.println("*** 쇼핑몰 프로그램 ***");
	    System.out.println("1. 쇼핑몰");
	    System.out.println("2. 고객 관리");
	    System.out.println("3. 제품 관리");
	    System.out.println("어떤 메뉴를 선택하시겠습니까? ");
	    
	    Scanner sc = new Scanner(System.in);
	      int menu = sc.nextInt();
	      
	      switch(menu) {
	         case 1 : 
	        	 break;
	         case 2 : {
	            customer acustomer = new customer();
	            acustomer.customerMenu();
	            break;
	         }
	         case 3 : 
	        	 break;
	         default : System.out.println("잘못된 입력입니다.");
	      }
	}
	
	
	
   public static void main(String[] args) {
      
	   start();
     
      
   	}
}