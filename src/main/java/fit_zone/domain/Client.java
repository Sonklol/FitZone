package fit_zone.domain;

import java.util.Objects;

public class Client {
    private int idClient;
    private String firstName;
    private String lastName1;
    private String lastName2;
    private int membershipNumber;

    public Client(){}

    public Client(int number, String numberType){
        numberType = numberType.toLowerCase();
        if (numberType.equals("idclient")) {
            this.idClient = number;
        } else {
            this.membershipNumber = number;
        }
    }

    public Client(String firstName, String lastName1, String lastName2, int membershipNumber){
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.membershipNumber = membershipNumber;
    }

    public Client(int idClient, String firstName, String lastName1, String lastName2, int membershipNumber){
        this(firstName, lastName1, lastName2, membershipNumber);
        this.idClient = idClient;
    }

    public int getIdClient() {return idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName1() {return lastName1;}
    public void setLastName1(String lastName1) {this.lastName1 = lastName1;}
    public String getLastName2() {return lastName2;}
    public void setLastName2(String lastName2) {this.lastName2 = lastName2;}
    public int getMembershipNumber() {return membershipNumber;}
    public void setMembershipNumber(int membershipNumber) {this.membershipNumber = membershipNumber;}

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", firstName='" + firstName + '\'' +
                ", lastName1='" + lastName1 + '\'' +
                ", lastName2='" + lastName2 + '\'' +
                ", membershipNumber=" + membershipNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient == client.idClient && membershipNumber == client.membershipNumber && Objects.equals(firstName, client.firstName) && Objects.equals(lastName1, client.lastName1) && Objects.equals(lastName2, client.lastName2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, firstName, lastName1, lastName2, membershipNumber);
    }
}
