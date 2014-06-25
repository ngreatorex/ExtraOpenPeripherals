package mekanismopenperipheral;

import openperipheral.api.*;
import mekanismopenperipheral.integration.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(serverSideRequired = true, clientSideRequired = false)
public class MekanismOpenPeripheral {

	@Instance(value = ModInfo.ID)
	public static MekanismOpenPeripheral instance;

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		if (Loader.isModLoaded("Mekanism")) {
			OpenPeripheralAPI.register(new AdapterIGridTransmitter());
		}
		if (Loader.isModLoaded("EnderIO")) {
			OpenPeripheralAPI.register(new AdapterTilePowerMonitor());
		}
	}
}
