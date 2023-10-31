package me.mskatking.crackedhub.modules.box;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.box.mechanic.Box;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CrackedHubBox {

    public boolean enabled = true;
    public final MultiverseWorld boxOverworld, boxNether, boxEnd;
    public final FileConfiguration config = new YamlConfiguration();
    public final File f;
    public static final ArrayList<Box> boxes = new ArrayList<>();

    public CrackedHubBox() {
        File path = new File(String.valueOf(CrackedHub.getPlugin(CrackedHub.class).getDataFolder()));
        if(!path.exists()) {
            path.mkdirs();
        }
        this.f = new File(path, "boxes.yml");
        try {
            if(!f.exists()) f.createNewFile();
            config.load(f);
        } catch (InvalidConfigurationException e) {
            Console.error("Box YAML is not valid!");
        } catch (Exception e) {
            Console.error(e.getMessage());
        }

        boxOverworld = CrackedHub.core.getMVWorldManager().getMVWorld("box_overworld");
        boxNether = CrackedHub.core.getMVWorldManager().getMVWorld("box_nether");
        boxEnd = CrackedHub.core.getMVWorldManager().getMVWorld("box_end");
        HashMap<Material, Double> a = new HashMap<>();
        a.put(Material.STONE, 50.0);
        a.put(Material.DIRT, 50.0);
        boxes.add(new Box("test", boxOverworld, a, 10, 0.0, new Location(boxOverworld.getCBWorld(), 10, 0, 10), new Location(boxOverworld.getCBWorld(), -10, 0, -10)));
        boxes.get(0).update();
    }
}
