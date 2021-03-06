package electrodynamics.module;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import electrodynamics.block.BlockRedWire;
import electrodynamics.block.EDBlocks;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.core.Strings;
import electrodynamics.tileentity.TileEntityRedWire;

public class EDModuleLogic extends EDModule {

	@Override
	public void preInit() {
		/* BLOCK */
		EDBlocks.blockRedWire = new BlockRedWire(BlockIDs.BLOCK_RED_WIRE_ID).setUnlocalizedName(Strings.BLOCK_RED_WIRE_NAME);
		GameRegistry.registerBlock(EDBlocks.blockRedWire, Strings.BLOCK_RED_WIRE_NAME);
		LanguageRegistry.addName(EDBlocks.blockRedWire, "Red Wire");
		
		/* ITEM */
	}

	@Override
	public void init() {
		GameRegistry.registerTileEntity(TileEntityRedWire.class, Strings.BLOCK_RED_WIRE_NAME);
	}

	@Override
	public void postInit() {
		
	}

}
