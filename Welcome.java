import java.util.Scanner;
public class Welcome {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Existing code
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your gender (M/F):");
        char gender = sc.next().charAt(0);
        System.out.println("Enter your age:");
        int age = sc.nextInt();
        System.out.println("Enter your mobile number:");
        long mobileNo = sc.nextLong();
        System.out.println("Enter your GPA:");
        double gpa = sc.nextDouble();

        // Output existing information
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Age: " + age);
        System.out.println("Mobile Number: " + mobileNo);
        System.out.println("GPA: " + gpa);

        // New code for calculating coins
        System.out.println("Enter an amount in dollars (e.g., 4.55):");
        double amount = sc.nextDouble();

        int totalCents = (int) Math.round(amount * 100);
        int quarters = totalCents / 25;
        totalCents %= 25;
        int dimes = totalCents / 10;
        totalCents %= 10;
        int nickels = totalCents / 5;

        System.out.println("You need " + quarters + " quarters");
        if (dimes > 0) {
            System.out.println(" and " + dimes + " dimes");
        }
        if (nickels > 0) {
            System.out.println(" and " + nickels + " nickels");
        }
        System.out.println(" to make $" + amount);

        sc.close();
    }
}
