package kema.raid;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class BloodMoonTask extends BukkitRunnable {

	public static int Limit = 120;

	public BloodMoonTask(World w) {
		this.world = w;
	}

	private boolean first = false;
	private World world;
	private Random rand = new Random(System.currentTimeMillis() + 10L);

	@Override
	public void run() {
		try {

			if (first) {
				first = true;
				for (Player p : world.getPlayers()) {
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
				}
			}

			if (!Main.getInstance().getTask().isBloodMoon()) {
				System.out.println("Blood moon is done.");
				cancel();
				return;
			}

			// Checking spawn point
			Location loc = world.getSpawnLocation();

			if (world.getPlayers().size() == 0) {
				return;
			}

			if (world.getEntitiesByClass(Zombie.class).size() > Limit) {
				return;
			}

			// spread
			double health = 80.0d;
			int len = 10 + rand.nextInt(10);
			for (int i = 0; i < len; i++) {
				int xx = (rand.nextBoolean() == true) ? 1 : -1;
				int xz = (rand.nextBoolean() == true) ? 1 : -1;
				int x = (int) (rand.nextDouble() * rand.nextInt(30) * xx);
				int z = (int) (rand.nextDouble() * rand.nextInt(30) * xz);

				Location l = loc.getWorld().getHighestBlockAt(x, z).getLocation();

				//System.out.println("X: " + l.getBlockX() + " Y: " + l.getBlockY() + " Z: " + l.getBlockZ());

				for (int ii = 0; ii < 3; ii++) {
					Zombie zomb = (Zombie) l.getWorld().spawnEntity(
							l,
							EntityType.ZOMBIE);
					zomb.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
					zomb.setHealth(health);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Location getMostNear(Location from) {
		Location to = null;
		double dis = 10000;

		for (Player p : from.getWorld().getPlayers()) {
			double d = p.getLocation().distanceSquared(from);
			if (dis > d) {
				dis = d;
				to = p.getLocation();
			}
		}

		return to;
	}
}
