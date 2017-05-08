package fr.mrstandu33.printocraft;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.bukkit.BukkitUtil;

@SuppressWarnings("deprecation")
public class printocraft extends JavaPlugin implements Listener
{
	
	public SqlConnection sql;
    public Vector pt1;
    public Vector pt2;
    public CuboidClipboard clipboard;
    public org.bukkit.World bukkitWorld;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

	private LocalWorld localWorld;

	//___________________________________________________________________________________________________
	public void onEnable()
	{
		System.out.println("_______________________________________________________________________________________________________________________________");
		System.out.println("#                                                                                                                             #");
		System.out.println("# " + ANSI_RED +  "88888888ba             88 " + ANSI_CYAN +  "                        ,ad8888ba,     d8' " + ANSI_GREEN +  "     ,ad8888ba,                           ad88        " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88      \"8b            \"\"              ,d " + ANSI_CYAN +  "       d8\"'    `\"8b   d8' " + ANSI_GREEN +  "     d8\"'    `\"8b                         d8\"     ,d   " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88      ,8P                            88 " + ANSI_CYAN +  "      d8'        `8b \"\" " + ANSI_GREEN +  "      d8'                                   88      88   " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88aaaaaa8P' 8b,dPPYba, 88 8b,dPPYba, MM88MMM " + ANSI_CYAN +  "   88          88 " + ANSI_GREEN +  "         88            8b,dPPYba, ,adPPYYba, MM88MMM MM88MMM" + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88\"\"\"\"\"\"'   88P'   \"Y8 88 88P'   `\"8a  88  " + ANSI_CYAN +  "     88          88 " + ANSI_GREEN +  "         88            88P'   \"Y8 \"\"     `Y8   88      88   " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88          88         88 88       88  88 " + ANSI_CYAN +  "      Y8,        ,8P " + ANSI_GREEN +  "         Y8,           88         ,adPPPPP88   88      88   " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88          88         88 88       88  88, " + ANSI_CYAN +  "      Y8a.    .a8P " + ANSI_GREEN +  "           Y8a.    .a8P 88         88,    ,88   88      88,  " + ANSI_RESET + " #");
		System.out.println("# " + ANSI_RED +  "88          88         88 88       88  \"Y888 " + ANSI_CYAN +  "     `\"Y8888Y\"' " + ANSI_GREEN +  "             `\"Y8888Y\"'  88         `\"8bbdP\"Y8   88      \"Y888" + ANSI_RESET + " #");
		System.out.println("#_____________________________________________________________________________________________________________________________#");
		sql = new SqlConnection("jdbc:mysql://149.202.36.43:3306/TestPluginImpr3D","printschem","testdb");
		sql.connection();
		System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN + " O'" + ANSI_GREEN + " Craft" + ANSI_RESET + "] v0.2 ALPHA is now running");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
		    public void run()
		    {
		    	core();
		    }
		}, 20, 1200);

		getCommand("poc").setExecutor(new Commands());
	}
	//___________________________________________________________________________________________________
	public void core()
	{
		if (sql.isSchem())
		{
			System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "] A schematic have to be created, starting initialisation and creation !");
			this.bukkitWorld = Bukkit.getWorld("world");
            WorldEdit.getInstance();
			this.localWorld = BukkitUtil.getLocalWorld(bukkitWorld);
	    	try
	    	{	
	            File dir = new File(this.getDataFolder() + "/" + "schematics" + "/");
	            if (!dir.exists())
	                dir.mkdirs();
		        File schematic = new File(this.getDataFolder() + "/" + "schematics" + "/" + sql.IDquery() + ".schematic");
		        EditSession editSession = new EditSession(localWorld, 255);
    		    Vector origin = new Vector(sql.coord("X2"),1 ,sql.coord("Z2"));
    		    Vector size = new Vector(sql.coord("X1"),sql.coord("Hauteur") ,sql.coord("Z1"));
                editSession.enableQueue();
	            CuboidClipboard clipboard = new CuboidClipboard(size.subtract(origin).add(new Vector(1, 1, 1)), origin);
	            clipboard.copy(editSession);
	            SchematicFormat.MCEDIT.save(clipboard, schematic);
	            editSession.flushQueue();
	            if (schematic.exists())
	            {
			    	System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "]Schematic n◦" + sql.IDquery() + " successfully created !");
	            }
	      }
	      catch (IOException | DataException ex)
	      {

		    	System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "]Failed to save schematic");
	    	  ex.printStackTrace();
	      }   
		}
		else
		{
			System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "]There's nothing to do for the moment, checking in a minute");
		}
	}
	//___________________________________________________________________________________________________
	public void onDisable()
	{
		sql.disconnect();
		System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "]Print O' Craft v0.2 ALPHA is now no more connected to the database");
		System.out.println("[" + ANSI_RED +  "Print" + ANSI_CYAN +  " O'" + ANSI_GREEN +  " Craft" + ANSI_RESET + "]Print O' Craft v0.2 ALPHA is now no more running");
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
