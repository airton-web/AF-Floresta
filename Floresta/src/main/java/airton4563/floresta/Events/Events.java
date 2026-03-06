package airton4563.floresta.Events;

import airton4563.floresta.Commands.Floresta;
import airton4563.floresta.Database.Account;
import airton4563.floresta.Database.Skins.*;
import airton4563.floresta.Enchantments.Derrubador;
import airton4563.floresta.Enchantments.Efficiency;
import airton4563.floresta.Enchantments.Fortune;
import airton4563.floresta.Enchantments.Motoserra;
import airton4563.floresta.Floresta.FlorestaUtil;
import airton4563.floresta.Main;
import airton4563.floresta.Util;
import airton4563.floresta.Variables.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sun.java2d.pipe.BufferedTextPipe;

import java.io.IOException;
import java.util.*;

import static airton4563.floresta.Floresta.FlorestaUtil.isCorrectToolForBlock;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Account account = new Account(event.getPlayer());
        account.create();
        Wood wood = new Wood(event.getPlayer());
        wood.create();
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            Floresta.inFloresta.remove(event.getPlayer());
            Floresta.lastLocation.remove(event.getPlayer());
            Floresta.timer.remove(event.getPlayer());
            event.getPlayer().getInventory();
        }
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent event) {
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            Player player = event.getPlayer();
            player.getInventory().clear();
            Floresta.lastLocation.remove(player);
            Floresta.inFloresta.remove(player);
            Floresta.cancelTask(player);
            player.getInventory().clear();
            if (Floresta.timer.containsKey(player)) {
                int value = Floresta.timer.get(player);
                Floresta.timer.remove(player);
                Account account = new Account(player);
                account.setColumn("time", value);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Floresta.inFloresta.contains((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) throws IOException {
        if (event.getPlayer().getItemInHand().getType().toString().equalsIgnoreCase(Motoserra.getEnchantBook().getType().toString())) {
            Player player = event.getPlayer();
            if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Motoserra.getEnchantBook().getItemMeta().getDisplayName())) {
                    Account account = new Account(player);
                    String last_axe = account.getLastAxe();
                    if (last_axe == null) {
                        Wood variavel = new Wood(player);
                        variavel.setMotoserra(true);
                    } else if (last_axe.equalsIgnoreCase("wood")) {
                        Wood variavel = new Wood(player);
                        variavel.setMotoserra(true);
                    } else if (last_axe.equalsIgnoreCase("stone")) {
                        Stone variavel = new Stone(player);
                        variavel.setMotoserra(true);
                    } else if (last_axe.equalsIgnoreCase("iron")) {
                        Iron variavel = new Iron(player);
                        variavel.setMotoserra(true);
                    } else if (last_axe.equalsIgnoreCase("gold")) {
                        Gold variavel = new Gold(player);
                        variavel.setMotoserra(true);
                    } else if (last_axe.equalsIgnoreCase("diamond")) {
                        Diamond variavel = new Diamond(player);
                        variavel.setMotoserra(true);
                    }
                    player.setItemInHand(new ItemStack(Material.AIR));
                    player.sendMessage("§aEncantamento de Motoserra adicionado ao seu Machado!");
                    return;
                }
            }
        }
        if (Floresta.inFloresta.contains(event.getPlayer())) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                ItemStack machado = FlorestaUtil.getMachado(player);
                if (player.getItemInHand().getType() == machado.getType()) {
                    event.setCancelled(true);
                    Inventory menu = Bukkit.getServer().createInventory(null, 45, "Evolua seu machado");
                    ItemStack fortuna = Fortune.getEnchantedBook(player);
                    ItemStack eficiencia = Efficiency.getEnchantedBook(player);
                    ItemStack derrubador = Derrubador.getEnchantedBook(player);

                    menu.setItem(12, fortuna);
                    menu.setItem(13, eficiencia);
                    menu.setItem(14, derrubador);

                    Wood woodDB = new Wood(player);
                    ItemStack wood = woodDB.getAxe();

                    Stone stoneDB = new Stone(player);
                    ItemStack stone = stoneDB.getAxe();

                    Iron ironDB = new Iron(player);
                    ItemStack iron = ironDB.getAxe();

                    Gold goldDB = new Gold(player);
                    ItemStack gold = goldDB.getAxe();

                    Diamond diamondDB = new Diamond(player);
                    ItemStack diamond = diamondDB.getAxe();

                    if (stone != null) {
                        menu.setItem(29, stone);
                    } else {
                        menu.setItem(29, Util.createItem(Material.BARRIER, "§c§k---------", null));
                    }
                    if (iron != null) {
                        menu.setItem(30, iron);
                    } else {
                        menu.setItem(30, Util.createItem(Material.BARRIER, "§c§k---------", null));
                    }
                    if (gold != null) {
                        menu.setItem(32, gold);
                    } else {
                        menu.setItem(32, Util.createItem(Material.BARRIER, "§c§k---------", null));
                    }
                    if (diamond != null) {
                        menu.setItem(33, diamond);
                    } else {
                        menu.setItem(33, Util.createItem(Material.BARRIER, "§c§k---------", null));
                    }

                    Account account = new Account(player);
                    String lastAxe = account.getLastAxe();
                    if (lastAxe == null) {
                        menu.setItem(40, wood);
                    } else if (lastAxe.equalsIgnoreCase("wood")) {
                        menu.setItem(40, wood);
                    } else if (lastAxe.equalsIgnoreCase("stone")) {
                        menu.setItem(40, stone);
                    } else if (lastAxe.equalsIgnoreCase("iron")) {
                        menu.setItem(40, iron);
                    } else if (lastAxe.equalsIgnoreCase("gold")) {
                        menu.setItem(40, gold);
                    } else if (lastAxe.equalsIgnoreCase("diamond")) {
                        menu.setItem(40, diamond);
                    }
                    player.openInventory(menu);
                }
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        if (event.getInventory().getName().contains("Informações de:")) {
            event.setCancelled(true);
            return;
        }
        if (event.getInventory().getName().equalsIgnoreCase("Evolua seu machado")) {
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType().toString().contains("AXE")) {
                FlorestaUtil.updateMachado(player, event.getCurrentItem());
            }
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aFortuna")) {
                int levelMax = Fortune.getLevelMax();
                Account account = new Account(player);
                String type = account.getLastAxe().toUpperCase();
                int lenhas = account.getLenhas();
                int level = 0;
                if (type.contains("WOOD")) {
                    Wood variavel = new Wood(player);
                    level = variavel.getFortune();
                } else if (type.contains("STONE")) {
                    Stone variavel = new Stone(player);
                    level = variavel.getFortune();
                } else if (type.contains("IRON")) {
                    Iron variavel = new Iron(player);
                    level = variavel.getFortune();
                } else if (type.contains("GOLD")) {
                    Gold variavel = new Gold(player);
                    level = variavel.getFortune();
                } else if (type.contains("DIAMOND")) {
                    Diamond variavel = new Diamond(player);
                    level = variavel.getFortune();
                }

                level++;

                if (level >= levelMax) {
                    player.sendMessage("§cVocê já está com o encantamento no nível máximo.");
                    player.closeInventory();
                    return;
                }

                int lenhasUpgrade = Fortune.getLenhas(level);

                if (lenhas >= lenhasUpgrade) {
                    account.removeLenhas(lenhasUpgrade);
                    if (type.contains("WOOD")) {
                        Wood variavel = new Wood(player);
                        variavel.addFortune(1);
                    } else if (type.contains("STONE")) {
                        Stone variavel = new Stone(player);
                        variavel.addFortune(1);
                    } else if (type.contains("IRON")) {
                        Iron variavel = new Iron(player);
                        variavel.addFortune(1);
                    } else if (type.contains("GOLD")) {
                        Gold variavel = new Gold(player);
                        variavel.addFortune(1);
                    } else if (type.contains("DIAMOND")) {
                        Diamond variavel = new Diamond(player);
                        variavel.addFortune(1);
                    }
                    player.sendMessage("§aEvoluído com sucesso!");
                    player.closeInventory();
                } else {
                    player.sendMessage("§cVocê não tem lenhas suficiencias.");
                    player.closeInventory();
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEficiência")) {
                int levelMax = Efficiency.getLevelMax();
                Account account = new Account(player);
                String type = account.getLastAxe().toUpperCase();
                int lenhas = account.getLenhas();
                int level = 0;
                if (type.contains("WOOD")) {
                    Wood variavel = new Wood(player);
                    level = variavel.getEfficiency();
                } else if (type.contains("STONE")) {
                    Stone variavel = new Stone(player);
                    level = variavel.getEfficiency();
                } else if (type.contains("IRON")) {
                    Iron variavel = new Iron(player);
                    level = variavel.getEfficiency();
                } else if (type.contains("GOLD")) {
                    Gold variavel = new Gold(player);
                    level = variavel.getEfficiency();
                } else if (type.contains("DIAMOND")) {
                    Diamond variavel = new Diamond(player);
                    level = variavel.getEfficiency();
                }

                level++;

                if (level >= levelMax) {
                    player.sendMessage("§cVocê já está com o encantamento no nível máximo.");
                    player.closeInventory();
                    return;
                }

                int lenhasUpgrade = Efficiency.getLenhas(level);

                if (lenhas >= lenhasUpgrade) {
                    account.removeLenhas(lenhasUpgrade);
                    if (type.contains("WOOD")) {
                        Wood variavel = new Wood(player);
                        variavel.addEfficiency(1);
                    } else if (type.contains("STONE")) {
                        Stone variavel = new Stone(player);
                        variavel.addEfficiency(1);
                    } else if (type.contains("IRON")) {
                        Iron variavel = new Iron(player);
                        variavel.addEfficiency(1);
                    } else if (type.contains("GOLD")) {
                        Gold variavel = new Gold(player);
                        variavel.addEfficiency(1);
                    } else if (type.contains("DIAMOND")) {
                        Diamond variavel = new Diamond(player);
                        variavel.addEfficiency(1);
                    }
                    player.sendMessage("§aEvoluído com sucesso!");
                    player.closeInventory();
                } else {
                    player.sendMessage("§cVocê não tem lenhas suficiencias.");
                    player.closeInventory();
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aDerrubador")) {
                int levelMax = Derrubador.getLevelMax();
                Account account = new Account(player);
                String type = account.getLastAxe().toUpperCase();
                int lenhas = account.getLenhas();
                int level = 0;
                if (type.contains("WOOD")) {
                    Wood variavel = new Wood(player);
                    level = variavel.getDerrubador();
                } else if (type.contains("STONE")) {
                    Stone variavel = new Stone(player);
                    level = variavel.getDerrubador();
                } else if (type.contains("IRON")) {
                    Iron variavel = new Iron(player);
                    level = variavel.getDerrubador();
                } else if (type.contains("GOLD")) {
                    Gold variavel = new Gold(player);
                    level = variavel.getDerrubador();
                } else if (type.contains("DIAMOND")) {
                    Diamond variavel = new Diamond(player);
                    level = variavel.getDerrubador();
                }

                if (level == levelMax) {
                    player.sendMessage("§cVocê já está com o encantamento no nível máximo.");
                    player.closeInventory();
                    return;
                }

                level++;

                int lenhasUpgrade = Derrubador.getLenhas(level);

                if (lenhas >= lenhasUpgrade) {
                    account.removeLenhas(lenhasUpgrade);
                    if (type.contains("WOOD")) {
                        Wood variavel = new Wood(player);
                        variavel.addDerrubador(1);
                    } else if (type.contains("STONE")) {
                        Stone variavel = new Stone(player);
                        variavel.addDerrubador(1);
                    } else if (type.contains("IRON")) {
                        Iron variavel = new Iron(player);
                        variavel.addDerrubador(1);
                    } else if (type.contains("GOLD")) {
                        Gold variavel = new Gold(player);
                        variavel.addDerrubador(1);
                    } else if (type.contains("DIAMOND")) {
                        Diamond variavel = new Diamond(player);
                        variavel.addDerrubador(1);
                    }
                    player.sendMessage("§aEvoluído com sucesso!");
                    player.closeInventory();
                    return;
                } else {
                    player.sendMessage("§cVocê não tem lenhas suficiencias.");
                    player.closeInventory();
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getWorld().getName().equalsIgnoreCase(Config.World)) {
            if (Floresta.inFloresta.contains(event.getPlayer())) {
                event.setCancelled(true);
                String block = getWoodType(event.getBlock().getType(), event.getBlock().getData());
                if (block != null) {
                    Player player = event.getPlayer();
                    ItemStack item = player.getItemInHand();
                    item.setDurability((short) 0);
                    boolean motoserraStatus = false;
                    Map<Block, Map<Material, Byte>> motoserraMap = new HashMap<>();
                    boolean derrubadorStatus = false;
                    Map<Block, Map<Material, Byte>> derrubadorMap = new HashMap<>();
                    if (item != null) {
                        if (item.getType().toString().contains("WOOD") || item.getType().toString().contains("STONE") || item.getType().toString().contains("IRON") || item.getType().toString().contains("GOLD") || item.getType().toString().contains("DIAMOND")) {
                            int lenhas = 1;
                            if (item.getType().toString().contains("WOOD")) {
                                Wood variavel = new Wood(player);
                                int fortune = variavel.getFortune();
                                int derrubador = variavel.getDerrubador();
                                String motoserra = variavel.getMotoserra();
                                lenhas = lenhas + fortune;
                                if (motoserra.equalsIgnoreCase("true")) {
                                    motoserraStatus = true;
                                    motoserraMap = breakTreeFullMotoserra(event.getBlock());
                                }  else if (derrubador != 0) {
                                    derrubadorStatus = true;
                                    derrubadorMap = breakTreeFull(derrubador, event.getBlock());
                                }
                            } else if (item.getType().toString().contains("STONE")) {
                                lenhas = (int) (lenhas * 1.1);
                                Stone variavel = new Stone(player);
                                int fortune = variavel.getFortune();
                                int derrubador = variavel.getDerrubador();
                                String motoserra = variavel.getMotoserra();
                                lenhas = lenhas + fortune;
                                if (motoserra.equalsIgnoreCase("true")) {
                                    motoserraStatus = true;
                                    motoserraMap = breakTreeFullMotoserra(event.getBlock());
                                }  else if (derrubador != 0) {
                                    derrubadorStatus = true;
                                    derrubadorMap = breakTreeFull(derrubador, event.getBlock());
                                }
                            } else if (item.getType().toString().contains("IRON")) {
                                lenhas = (int) (lenhas * 1.5);
                                Iron variavel = new Iron(player);
                                int fortune = variavel.getFortune();
                                int derrubador = variavel.getDerrubador();
                                String motoserra = variavel.getMotoserra();
                                lenhas = lenhas + fortune;
                                if (motoserra.equalsIgnoreCase("true")) {
                                    motoserraStatus = true;
                                    motoserraMap = breakTreeFullMotoserra(event.getBlock());
                                }  else if (derrubador != 0) {
                                    derrubadorStatus = true;
                                    derrubadorMap = breakTreeFull(derrubador, event.getBlock());
                                }
                            } else if (item.getType().toString().contains("GOLD")) {
                                lenhas = (int) (lenhas * 2.0);
                                Gold variavel = new Gold(player);
                                int fortune = variavel.getFortune();
                                int derrubador = variavel.getDerrubador();
                                String motoserra = variavel.getMotoserra();
                                lenhas = lenhas + fortune;
                                if (motoserra.equalsIgnoreCase("true")) {
                                    motoserraStatus = true;
                                    motoserraMap = breakTreeFullMotoserra(event.getBlock());
                                }  else if (derrubador != 0) {
                                    derrubadorStatus = true;
                                    derrubadorMap = breakTreeFull(derrubador, event.getBlock());
                                }
                            } else if (item.getType().toString().contains("DIAMOND")) {
                                lenhas = (int) (lenhas * 2.2);
                                Diamond variavel = new Diamond(player);
                                int fortune = variavel.getFortune();
                                int derrubador = variavel.getDerrubador();
                                String motoserra = variavel.getMotoserra();
                                lenhas = lenhas + fortune;
                                if (motoserra.equalsIgnoreCase("true")) {
                                    motoserraStatus = true;
                                    motoserraMap = breakTreeFullMotoserra(event.getBlock());
                                }  else if (derrubador != 0) {
                                    derrubadorStatus = true;
                                    derrubadorMap = breakTreeFull(derrubador, event.getBlock());
                                }
                            }

                            if (isCorrectToolForBlock(item, event.getBlock())) {
                                byte data = event.getBlock().getData();
                                Material material = event.getBlock().getType();
                                event.getBlock().setType(Material.BEDROCK);
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        event.getBlock().setType(material);
                                        event.getBlock().setData(data);
                                    }
                                }, 300);
                            } else {
                                Util.sendActionBar(player, "§cEssa ferramenta não pode quebrar essa árvore!");
                                return;
                            }
                            if (motoserraStatus) {
                                int amount = 0;
                                for (Map.Entry<Block, Map<Material, Byte>> entry : motoserraMap.entrySet()) {
                                    amount++;
                                    Block blockLoop = entry.getKey();
                                    Material material = blockLoop.getType();
                                    byte data = blockLoop.getData();
                                    blockLoop.setType(Material.BEDROCK);
                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                        @Override
                                        public void run() {
                                            event.getBlock().setType(material);
                                            event.getBlock().setData(data);
                                            blockLoop.setType(material);
                                            blockLoop.setData(data);
                                        }}, 300);
                                }
                                lenhas = lenhas + amount;
                            }
                            if (derrubadorStatus) {
                                int amount = 0;
                                for (Map.Entry<Block, Map<Material, Byte>> entry : derrubadorMap.entrySet()) {
                                    amount++;
                                    Block blockLoop = entry.getKey();
                                    Material material = blockLoop.getType();
                                    byte data = blockLoop.getData();
                                    blockLoop.setType(Material.BEDROCK);
                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                        @Override
                                        public void run() {
                                            event.getBlock().setType(material);
                                            event.getBlock().setData(data);
                                            blockLoop.setType(material);
                                            blockLoop.setData(data);
                                        }}, 300);
                                }
                                lenhas = lenhas + amount;
                            }

                            Account account = new Account(player);
                            account.addLenhas(lenhas);
                            Util.sendActionBar(player, "§a+" + lenhas + " Lenha");
                        }
                    }
                }
            }
        }
    }

    private Map<Block, Map<Material, Byte>> breakTreeFull(int nivel, Block block) {
        double chanceFull = 0.005 * nivel;
        Map<Block, Map<Material, Byte>> materiais = new HashMap<>();
        if (Math.random() < chanceFull) {
            Set<Block> blocksToBreak = new HashSet<>();
            findTreeBlocks(block, blocksToBreak);
            for (Block treeBlock : blocksToBreak) {
                Map<Material, Byte> data = new HashMap<>();
                data.put(treeBlock.getType(), treeBlock.getData());
                materiais.put(treeBlock, data);
            }
        }
        return materiais;
    }

    private Map<Block, Map<Material, Byte>> breakTreeFullMotoserra(Block block) {
        Set<Block> blocksToBreak = new HashSet<>();
        findTreeBlocks(block, blocksToBreak);
        Map<Block, Map<Material, Byte>> materiais = new HashMap<>();
        for (Block treeBlock : blocksToBreak) {
            Map<Material, Byte> data = new HashMap<>();
            data.put(treeBlock.getType(), treeBlock.getData());
            materiais.put(treeBlock, data);
        }
        return materiais;
    }

    private static final Set<Material> TREE_MATERIALS = new HashSet<Material>() {{
        add(Material.LOG);
        add(Material.LOG_2);
    }};

    public void findTreeBlocks(Block block, Set<Block> blocksToBreak) {
        if (blocksToBreak.contains(block) || !TREE_MATERIALS.contains(block.getType())) {
            return;
        }
        blocksToBreak.add(block);
        BlockFace[] faces = {
                BlockFace.UP,
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.DOWN,
                BlockFace.NORTH_EAST,
                BlockFace.NORTH_WEST,
                BlockFace.SOUTH_EAST,
                BlockFace.SOUTH_WEST
        };
        for (BlockFace face : faces) {
            Block relative = block.getRelative(face);
            findTreeBlocks(relative, blocksToBreak);
        }
    }

    public String getWoodType(Material material, byte data) {
        if (material == Material.LOG) {
            if (data == 0 || data == 8 || data == 4) {
                return "oak_log";
            } else if (data == 1 || data == 9 || data == 5) {
                return "spruce_log";
            } else if (data == 2 || data == 10 || data == 6) {
                return "birch_log";
            } else if (data == 3 || data == 11 || data == 7) {
                return "jungle_log";
            } else {
                return null;
            }
        } else if (material == Material.LOG_2) {
            if (data == 0 || data == 8 || data == 4) {
                return "acacia_log";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
