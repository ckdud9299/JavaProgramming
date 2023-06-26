package project;

import java.util.*;

class customer{
//      private String name;
//      private int age;
//      private String gender;
//      private String address;
//      private String phoneNumber;
//      private String grade;
      
//      customer(String name, int age, String gender, String address, String phoneNumber, String grade){
//         this.name = name;
//         this.age = age;
//         this.address = address;
//         this.phoneNumber = phoneNumber;
//         this.grade = grade;
//      }
		customer(){}
      
      public void customerManu() {
         System.out.println("1.입력");
         System.out.println("2.수정");
         System.out.println("3.삭제");
         System.out.println("4. 메인 메뉴로 돌아가기");
      }
}

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
               customer m = new customer();
               m.customerManu();
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
      
   