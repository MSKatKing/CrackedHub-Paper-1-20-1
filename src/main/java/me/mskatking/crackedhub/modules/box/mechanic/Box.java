package me.mskatking.crackedhub.modules.box.mechanic;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.box.CrackedHubBox;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class Box {

    private final MultiverseWorld world;
    private final Location loc1, loc2;
    private HashMap<Material, Double> blocks;
    private int resetTime;
    private double resetPercent;
    private int timeSinceReset;
    private final String name;
    private final UUID id;

    public Box(String name, MultiverseWorld world, HashMap<Material, Double> blocks, int resetTime, double resetPercent, Location loc1, Location loc2) {
        this.name = name;
        this.id = UUID.nameUUIDFromBytes(name.getBytes());
        this.world = world;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.blocks = blocks;
        this.resetTime = resetTime;
        this.resetPercent = resetPercent;

        CrackedHubBox.boxes.add(this);
    }

    public Box(UUID id, String name, MultiverseWorld world, HashMap<Material, Double> blocks, int resetTime, double resetPercent, Location loc1, Location loc2) {
        this.name = name;
        this.id = id;
        this.world = world;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.blocks = blocks;
        this.resetTime = resetTime;
        this.resetPercent = resetPercent;

        CrackedHubBox.boxes.add(this);
    }

    public void update() {
        timeSinceReset++;
        if(timeSinceReset >= resetTime) {
            reset();
            return;
        }
        double percentFilled = 0;
        int blocks = 0;
        for(int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc1.getBlockX(), loc2.getBlockX()); x++) {
            for(int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y <= Math.max(loc1.getBlockY(), loc2.getBlockY()); y++) {
                for(int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc1.getBlockZ(), loc2.getBlockZ()); z++) {
                    Location workingLoc = new Location(world.getCBWorld(), x, y, z);
                    blocks++;
                    percentFilled += workingLoc.getBlock().getType().equals(Material.AIR) ? 0 : 1;
                }
            }
        }
        percentFilled /= blocks;
        if(percentFilled <= resetPercent) {
            reset();
        }
    }

    public void reset() {
        for(int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc1.getBlockX(), loc2.getBlockX()); x++) {
            for(int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y <= Math.max(loc1.getBlockY(), loc2.getBlockY()); y++) {
                for(int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc1.getBlockZ(), loc2.getBlockZ()); z++) {
                    Location workingLoc = new Location(world.getCBWorld(), x, y, z);
                }
            }
        }
    }

    public void serialize(FileConfiguration config) {
        config.set(id.toString() + ".name", name);
        config.set(id + ".blocks", Collections.singletonList(blocks));
        config.set(id + ".world", world.getName());
        config.set(id + ".loc1", Collections.singletonList(loc1.serialize()));
        config.set(id + ".loc2", Collections.singletonList(loc1.serialize()));
        config.set(id + ".resettime", resetTime);
        config.set(id + ".resetpercent", resetPercent);
    }

    public Box(FileConfiguration config, String id) throws InvalidConfigurationException {
        this.id = UUID.fromString(id);
        try {
            this.name = (String) config.get(id + ".name");
            this.blocks = (HashMap<Material, Double>) config.getMapList(id + ".blocks").get(0);
            this.world = CrackedHub.core.getMVWorldManager().getMVWorld((String) config.get(id + ".world"));
            this.loc1 = Location.deserialize((Map<String, Object>) config.getMapList(id + ".loc1").get(0));
            this.loc2 = Location.deserialize((Map<String, Object>) config.getMapList(id + ".loc2").get(0));
            this.resetTime = config.getInt(id + ".resettime");
            this.resetPercent = config.getDouble(id + ".resetpercent");
        } catch (Exception e) {
            Console.error("Unable to read box " + id);
            throw new InvalidConfigurationException();
        }
    }
}
