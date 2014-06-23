package mekanismopenperipheral.integration;

import openperipheral.api.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import mekanism.api.transmitters.DynamicNetwork;
import mekanism.api.transmitters.IGridTransmitter;
import mekanism.api.transmitters.ITransmitterNetwork;
import mekanism.api.energy.IStrictEnergyAcceptor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class AdapterIGridTransmitter implements IPeripheralAdapter {
	private static final Class<?> TRANSMITTER_CLAZZ = IGridTransmitter.class;

	@Override
	public Class<?> getTargetClass() {
		return TRANSMITTER_CLAZZ;
	}

	@LuaMethod(description = "Get the number of power conduits on the network", returnType = LuaType.NUMBER)
	public int getTransmitterNetworkSize(IComputerAccess computer, IGridTransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkSize();
	}

  @LuaMethod(description = "Get the number of power consumers on the network", returnType = LuaType.NUMBER)
	public int getTransmitterNetworkAcceptorSize(IComputerAccess computer, IGridTransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkAcceptorSize();
	}

	@LuaMethod(description = "Get the current energy requested in the network", returnType = LuaType.STRING)
	public String getTransmitterNetworkNeeded(IComputerAccess computer, IGridTransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkNeeded();
	}
	
	@LuaMethod(description = "Get the current energy flowing through the network", returnType = LuaType.STRING)
	public String getTransmitterNetworkFlow(IComputerAccess computer, IGridTransmitter tileEntityTransmitter) {
		return tileEntityTransmitter.getTransmitterNetworkFlow();
	}
	
	@LuaMethod(description = "Get the current energy stored in all nodes on the network", returnType = LuaType.NUMBER)
	public synchronized float getEnergyStoredInNetwork(IComputerAccess computer, IGridTransmitter<? extends DynamicNetwork<TileEntity, ?>> tileEntityTransmitter) {
		float totalStored = 0;
		
		try {
			DynamicNetwork<TileEntity, ?> network = tileEntityTransmitter.getTransmitterNetwork();
		
			for(TileEntity acceptor : network.possibleAcceptors)
			{
				ForgeDirection side = network.acceptorDirections.get(acceptor);
				if (side == null)
					continue;
				
				if (acceptor instanceof cofh.api.energy.IEnergyStorage) {
					int rfStored = ((cofh.api.energy.IEnergyStorage) acceptor).getEnergyStored();
					totalStored += rfStored;
					
					//System.err.println(String.format("Found TE IEnergyStorage containing %d RF at (%d, %d, %d)", rfStored, acceptor.xCoord, acceptor.yCoord, acceptor.zCoord));
				} else if (acceptor instanceof cofh.api.energy.IEnergyHandler) {
					int rfStored = ((cofh.api.energy.IEnergyHandler) acceptor).getEnergyStored(side);
					totalStored += rfStored;
					
					//System.err.println(String.format("Found TE IEnergyHandler containing %d RF at (%d, %d, %d)", rfStored, acceptor.xCoord, acceptor.yCoord, acceptor.zCoord));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
              
		return totalStored;
	}
}
