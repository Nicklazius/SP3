import java.util.Scanner;

public class StartMenu {
    public StartMenu() {
        // mulighed for at modtage bruger input
        Scanner input = new Scanner(System.in);

        // valgmuligheder
        System.out.println("Velkommen til Chill!");
        System.out.println("1. Log ind");
        System.out.println("2. Opret bruger");
        System.out.print("Vælg et tal: "); // brugeren skriver input lige efter her

        // modtager brugeren input og gå sender dem til deres valg
        int chooseOption = input.nextInt();
        input.nextLine();

        // sender brugeren videre baseret på valg
        switch (chooseOption) {
            case 1:
                LogIn.login(); // Kalder login-metoden fra Login-klassen
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
