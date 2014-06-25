package extraopenperipherals.integration;

import openperipheral.api.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import extraopenperipherals.ReflectionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class AdapterTilePowerMonitor implements IPeripheralAdapter {
	private static final Class<?> CLAZZ = ReflectionHelper.getClass("crazypants.enderio.machine.monitor.TilePowerMonitor");

	@Override
	public Class<?> getTargetClass() {
		return CLAZZ;
	}
	
	@LuaMethod(description = "Get the current power stored in all conduits on the power network", returnType = LuaType.NUMBER)
  public float getPowerInConduits(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getPowerInConduits") * 10f;
  }

	@LuaMethod(description = "Get the maximum amount of power that can be stored in all conduits on the power network", returnType = LuaType.NUMBER)
  public float getMaxPowerInConduits(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getMaxPowerInCoduits") * 10f; /* Typo in method name! */
  }

	@LuaMethod(description = "Get the current power stored in the capacitors attached to the power network", returnType = LuaType.NUMBER)
  public float getPowerInCapBanks(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getPowerInCapBanks") * 10f;
  }

	@LuaMethod(description = "Get the maximum amount of power that can be stored in the capacitors attached to the power network", returnType = LuaType.NUMBER)
  public float getMaxPowerInCapBanks(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getMaxPowerInCapBanks") * 10f;
  }

	@LuaMethod(description = "Get the current amount of power stored in all the machines on the power network", returnType = LuaType.NUMBER)
  public float getPowerInMachines(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getPowerInMachines") * 10f;
  }

	@LuaMethod(description = "Get the maximum amount of power that can be stored in all machines on the power network", returnType = LuaType.NUMBER)
  public float getMaxPowerInMachines(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getMaxPowerInMachines") * 10f;
  }

	@LuaMethod(description = "Get the average power received per tick by the power network", returnType = LuaType.NUMBER)
  public float getAvgPowerOut(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getAveMjSent") * 10f;
  }

	@LuaMethod(description = "Get the average power received per tick by the power network", returnType = LuaType.NUMBER)
  public float getAvgPowerIn(IComputerAccess computer, TileEntity monitor) {
    return ReflectionHelper.<Float>call(monitor, "getAveMjRecieved") * 10f; /* Typo in method name! */
  }
}
