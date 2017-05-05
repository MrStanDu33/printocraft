package fr.mrstandu33.printocraft;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import fr.mrstandu33.printocraft.SqlConnection;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Location;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.bukkit.BukkitUtil;

@SuppressWarnings("deprecation")
public class printocraft extends JavaPlugin implements Listener
{
	
	public SqlConnection sql;
    public Vector pt1;
    public Vector pt2;
    public CuboidClipboard clipboard;
    private static FileWriter out;
	public org.bukkit.World bukkitWorld;

	private WorldEdit we;
	private EditSession editSession;
	private LocalWorld localWorld;
    
	//___________________________________________________________________________________________________
	public void onEnable()
	{
		sql = new SqlConnection("jdbc:mysql://149.202.36.43:3306/TestPluginImpr3D","printschem","testdb");
		sql.connection();
		System.out.println("Print O' Craft v1.0 ALPHA is now running");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
		    public void run()
		    {
		    	core();
		    }
		}, 20, 1200);
	}
	//___________________________________________________________________________________________________
	@SuppressWarnings("deprecation")
	public void core()
	{
		if (sql.isSchem())
		{
			System.out.println("HEY IL Y A UN SCHEM A FAIRE");
			this.bukkitWorld = Bukkit.getWorld("world");;
			this.we = WorldEdit.getInstance();
			this.localWorld = BukkitUtil.getLocalWorld(bukkitWorld);
	    	try
	    	{	
	            File dir = new File(this.getDataFolder() + "/" + "schematics" + "/");
	            if (!dir.exists())
	                dir.mkdirs();
		        File schematic = new File(this.getDataFolder() + "/" + "schematics" + "/" + "schem.schematic");
		        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	            WorldEdit we = wep.getWorldEdit();
                EditSession editSession = new EditSession(localWorld, 255);
    		    Vector origin = new Vector(sql.coord("X2"),1 ,sql.coord("Z2"));
    		    Vector size = new Vector(sql.coord("X1"),sql.coord("Hauteur") ,sql.coord("Z1"));
                editSession.enableQueue();
	            CuboidClipboard clipboard = new CuboidClipboard(size.subtract(origin).add(new Vector(1, 1, 1)), origin);
	            clipboard.copy(editSession);
	            SchematicFormat.MCEDIT.save(clipboard, schematic);
	            editSession.flushQueue();
	            
	      }
	      catch (IOException | DataException ex)
	      {

		    	System.out.println("Failed to save schematic");
	    	  ex.printStackTrace();
	      }   
		}
		else
		{
			System.out.println("HEY IL N'Y A PAS DE SCHEM A FAIRE");
		}
	}
	//___________________________________________________________________________________________________
	public void onDisable()
	{
		System.out.println("Print O' Craft v1.0 ALPHA is now no more running");
		sql.disconnect();
	}
	//___________________________________________________________________________________________________
    public WorldEditPlugin getWorldEdit()
    {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (p instanceof WorldEditPlugin)
        	return (WorldEditPlugin) p;
        else 
        	return null;
    }
    
    
}
