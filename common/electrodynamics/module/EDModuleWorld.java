package electrodynamics.module;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.block.BlockDecorative;
import electrodynamics.block.BlockLithiumClay;
import electrodynamics.block.BlockOre;
import electrodynamics.block.BlockWormseed;
import electrodynamics.block.EDBlocks;
import electrodynamics.block.item.ItemBlockDecorative;
import electrodynamics.block.item.ItemBlockOre;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.core.Strings;
import electrodynamics.util.BiomeHelper;
import electrodynamics.world.TickHandlerMBS;
import electrodynamics.world.gen.WorldGenClay;
import electrodynamics.world.gen.WorldGenLimestone;
import electrodynamics.world.gen.WorldGenPlant;
import electrodynamics.world.gen.WorldGenOre;

public class EDModuleWorld extends EDModule {

	@Override
	public void preInit() {
		EDBlocks.blockOre = new BlockOre( BlockIDs.BLOCK_ORE_ID ).setUnlocalizedName( Strings.BLOCK_ORE_NAME );
		GameRegistry.registerBlock( EDBlocks.blockOre, ItemBlockOre.class, Strings.BLOCK_ORE_NAME );
		for( int i = 0; i < Ore.values().length; i++ ) {
			ItemStack ore = new ItemStack( EDBlocks.blockOre, 1, i );

			LanguageRegistry.addName( ore, Ore.get( i ).getLocalizedName( "en_US" ) );
			MinecraftForge.setBlockHarvestLevel( EDBlocks.blockOre, i, "pickaxe", Ore.get( i ).harvestLevel );
			OreDictionary.registerOre( Ore.get( i ).oreDictionaryName, ore );
		}

		EDBlocks.blockDecorative = new BlockDecorative( BlockIDs.BLOCK_DECORATIVE_ID ).setUnlocalizedName( Strings.BLOCK_DECORATIVE );
		GameRegistry.registerBlock( EDBlocks.blockDecorative, ItemBlockDecorative.class, Strings.BLOCK_DECORATIVE );
		for( int i = 0; i < BlockDecorative.blockNames.length; i++ ) {
			LanguageRegistry.addName( new ItemStack( EDBlocks.blockDecorative, 1, i ), BlockDecorative.blockNames[i] );
		}

		EDBlocks.blockWormseed = new BlockWormseed( BlockIDs.BLOCK_WORMSEED_ID ).setUnlocalizedName( Strings.BLOCK_WORMSEED );
		GameRegistry.registerBlock( EDBlocks.blockWormseed, Strings.BLOCK_WORMSEED );
		LanguageRegistry.addName( EDBlocks.blockWormseed, "Wormseed" );

		EDBlocks.blockLithiumClay = new BlockLithiumClay(BlockIDs.BLOCK_LITHIUM_CLAY_ID).setUnlocalizedName(Strings.ORE_LITHIUM_CLAY);
		GameRegistry.registerBlock(EDBlocks.blockLithiumClay, Strings.ORE_LITHIUM_CLAY);
		LanguageRegistry.addName(EDBlocks.blockLithiumClay, "Lithium-Rich Clay");
	}

	@Override
	public void init() {
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_DECORATIVE_ID, 2, new ItemStack(EDBlocks.blockDecorative, 1, 0), 0F);
		
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 1), "XX", "XX", 'X', EDBlocks.blockDecorative);
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 3), "XXX", "XXX", 'X', EDBlocks.blockDecorative);
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 4), "XX", "XX", 'X', new ItemStack(EDBlocks.blockDecorative, 1, 1));
		
		// Cobaltite
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 0, 8, 16, 64, 4));
		// Chalcopyrite
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 1, 8, 32, 78, 8));
		// Galena
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 2, 4, 16, 32, 6));
		// Lithium
		GameRegistry.registerWorldGenerator(new WorldGenClay(BlockIDs.BLOCK_LITHIUM_CLAY_ID, 4));
		// Magnetite
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 3, 8, 16, 32, 4));
		// Nickel
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 4, 8, 16, 64, 8));
		// Wolframite
		// TODO Write world-gen code for spawn near lava
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, 5, 4, 6, 16, 4));
		// Limestone
		GameRegistry.registerWorldGenerator(new WorldGenLimestone(BlockIDs.BLOCK_DECORATIVE_ID, 25));
		// Voidstone
		GameRegistry.registerWorldGenerator(new WorldGenOre(BlockIDs.BLOCK_ORE_ID, Ore.VOIDSTONE.ordinal(), 2, 1, 10, 6));
		// Wormseed
		GameRegistry.registerWorldGenerator(new WorldGenPlant(BlockIDs.BLOCK_WORMSEED_ID, 0, BiomeHelper.getBiomesForTypes(Type.PLAINS, Type.SWAMP, Type.HILLS, Type.FOREST, Type.JUNGLE, Type.MOUNTAIN)));
		GameRegistry.registerWorldGenerator(new WorldGenPlant(BlockIDs.BLOCK_WORMSEED_ID, 1, BiomeHelper.getBiomesForTypes(Type.DESERT, Type.WASTELAND)));
	}

	@Override
	public void postInit() {
		// Might need some side-sensitivity for these tick handlers.
		TickRegistry.registerTickHandler(TickHandlerMBS.instance(), Side.SERVER);
		TickRegistry.registerTickHandler(TickHandlerMBS.instance(), Side.CLIENT);
	}

}
