package electrodynamics.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import electrodynamics.block.EDBlocks;
import electrodynamics.client.fx.FXLightningBolt;
import electrodynamics.client.render.block.RenderBlockOre;
import electrodynamics.client.render.block.RenderBlockRedWire;
import electrodynamics.client.render.entity.RenderBeam;
import electrodynamics.client.render.item.RenderItemMachine;
import electrodynamics.client.render.item.RenderItemTable;
import electrodynamics.client.render.tileentity.RenderBasicSieve;
import electrodynamics.client.render.tileentity.RenderSinteringOven;
import electrodynamics.client.render.tileentity.RenderTable;
import electrodynamics.control.KeyBindingHelper;
import electrodynamics.control.KeybindingHandler;
import electrodynamics.core.handler.SoundHandler;
import electrodynamics.entity.EntityBeam;
import electrodynamics.lib.client.FXType;
import electrodynamics.tileentity.TileEntityBasicSieve;
import electrodynamics.tileentity.TileEntitySinteringOven;
import electrodynamics.tileentity.TileEntityTable;

public class ClientProxy extends CommonProxy {

	public void preInitClient() {
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

	public void initClient() {
		KeyBindingRegistry.registerKeyBinding(new KeybindingHandler());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySinteringOven.class, new RenderSinteringOven());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTable.class, new RenderTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicSieve.class, new RenderBasicSieve());

		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockTable.blockID, new RenderItemTable());
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockMachine.blockID, new RenderItemMachine());

		RenderingRegistry.registerBlockHandler(new RenderBlockRedWire());
		RenderingRegistry.registerBlockHandler(new RenderBlockOre());

		RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, new RenderBeam());
	}

	@Override
	public void setKeyBinding(String name, int value, boolean repeats) {
		KeyBindingHelper.addKeyBinding(name, value, repeats);
	}

	@Override
	public void registerKeyBindings() {
		KeyBindingRegistry.registerKeyBinding(new KeybindingHandler());
	}

	public void handleFXPacket(FXType type, double x, double y, double z, int[] extraInfo) {
		switch (type) {
			case BLOCK_BREAK: {
				addBlockDestroyParticles((int)x, (int)y, (int)z, extraInfo[0], extraInfo[1]);
			}
		}
	}
	
	@Override
	public void addBlockDestroyParticles(int x, int y, int z, int blockID, int meta) {
		if (blockID > 0) {
			Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(x, y, z, blockID, meta);
		}
	}

	public void addLightningFX(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration) {
		FXLightningBolt bolt = new FXLightningBolt(world, x1, y1, z1, x, y, z, seed, duration);
		bolt.defaultFractal();
		//TODO add this to method params
		bolt.setRandomType(4, 7);
		bolt.finalizeBolt();
	}
	
}
