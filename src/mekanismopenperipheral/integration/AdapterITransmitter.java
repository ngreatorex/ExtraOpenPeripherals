package mekanismopenperipheral.integration;

import openperipheral.api.*;
import dan200.computer.api.IComputerAccess;
import mekanism.api.transmitters.DynamicNetwork;
import mekanism.api.transmitters.ITransmitter;
import mekanism.api.transmitters.ITransmitterNetwork;
import mekanism.api.energy.IStrictEnergyAcceptor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IElectricalStorage;
import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.compatibility.Compatibility;

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
	
	@LuaMethod(description = "Get the current energy stored in all nodes on the network", returnType = LuaType.STRING)
	public synchronized String getEnergyStoredInNetwork(IComputerAccess computer, ITransmitter<? extends DynamicNetwork> tileEntityTransmitter) {
		float totalStored = 0;
		
		try {
			DynamicNetwork<TileEntity, ? extends DynamicNetwork> network = tileEntityTransmitter.getTransmitterNetwork();
		
			for(TileEntity acceptor : network.possibleAcceptors)
			{
				ForgeDirection side = network.acceptorDirections.get(acceptor);
				if (side == null)
					continue;
				
				if (acceptor instanceof IElectricalStorage) {
					float joulesStored = ((IElectricalStorage) acceptor).getEnergyStored();
					totalStored += joulesStored;
					
					System.err.println(String.format("Found Mekanism IElectricalStorage containing %f J at (%d, %d, %d)", joulesStored, acceptor.xCoord, acceptor.yCoord, acceptor.zCoord));
				}
				else if (acceptor instanceof ic2.api.tile.IEnergyStorage) {
					int euStored = ((ic2.api.tile.IEnergyStorage) acceptor).getStored();
					float joulesStored = euStored * Compatibility.IC2_RATIO;
					totalStored += joulesStored;
					
					System.err.println(String.format("Found IC2 IEnergyStorage containing %d EU (%f J) at (%d, %d, %d)", euStored, joulesStored, acceptor.xCoord, acceptor.yCoord, acceptor.zCoord));
				}
				else if (acceptor instanceof cofh.api.energy.IEnergyStorage) {
					int rfStored = ((cofh.api.energy.IEnergyStorage) acceptor).getEnergyStored();
					float joulesStored = rfStored * Compatibility.TE_RATIO;
					totalStored += joulesStored;
					
					System.err.println(String.format("Found TE IEnergyStorage containing %d RF (%f J) at (%d, %d, %d)", rfStored, joulesStored, acceptor.xCoord, acceptor.yCoord, acceptor.zCoord));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
              
		return ElectricityDisplay.getDisplay(totalStored, ElectricityDisplay.ElectricUnit.JOULES, 1, true);
	}
}
