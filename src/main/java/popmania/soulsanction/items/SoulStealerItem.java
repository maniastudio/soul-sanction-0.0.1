package popmania.soulsanction.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Item;

public class SoulStealerItem extends SwordItem {
    public SoulStealerItem(ToolMaterial material, FabricItemSettings fabricItemSettings) {
        super(material, 5, -3F, new Item.Settings());
    }

    @Override
    public boolean isEnchantable(net.minecraft.item.ItemStack stack) {
        return false; // Prevents enchanting
    }
}
