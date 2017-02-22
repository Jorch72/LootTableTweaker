package net.darkhax.lttweaker;

import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.SystemUtils;

import net.darkhax.lttweaker.libs.Constants;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;

public class CommandTableDump extends CommandBase {

    @Override
    public String getName () {

        return "dumploot";
    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "none";
    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        sender.sendMessage(new TextComponentTranslation("command.loottabletweaks.startdump"));
        for (final Entry<String, LootTable> table : LTTMod.tables.entrySet()) {

            Constants.LOG.info("## " + table.getKey());

            final List<LootPool> thePools = LTTMod.getPools(table.getValue());

            if (thePools.isEmpty()) {
                Constants.LOG.info("No entries for this table!");
            }
            else {

                Constants.LOG.info(SystemUtils.LINE_SEPARATOR);
                Constants.LOG.info("|Pool|Name|Item|Weight|");
                Constants.LOG.info("|----|----|----|------|");
            }

            for (final LootPool pool : thePools) {

                for (final LootEntry entry : LTTMod.getLootEntries(pool)) {

                    if (entry instanceof LootEntryItem) {

                        final LootEntryItem item = (LootEntryItem) entry;
                        Constants.LOG.info(String.format("|%s|%s|%s|%d|", pool.getName(), entry.getEntryName(), item.item.getRegistryName().toString(), item.weight));
                    }
                }
            }

            Constants.LOG.info(SystemUtils.LINE_SEPARATOR);
        }

        sender.sendMessage(new TextComponentTranslation("command.loottabletweaks.enddump"));
    }
}
