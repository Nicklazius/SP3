import java.util.Scanner;

public class StartMenu {
    public static void main(String[] args) {
        // mulighed for at modtage bruger input
        Scanner input = new Scanner(System.in);

        // valgmuligheder
        System.out.println("Velkommen til Chill!");
        System.out.println("1. Log ind");
        System.out.println("2. Opret bruger");
        System.out.print("Vælg et tal: "); // brugeren skriver input lige efter her

        // vi modtager brugeren input og gå sender dem til deres valg
        int chooseOption = input.nextInt();
        input.nextLine();

        // Vi bruger en switch til at sende brugeren videre baseret på valg
        switch (chooseOption) {
            case 1:
                // Login.login(); // Kalder login-metoden fra Login-klassen
                break;
            case 2:
                SignUp.signUp(); // Kalder signUp-metoden fra SignUp-klassen
                break;
            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
        }

        input.close(); // Vi lukker scanneren for at frigive ressourcer
    }
}
