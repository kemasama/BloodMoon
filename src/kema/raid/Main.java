package kema.raid;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main Instance;
	private SyncTask task;

	public static Main getInstance() {
		return Instance;
	}

	public SyncTask getTask() {
		return task;
	}

	@Override
	public void onDisable() {
		super.onDisable();
		Instance = null; // Dispose
		task = null; // Dispose
	}

	@Override
	public void onEnable() {
		super.onEnable();
		Instance = this; // allocate self

		// sync task
		task = new SyncTask();
		task.runTaskTimer(this, 20L, 20L * 10);
	}

}
