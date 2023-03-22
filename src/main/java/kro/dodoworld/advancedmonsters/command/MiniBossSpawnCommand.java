package kro.dodoworld.advancedmonsters.command;

import kro.dodoworld.advancedmonsters.entity.miniboss.Bombie;
import kro.dodoworld.advancedmonsters.entity.miniboss.DiamondZombie;
import kro.dodoworld.advancedmonsters.entity.miniboss.EarthQuaker;
import kro.dodoworld.advancedmonsters.entity.miniboss.Inferno;
import kro.dodoworld.advancedmonsters.entity.miniboss.LeapingSpider;
import kro.dodoworld.advancedmonsters.entity.miniboss.Storm;
import kro.dodoworld.advancedmonsters.entity.miniboss.VoidGloom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MiniBossSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!command.getName().equals("admminiboss")) return false;
        if(!(sender instanceof Player) || !sender.isOp()){
            sender.sendMessage(Component.text("당신은 명령어를 사용할 권한이 없습니다!").color(NamedTextColor.LIGHT_PURPLE));
            return true;
        }
        if(args.length < 1){
            sender.sendMessage(Component.text("사용법 /admminiboss <보스 이름>").color(NamedTextColor.LIGHT_PURPLE));
            return true;
        }
        String bossName = args[0];
        Location loc = ((Player) sender).getLocation();
        switch (bossName) {
            case "diamond_zombie" -> DiamondZombie.createZombie(loc);
            case "leaping_spider" -> LeapingSpider.createLeapingSpider(loc);
            case "voidgloom" -> VoidGloom.createVoidGloom(loc);
            case "inferno" -> Inferno.createInferno(loc);
            case "bombie" -> Bombie.createBombie(loc);
            case "storm" -> Storm.createStorm(loc);
            case "earth_quaker" -> EarthQuaker.createEarthQuaker(loc);
            default -> sender.sendMessage(Component.text("알 수 없는 미니보스 입니다!").color(NamedTextColor.LIGHT_PURPLE));
        }
        return true;
    }
}
