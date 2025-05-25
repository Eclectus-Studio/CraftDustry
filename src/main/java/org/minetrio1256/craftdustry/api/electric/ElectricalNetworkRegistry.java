package org.minetrio1256.craftdustry.api.electric;

import java.util.HashMap;
import java.util.Map;

public class ElectricalNetworkRegistry {
    private static Map<Integer, ElectricalNetwork> networks = new HashMap<>();
    private static int nextId = 1;

    public void loadFromSerializer() {
        ElectricalNetworkSerializer.loadFromSerializer();
        networks.clear();
        networks = ElectricalNetworkSerializer.getNetworks();
        int totalID = 1;
        for (int i : networks.keySet()){
            totalID++;
        }
        nextId = totalID;
    }

    public void saveToSerializer() {
        ElectricalNetworkSerializer.setNetworks(networks);
        ElectricalNetworkSerializer.saveToSerializer();
    }

    public ElectricalNetwork createNetwork() {
        ElectricalNetwork net = new ElectricalNetwork(nextId++);
        networks.put(net.getId(), net);
        return net;
    }

    public ElectricalNetwork getNetwork(int id) {
        return networks.get(id);
    }

    public void removeNetwork(int id) {
        networks.remove(id);
    }

    public Map<Integer, ElectricalNetwork> getNetworks() {
        return networks;
    }
}
