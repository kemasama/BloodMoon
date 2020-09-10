package kema.raid;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncTask extends BukkitRunnable {

	private Random rand = new Random(System.currentTimeMillis());
	private boolean raiding = false;
	private boolean choose = false;

	public boolean isBloodMoon() {
		return raiding;
	}

	@Override
	public void run() {
		try {
			// Check

			final World main = Bukkit.getWorlds().get(0); // dispose

			// night && !choose
			if (!IWorld.isDay(main.getName()) && !choose) {
				// Is Night !
				choose = true;
				// choosing

				if (rand.nextBoolean()) {
					raiding = true;
					// blood moon
					Bukkit.broadcastMessage("§c§lBlood moon is coming...");

					// blood moon is starting...
					BloodMoonTask task = new BloodMoonTask(main);
					task.runTaskTimer(Main.getInstance(), 20 * 30L, 30L);
				} else {
					System.out.println("Blood moon... false");
				}
			} else if (choose && IWorld.isDay(main.getName())) {
				if (raiding) {
					Bukkit.broadcastMessage("§c§lBlood moon seems to be gone");
				}

				// reset
				raiding = false;
				choose = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
