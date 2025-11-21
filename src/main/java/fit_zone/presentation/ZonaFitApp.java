package fit_zone.presentation;

import fit_zone.data.ClientDAO;
import fit_zone.data.IClientDAO;
import fit_zone.domain.Client;

import java.util.List;
import java.util.Scanner;

public class ZonaFitApp {
    static void main(String[] args) {MainApp();}

    public static void MainApp() {
        boolean menuExit = false;
        IClientDAO clientDAO = new ClientDAO();

        Scanner sc = new Scanner(System.in);

        while (!menuExit) {
            try {
                int option = showMenu(sc);
                menuExit = execOptions(option, sc, clientDAO);
            } catch (Exception e) {
                System.out.println("Error showing the Menu: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    private static boolean execOptions(int option, Scanner sc, IClientDAO clientDAO) {
        boolean exitMenuExecOptions = false;
        switch (option) {
            case 1 -> showClientsList(clientDAO);
            case 2 -> searchClient(sc, clientDAO);
            case 3 -> addNewClient(sc, clientDAO);
            case 4 -> modifyClient(sc, clientDAO);
            case 5 -> removeClient(sc, clientDAO);
            case 6 -> exitMenuExecOptions = true;
            default -> System.out.println("Invalid option: " + option);
        }
        return exitMenuExecOptions;
    }

    private static int showMenu(Scanner sc) {
        System.out.print("""
        -- MENU --
         1. LIST OF CLIENTS
         2. SEARCH A CLIENT
         3. ADD A NEW CLIENT
         4. MODIFY A CLIENT
         5. DELETE A CLIENT
         6. EXIT
        """);
        System.out.print("Select an option: ");
        // Read and return the selected option
        return Integer.parseInt(sc.nextLine());
    }

    private static void showClientsList(IClientDAO clientDAO) {
        List<Client> clients = clientDAO.listClients();
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    private static void addNewClient(Scanner sc, IClientDAO clientDAO) {
        System.out.print("Enter the name of the new client: ");
        String firstName = sc.nextLine();
        System.out.print("Enter First last name: ");
        String lastName1 = sc.nextLine();
        System.out.print("Enter Second last name: ");
        String lastName2 = sc.nextLine();
        System.out.print("Enter the membership number: ");
        int membershipNumber = Integer.parseInt(sc.nextLine());
        System.out.println();

        Client client = new Client(firstName, lastName1, lastName2, membershipNumber);
        if (clientDAO.addClient(client)){
            System.out.println("Client added successfully!");
        } else {
            System.out.println("Failed to insert new client!");
        }
    }

    private static String typeOfIdentifier(Scanner sc){
        System.out.print("What type of identifier do you want to search for? [idClient, membershipNumber]: ");
        return sc.nextLine().toLowerCase();
    }

    private static void searchClient(Scanner sc, IClientDAO clientDAO) {
        String typeOfIdentifier = typeOfIdentifier(sc);
        if (typeOfIdentifier.equals("idclient")) {
            System.out.print("Enter Client ID: ");
            int idClient = Integer.parseInt(sc.nextLine());
            Client client1 = new Client(idClient, typeOfIdentifier);
            if (clientDAO.searchClientByClientId(client1)){
                System.out.println("Client found successfully!\n" + client1);
            } else  {
                System.out.println("Client not found!");
            }
        } else if (typeOfIdentifier.equals("membershipnumber")) {
            System.out.print("Enter Membership Number: ");
            int membershipNumber = Integer.parseInt(sc.nextLine());
            Client client2 = new Client(membershipNumber, typeOfIdentifier);
            if (clientDAO.searchClientByMembershipNumber(client2)){
                System.out.println("Client found successfully!\n" + client2);
            } else  {
                System.out.println("Client not found!");
            }
        } else {
            System.out.println("Invalid input!");
        }

    }

    private static void modifyClient(Scanner sc, IClientDAO clientDAO) {
        // IDCLIENT CAN NOT BE MODIFY
        // MEMBERSHIP NUMBER CAN NOT BE MODIFY BECAUSE THIS IS A IMPORTANT UNIQUE IDENTIFIER OF THE CLIENT AND IT DOES NOT HAVE AUTOINCREMENT LIKE THE IDCLIENT.
        // YOU MAY DELETE THE USER AND CREATE A NEW USER WITH NEW MEMBERSHIP NUMBER
        System.out.print("Enter the membership number: ");
        int membershipNumber = Integer.parseInt(sc.nextLine());
        if (clientDAO.searchClientByMembershipNumber(new Client(membershipNumber, "membershipNumber"))) {
            System.out.print("Name: ");
            String firstName = sc.nextLine();
            System.out.print("First last name: ");
            String lastName1 = sc.nextLine();
            System.out.print("Second last name: ");
            String lastName2 = sc.nextLine();
            System.out.println();

            Client client = new Client(firstName, lastName1, lastName2, membershipNumber);
            if (clientDAO.updateClient(client)){
                System.out.println("Client modified successfully!");
            } else  {
                System.out.println("Failed to modify the client!");
            }
        } else {
            System.out.println("The membership does not exist!");
        }
    }

    private static void removeClient(Scanner sc, IClientDAO clientDAO) {
        String typeOfIdentifier = typeOfIdentifier(sc);
        if (typeOfIdentifier.equals("idclient")) {
            System.out.print("Enter Client ID: ");
            int idClient = Integer.parseInt(sc.nextLine());

            Client client = new Client(idClient, typeOfIdentifier);
            if (clientDAO.searchClientByClientId(client)) {
                if (clientDAO.deleteClient(client)) {
                    System.out.println("Client deleted successfully!");
                } else {
                    System.out.println("Failed to delete the client!");
                }
            } else {
                System.out.println("The membership does not exist!");
            }
        } else if (typeOfIdentifier.equals("membershipnumber")) {
            System.out.print("Enter the membership number: ");
            int membershipNumber = Integer.parseInt(sc.nextLine());
            Client client = new Client(membershipNumber, typeOfIdentifier);
            if (clientDAO.searchClientByMembershipNumber(client)) {
                if (clientDAO.deleteClient(client)) {
                    System.out.println("Client deleted successfully!");
                } else {
                    System.out.println("Failed to delete the client!");
                }
            } else {
                System.out.println("The membership does not exist!");
            }
        } else  {
            System.out.println("Invalid input!");
        }
    }
}
