package mekanismopenperipheral.integration;

import openperipheral.api.*;
import dan200.computer.api.IComputerAccess;
import mekanism.api.transmitters.ITransmitter;

public class AdapterITransmitter implements IPeripheralAdapter {
	private static final Class<?> TRANSMITTER_CLAZZ = ITransmitter.class;

	@Override
	public Class<?> getTargetClass() {
		return TRANSMITTER_CLAZZ;
	}

	@LuaMethod(description = "Get the current energy flowing through the network", returnType = LuaType.STRING)
	public String getNetworkFlow(IComputerAccess computer, ITransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkFlow();
	}
}
