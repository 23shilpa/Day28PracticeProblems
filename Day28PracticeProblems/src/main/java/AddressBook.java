import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    private List<Contact> contacts;
    private static final String CSV_FILE = "addressbook.csv";
    private static final String JSON_FILE = "addressbook.json";

    public AddressBook() {
        contacts = new ArrayList<>();
        loadFromCSV();
        // You can also load from JSON here.
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public void listContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void saveToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            for (Contact contact : contacts) {
                writer.writeNext(new String[] {
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getAddress(),
                        contact.getCity(),
                        contact.getState(),
                        contact.getZip(),
                        contact.getPhoneNumber(),
                        contact.getEmail()
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromCSV() {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Contact contact = new Contact(
                        line[0], line[1], line[2], line[3],
                        line[4], line[5], line[6], line[7]
                );
                contacts.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Contact> loadedContacts = objectMapper.readValue(new File(JSON_FILE), objectMapper.getTypeFactory().constructCollectionType(List.class, Contact.class));
            contacts.addAll(loadedContacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter contact details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("ZIP: ");
        String zip = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Contact newContact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        addressBook.addContact(newContact);

        addressBook.saveToCSV();
        addressBook.loadFromCSV();

        addressBook.saveToJSON();
        addressBook.loadFromJSON();

        addressBook.listContacts();
    }
}
