package fit_zone.data;

import fit_zone.domain.Client;

import java.util.List;

public interface IClientDAO {
    List<Client> listClients();
    boolean searchClientByMembershipNumber(Client client);
    boolean searchClientByClientId(Client client);
    boolean addClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(Client client);
}
