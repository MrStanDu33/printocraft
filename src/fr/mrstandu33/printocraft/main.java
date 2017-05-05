package fr.mrstandu33.printocraft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.Rbmc.Pintocraft.main.ITOA;
import fr.mrstandu33.printocraft.SqlConnection;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class utils extends JavaPlugin
{
	Plugin plugin;
	 
    public void StructureAPI(Plugin pl)
    {
    	this.plugin = pl;
    }
	public SqlConnection sql;
	public static int ID;
	
    public void onEnable()
    {
        sql = new SqlConnection("jdbc:mysql://","149.202.36.43:3306","TestPluginImpr3D","printschem","schempass");
        sql.connection();
    }
   
    public void onDisable()
    {
        sql.disconnect();
    }
 
    public static class ITOA
    {
    	public static String convert(int value, int base) 
    	{
    		boolean negative = false;
    		String s = "";
    		if (value == 0)
    			return "0";
    		negative = (value < 0);
    		if (negative)
    			value = -1 * value;
    		while (value != 0)
    		{
    			s = (value % base) + s;
    			value = value / base;
    		}
    		if (negative)
    			s = "-" + s;
    		return s;
    	}
    }
 
    public int[][][] getStructure(Block block, Block block2)
    {
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        int[][][] blocks = new int[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
 
        for(int x = minX; x <= maxX; x++)
        {
            for(int y = minY; y <= 255; y++)
            {
            	for(int z = minZ; z <= maxZ; z++)
                {
                    Block b = block.getWorld().getBlockAt(x, y, z);
                    blocks[x-minX][y-minY][z-minZ] = b.getTypeId();
                }
            }
        }
        return blocks;
    }    
    
    public void save(int ID, int[][][] b)
    {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;

        File f = new File(((JavaPlugin) plugin).getDataFolder() + "/schematics/"+ ITOA.convert(ID, 10) + ".schem");
        File dir = new File(((JavaPlugin) plugin).getDataFolder() + "/schematics");
        
        try
        {
                dir.mkdirs();
                f.createNewFile();
        }
        catch (IOException e1)
        {
                e1.printStackTrace();
        }
        try
        {
               fout = new FileOutputStream(f);
               oos = new ObjectOutputStream(fout);
               oos.writeObject(b);
        }
        catch (Exception e)
        {
               e.printStackTrace();
        }
        finally
        {
               if(oos  != null)
               {
                   try
                   {
                        oos.close();
                   }
                   catch (IOException e)
                   {
                        e.printStackTrace();
                   }
               }
        }
    }
}

public class main extends JavaPlugin
{
	public void main()
	{
		if (utils.ID == 0)
		{
			utils.save();
		}
	}

}