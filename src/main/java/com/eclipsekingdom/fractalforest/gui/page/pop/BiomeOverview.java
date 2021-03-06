package com.eclipsekingdom.fractalforest.gui.page.pop;

import com.eclipsekingdom.fractalforest.gui.MenuGlass;
import com.eclipsekingdom.fractalforest.gui.PopData;
import com.eclipsekingdom.fractalforest.gui.SessionData;
import com.eclipsekingdom.fractalforest.gui.page.Icons;
import com.eclipsekingdom.fractalforest.gui.page.MenuUtil;
import com.eclipsekingdom.fractalforest.gui.page.PageContents;
import com.eclipsekingdom.fractalforest.gui.page.PageType;
import com.eclipsekingdom.fractalforest.util.X.FGlass;
import com.eclipsekingdom.fractalforest.util.X.XMaterial;
import com.eclipsekingdom.fractalforest.worldgen.pop.TreePopulator;
import com.eclipsekingdom.fractalforest.worldgen.pop.util.TreeBiome;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.eclipsekingdom.fractalforest.sys.language.Message.*;

public class BiomeOverview implements PageContents {

    private Material writtenBook = XMaterial.WRITABLE_BOOK.parseMaterial();

    @Override
    public Inventory populate(Inventory menu, SessionData sessionData) {
        PopData popData = sessionData.getPopData();
        TreePopulator pop = popData.getPopulator();
        List<TreeBiome> biomes = new ArrayList<>();
        for (TreeBiome biome : pop.getBiomeToTreeSpawner().keySet()) {
            if (biome != TreeBiome.NONE) {
                biomes.add(biome);
            }
        }
        menu.setItem(4, Icons.createIcon(writtenBook, ChatColor.DARK_GRAY + MENU_EDIT_BIOMES.toString()));

        int offset = sessionData.getPageOffsetX();
        int biomesSize = biomes.size();

        for (int i = 0; i < 7; i++) {
            int index = i + 10;
            if (biomesSize > i + offset) {
                TreeBiome biome = biomes.get(i + offset);
                menu.setItem(index, biome.getItemStack());
                menu.setItem(index + 9, Icons.createGlass(MenuGlass.RED, ChatColor.RED + "X"));
            } else {
                if (biomesSize > i - 1 + offset) {
                    menu.setItem(index, Icons.createGlass(MenuGlass.LIME, ChatColor.GREEN + "+"));
                } else {
                    menu.setItem(index, Icons.BACKGROUND_ITEM);
                }
                menu.setItem(index + 9, Icons.BACKGROUND_ITEM);
            }
        }

        menu.setItem(30, Icons.createIcon(Material.ARROW, MENU_SCROLL_LEFT.toString()));
        menu.setItem(31, Icons.createIcon(Material.STONE_BUTTON, "+" + sessionData.getPageOffsetX()));
        menu.setItem(32, Icons.createIcon(Material.ARROW, MENU_SCROLL_RIGHT.toString()));

        return menu;
    }

    @Override
    public void processClick(Player player, Inventory menu, SessionData sessionData, int slot, ClickType clickType) {
        PopData popData = sessionData.getPopData();
        if (slot == 30) {
            sessionData.scrollLeft(player, this, menu);
        } else if (slot == 32) {
            sessionData.scrollRight(player, this, menu);
        } else {
            ItemStack itemStack = menu.getItem(slot);
            TreePopulator pop = popData.getPopulator();
            if (itemStack != null) {
                if (FGlass.equals(itemStack, MenuGlass.LIME)) {
                    sessionData.transition(player, PageType.BIOME_SELECT);
                } else if (FGlass.equals(itemStack, MenuGlass.RED)) {
                    ItemStack biomeStack = menu.getItem(slot - 9);
                    if (biomeStack != null && biomeStack.getType() != Material.AIR) {
                        ItemMeta meta = biomeStack.getItemMeta();
                        String name = meta.hasDisplayName() ? meta.getDisplayName() : "";
                        TreeBiome biome = TreeBiome.valueOf(name);
                        MenuUtil.playClickSound(player);
                        pop.getBiomeToTreeSpawner().remove(biome);
                        sessionData.registerEdit();
                        populate(menu, sessionData);
                    }
                }
            }
        }
    }


}
