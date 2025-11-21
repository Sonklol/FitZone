package fit_zone.data;

import fit_zone.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static fit_zone.connection.SQLConnection.getConnection;

public class ClientDAO implements IClientDAO {
    private static final String SELECT_CLIENTS = "SELECT idClient, first_name, last_name1, last_name2, membership_number FROM clients ORDER BY idClient";
    private static final String SELECT_SEARCH_MEMBERSHIP_NUMBER = "SELECT idClient, first_name, last_name1, last_name2, membership_number FROM clients WHERE membership_number = ?";
    private static final String SELECT_SEARCH_IDCLIENT = "SELECT first_name, last_name1, last_name2, membership_number FROM clients WHERE idClient = ?";
    private static final String INSERT_CLIENT = "INSERT INTO clients (first_name, last_name1, last_name2, membership_number) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE clients SET first_name = ?, last_name1 = ?, last_name2 = ?, membership_number = ? WHERE membership_number = ?";
    private static final String DELETE_CLIENT = "DELETE FROM clients WHERE membership_number = ?";

    @Override
    public List<Client> listClients() {
        List<Client> clients = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try (Connection con = getConnection()) {
            try {
                ps = con.prepareStatement(SELECT_CLIENTS);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Client client = new Client();
                    client.setIdClient(rs.getInt("idClient"));
                    client.setFirstName(rs.getString("first_name"));
                    client.setLastName1(rs.getString("last_name1"));
                    client.setLastName2(rs.getString("last_name2"));
                    client.setMembershipNumber(rs.getInt("membership_number"));
                    clients.add(client);
                }
            } catch (Exception e) {
                System.out.println("Error listing clients: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return clients;
    }

    @Override
    public boolean searchClientByMembershipNumber(Client client) {
        PreparedStatement ps;
        ResultSet rs;

        try (Connection con = getConnection()) {
            try {
                ps = con.prepareStatement(SELECT_SEARCH_MEMBERSHIP_NUMBER);
                ps.setInt(1, client.getMembershipNumber());
                rs = ps.executeQuery();
                if (rs.next()) {
                    client.setIdClient(rs.getInt("idClient"));
                    client.setFirstName(rs.getString("first_name"));
                    client.setLastName1(rs.getString("last_name1"));
                    client.setLastName2(rs.getString("last_name2"));
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error searching client by id: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean searchClientByClientId(Client client) {
        PreparedStatement ps;
        ResultSet rs;

        try (Connection con = getConnection()) {
            try {
                ps = con.prepareStatement(SELECT_SEARCH_IDCLIENT);
                ps.setInt(1, client.getIdClient());
                rs = ps.executeQuery();
                if (rs.next()) {
                    client.setFirstName(rs.getString("first_name"));
                    client.setLastName1(rs.getString("last_name1"));
                    client.setLastName2(rs.getString("last_name2"));
                    client.setMembershipNumber(rs.getInt("membership_number"));
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error searching client by id: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean addClient(Client client) {
        PreparedStatement ps;

        // Avoid duplicate clients

        try (Connection con = getConnection()) {
            try {
                if (searchClientByMembershipNumber(client)) {
                    return false;
                }
                ps = con.prepareStatement(INSERT_CLIENT);
                ps.setString(1, client.getFirstName());
                ps.setString(2, client.getLastName1());
                ps.setString(3, client.getLastName2());
                ps.setInt(4, client.getMembershipNumber());
                ps.execute();
                return true;
            } catch (Exception e) {
                System.out.println("Error adding client: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateClient(Client client) {
        PreparedStatement ps;

        try (Connection con = getConnection()) {
            try {
                ps = con.prepareStatement(UPDATE_CLIENT);
                ps.setString(1, client.getFirstName());
                ps.setString(2, client.getLastName1());
                ps.setString(3, client.getLastName2());
                ps.setInt(4, client.getMembershipNumber());
                ps.setInt(5, client.getMembershipNumber());
                ps.execute();
                return true;
            } catch (Exception e) {
                System.out.println("Error updating client: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteClient(Client client) {
        PreparedStatement ps;

        try (Connection con = getConnection()) {
            try {
                ps = con.prepareStatement(DELETE_CLIENT);
                ps.setInt(1, client.getMembershipNumber());
                ps.execute();
                return true;
            } catch (Exception e) {
                System.out.println("Error deleting client: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

            return false;
    }
}