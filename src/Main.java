import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== QUAN LY CUA HANG BAN LE =====");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Quan ly khach hang");
            System.out.println("3. Quan ly don hang");
            System.out.println("4. Quan ly nhap hang");
            System.out.println("5. Bao cao thong ke");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            String input = scanner.nextLine().trim();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so tu 0 den 5.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Da thoat chuong trinh.");
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    System.out.println("Chuc nang nay se duoc lam o buoc sau.");
                    break;
                default:
                    System.out.println("Vui long chon tu 0 den 5.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
