package net.naturva.morphie.mr.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MenuUtils {

    public ItemStack createInventoryItem(String paramString1, int paramInt, String paramString2, ArrayList<String> paramArrayList, boolean paramBoolean) {
        ItemStack localItemStack = new ItemStack(Material.matchMaterial(paramString1), paramInt);
        ItemMeta localItemMeta = localItemStack.getItemMeta();
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        if (paramBoolean) {
            localItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', paramString2));
        localItemMeta.setLore(paramArrayList);
        localItemStack.setItemMeta(localItemMeta);
        return localItemStack;
    }

    public ItemStack createInventoryGlassItem(String paramString1, int glassInt, int paramInt, String paramString2, ArrayList<String> paramArrayList, boolean paramBoolean) {
        ItemStack localItemStack = new ItemStack(Material.matchMaterial(paramString1), paramInt, (short) glassInt);
        ItemMeta localItemMeta = localItemStack.getItemMeta();
        if (paramBoolean) {
            localItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', paramString2));
        localItemMeta.setLore(paramArrayList);
        localItemStack.setItemMeta(localItemMeta);
        return localItemStack;
    }
}
