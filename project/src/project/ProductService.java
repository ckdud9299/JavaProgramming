package project;

import java.io.*;
import java.util.*;

public class ProductService {
	// Customer Main에서 필드값 리스트로 만들기
	ArrayList<ProductMain> productList = new ArrayList<>();
	HashMap<String, ProductMain> productHash = new HashMap<>();
	Scanner sc = new Scanner(System.in);

	// ***Insert Method***///
	public void insert(int stepNo) {
		int isStempNo = stepNo; // stepNo
		String productId = null, productName = null, category = null;
		int price = 0, quantity = 0;

		isStempNo++; // stempNo=1

		// *** Step1 = 제품 ID 입력받기 ***//
		if (isStempNo == 1) {
			System.out.println("==========제품 정보를 입력합니다==========");
			System.out.println("제품의 id를 입력하세요 = ");
			productId = sc.next();

			// 중복된 id값 Hash로 찾기
			if (productHash.containsKey(productId)) {
				System.out.println("이미 등록된 제품 id입니다.");
				insert(isStempNo); // 중복시 insert 다시 호출
				return;
			}
			isStempNo++;
		}
		// *** Step2 = 제품명 입력받기 ***//
		if (isStempNo == 2) {
			// 제품명 입력받기
			System.out.println("제품명을 입력하세요 = ");
			productName = sc.next();
			isStempNo++;
		}
		// *** Step3 = 카테고리 입력받기 ***//
		if (isStempNo == 3) {
			System.out.println("카테고리를 입력하세요 = ");
			category = sc.next();
			isStempNo++;
		}
		// *** Step4 = 가격 입력받기 ***//
		if (isStempNo == 4) {
			// 정상적으로 Int값 들어올때까지 반복
			while (true) {
				try {
					System.out.println("가격을 입력하세요 = ");
					price = sc.nextInt();
					isStempNo++;
					break;
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("[ERROR]가격이 잘못 입력되었습니다.");
				}
			}
		}
		// *** Step5 = 수량 입력받기 ***//
		if (isStempNo == 5) {
			// 정상적으로 Int값 들어올때까지 반복
			while (true) {
				try {
					System.out.println("수량을 등록하세요 = ");
					quantity = sc.nextInt();
					isStempNo++;
					break;
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("[ERROR]수량이 잘못 입력되었습니다.");
				}
			}
		}
		// *** Step6 = 입력받은 값 저장 ***//
		if (isStempNo == 6) {
			ProductMain productMain = new ProductMain(productId, productName, category, price, quantity);
			productList.add(productMain); // *** 입력받은 값 ArrayList에 저장 *** //
			productHash.put(productId, productMain); // *** 입력받은 값 HashMap에 저장 *** //
			System.out.println("==========제품 정보 입력을 완료했습니다==========");
		}
	}

	// ***edit Method***///
	public void edit(int stepNo) {
		int edStempNo = stepNo; // stepNo
		String productId = null;
		int n = 0, index = 0;
		edStempNo++; // stepNo=1

		// ***step1=수정할 제품id 입력받기 ***//
		if (edStempNo == 1) {
			System.out.println("==========제품 정보를 수정합니다==========");
			System.out.println("수정할 제품 id를 입력하세요 =");
			productId = sc.next();
			edStempNo++;
		}
		// ***step2 수정할 항목 선택하기 ***//
		if (edStempNo == 2) {
			// 수정하고자하는 제품의 id가 등록되어 있는지 확인하기
			if (productHash.containsKey(productId)) {
				while (true) {
					try {
						System.out.println("수정하실 항목을 선택하세요!");
						System.out.println("1.제품명 2.카테고리 3.가격 4.수량");
						n = sc.nextInt();
						if (n > 4 || n < 1) {
							System.out.println("[ERROR]1~4번을 입력해주세요.");
						} else {
							edStempNo++;
							break;
						}
					} catch (Exception e) {
						sc.nextLine();
						System.out.println("[ERROR]숫자를 입력해주세요.");
					}
				}
			}else {
				System.out.println("[ERROR]등록되지 않은 id입니다.");
				edit(0);
				return;
		}
		// *** step3 HashMap, ArrayList indexNo 찾기 && 값 수정 ***//
		if (edStempNo == 3) {
			// 고객 메인 = 해쉬맵에서 입력받은 id의 값을 넣어준다 productHash.get(id) = 주소값 반환
			ProductMain productMain = productHash.get(productId);
			// 해쉬맵에서 찾은 값을 리스트indexof에 넣어줘서, 리스트의 값이 위치하는 인덱스 번호를 찾는다.
			index = productList.indexOf(productMain);

			switch (n) {
			case 1:
				System.out.println("제품명을 새로 입력하세요 =");
				String productName = sc.next();
				// 해쉬맵 값 바꾸기 // productMain은 productHash.get(productId) 값의 주소를 받았다.
				// 그러므로 setProductName 을 해주면 해당값을 바꿀수있다.
				productMain.setProductName(productName); // 해쉬맵 값 바꾸기
				productList.get(index).setProductName(productName); // 리스트 값 바꾸기
				break;
			case 2:
				System.out.println("카테고리를 새로 입력하세요 =");
				String category = sc.next();
				productMain.setProductName(category);
				productList.get(index).setCategory(category);
				break;
			case 3:
				System.out.println("가격을 새로 입력하세요 =");
				while (true) {
					try {
						int price = sc.nextInt();
						productMain.setPrice(price);
						productList.get(index).setPrice(price);
						break;
					} catch (Exception e) {
						sc.nextLine();
						System.out.println("[ERROR]숫자를 입력해주세요.");
					}
				}
				break;
			case 4:
				while (true) {
					try {
						System.out.println("수량을 새로 입력하세요. = ");
						int quantity = sc.nextInt();
						productMain.setPrice(quantity);
						productList.get(index).setQuantity(quantity);
						break;
					} catch (Exception e) {
						sc.nextLine();
						System.out.println("[ERROR]숫자를 입력해주세요.");
					}
				}
			default:
				break;
			}

		}
	}

	}

	// ***Product Delete Method***///
	public void delete(int stepNo) {
		int deStepNo = stepNo;
		String productId = null;
		deStepNo++;
		// *** step1 제품 id 입력받기 *** //
		if (deStepNo == 1) {
			System.out.println("==========제품을 삭제합니다.==========");
			System.out.println("삭제할 제품 id를 입력해주세요 = ");
			productId = sc.next();
			deStepNo++;
		}
		// *** step2 제품 삭제 ***//
		if (deStepNo == 2) {
			if (productHash.containsKey(productId)) {
				// ArrayList의 값을 바꾸기 위해서 index값을 찾아준다.
				ProductMain productMain = productHash.get(productId);
				int index = productList.indexOf(productMain);
				productHash.remove(productId);
				productList.remove(index);
				System.out.println(productId + " " + "제품 아이디가 삭제되었습니다.");
				return;
			} else
				System.out.println("[ERROR] 입력하신 제품은 없습니다.");
		}
	}

	// ***Single Product View Method***///
	public void SingleView(int stepNo) {
		int svStepNo = stepNo;
		String productId = null;
		svStepNo++;
		// *** step1 제품 id 입력받기 *** //
		if (svStepNo == 1) {
			System.out.println("==========단일 제품을 출력합니다.==========");
			System.out.println("제품의 Id를 입력하세요 = ");
			productId = sc.next();
			svStepNo++;
		}
		// *** step2 입력한 제품출력 *** //
		if (svStepNo == 2) {
			if (productHash.containsKey(productId)) {
				// *** 출력문 *** //
				System.out.printf("%-10s%-10s%-10s%-10s%-10s\n", "제품이름", "제품ID", "카테고리", "가격", "수량");
				productHash.get(productId).productString();
			} else
				System.out.println("해당 제품은 없습니다.");
		}
	}

	// *** All Product List View Method *** //
	public void view(int stepNo) {
		int vStepNo = stepNo;
		vStepNo++;
		// step1 제품 전부 출력
		if (vStepNo == 1) {
			// 리스트가 비어있지 않으면 등록된 제품 출력
			System.out.println("==========등록된 제품을 모두 출력합니다.==========");
			System.out.printf("%-10s%-10s%-10s%-10s%-10s\n", "제품이름", "제품ID", "카테고리", "가격", "수량");
			if (!productList.isEmpty()) {
				for (ProductMain productMain : productList) {
					productMain.productString();
				}
				// 리스트가 비어있으면 view 메소드 종료
			} else if (productList.isEmpty()) {
				System.out.println("등록된 제품이 없습니다.");
				return;
			}
		}
	}

	// *** File Save Method *** //
	public void FileSave() {
		// *** 1. 파일 존재여부 체크 및 생성 ***//
		String path = "product.txt";
		File file = new File(path);
		// ***2. 파일 존재여부 체크 및 생성 ***//
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// *** 3. FileWriter를 사용해서 ***//
			FileWriter fileWriter = new FileWriter(file, false); // false = 덮어쓰기 , write = 기존꺼 밑에 쓰기
			BufferedWriter writer = new BufferedWriter(fileWriter);

			// *** 4. 입력된 arraylist값 "," 붙여서 메모장 생성 ***//
			for (ProductMain e : productList) {
				String str = e.getProductId() + "," + e.getProductName() + "," + e.getCategory() + "," + e.getPrice()
						+ "," + e.getQuantity();
				writer.append(str + "\n");
			}
			// *** 5. 파일 닫기 ***//
			writer.close();
			// System.out.println("리스트 메모장 저장 성공! ");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Fileread() throws IOException {
		// *** 1. 파일객체생성 *** //
		String path = "product.txt";
		File file = new File(path);
		// 파일이 있다면 조건문 실행
		if (file.exists()) {
			// *** 2. BufferedReader사용해서 file데이터값 읽기 *** //
			BufferedReader inFile = new BufferedReader(new FileReader(file));
			String sLine = null;
			// *** 3. 리스트를 "," 기준으로 잘라서 저장하기 *** //
			while ((sLine = inFile.readLine()) != null) {
				String[] arr = sLine.split(",");
				try {
					String productId = arr[0].trim();
					String productName = arr[1].trim();
					String category = arr[2].trim();
					int price = Integer.parseInt(arr[3].trim());
					int quantity = Integer.parseInt(arr[4].trim());
					ProductMain readproduct = new ProductMain(productId, productName, category, price, quantity);
					productList.add(readproduct);
					productHash.put(productId, readproduct);

				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
			// System.out.println("파일 읽기가 완료되었습니다.");
		}

	}

}