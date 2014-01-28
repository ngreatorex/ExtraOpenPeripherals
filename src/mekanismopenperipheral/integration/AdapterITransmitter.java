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

	@LuaMethod(description = "Get the number of power conduits on the network", returnType = LuaType.NUMBER)
	public int getTransmitterNetworkSize(IComputerAccess computer, ITransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkSize();
	}

  @LuaMethod(description = "Get the number of power consumers on the network", returnType = LuaType.NUMBER)
	public int getTransmitterNetworkAcceptorSize(IComputerAccess computer, ITransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkAcceptorSize();
	}

	@LuaMethod(description = "Get the current energy requested in the network", returnType = LuaType.STRING)
	public String getTransmitterNetworkNeeded(IComputerAccess computer, ITransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkNeeded();
	}
	
	@LuaMethod(description = "Get the current energy flowing through the network", returnType = LuaType.STRING)
	public String getTransmitterNetworkFlow(IComputerAccess computer, ITransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkFlow();
	}
}
