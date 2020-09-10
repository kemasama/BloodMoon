package kema.raid;

import org.bukkit.Bukkit;

public class IWorld {
	public static boolean isDay(String worldname) {

		long time = Bukkit.getWorld(worldname).getTime();
		return time < 12300 || time > 23850;

	}
}
