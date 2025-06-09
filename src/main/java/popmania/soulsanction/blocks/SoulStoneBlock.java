package popmania.soulsanction.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;

public class SoulStoneBlock extends Block {
    public SoulStoneBlock() {
        super(AbstractBlock.Settings
                .copy(net.minecraft.block.Blocks.BEDROCK)
                .strength(-1.0F, 3600000.0F)
                .dropsNothing());
    }
}