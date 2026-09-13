import java.util.Scanner;

public class bai4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">> Kiem tra so chan le <<");
        System.out.print("Vui long nhap so can kiem tra: ");
        int so = scanner.nextInt();
        if (so % 2 == 0) {
            System.out.println("So " + so + " la so chan ");
        } else {
            System.out.println("So " + so + " la so le ");

        }
        scanner.close();

    }

}
