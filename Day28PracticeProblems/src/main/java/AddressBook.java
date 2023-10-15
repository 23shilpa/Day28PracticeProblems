import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public <CSVWriter> void saveToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            for (Contact contact : contacts) {
                writer.wait(new String[] {
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

        Contact contact1 = new Contact("John", "Doe", "123 Main St", "New York", "NY", "10001", "555-123-4567", "john.doe@example.com");
        Contact contact2 = new Contact("Jane", "Smith", "456 Elm St", "Los Angeles", "CA", "90001", "555-987-6543", "jane.smith@example.com");

        addressBook.addContact(contact1);
        addressBook.addContact(contact2);

        addressBook.saveToCSV();
        addressBook.loadFromCSV();

        addressBook.saveToJSON();
        addressBook.loadFromJSON();

        addressBook.listContacts();
    }
}

class Contact {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    public Contact(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName +
                "\nAddress: " + address +
                "\nCity: " + city +
                "\nState: " + state +
                "\nZip: " + zip +
                "\nPhone Number: " + phoneNumber +
                "\nEmail: " + email + "\n";
    }

    public String getFirstName() {
        return null;
    }
}
