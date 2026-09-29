package Scoreboard.hide.scoreboardhide.mixin.client;

import Scoreboard.hide.scoreboardhide.client.ScoreboardhideClient;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class HudMixin {

    /**
     * 26.3 的 HUD 采用渲染状态管线，侧边栏记分板在此方法中写入渲染状态，
     * 在入口取消即可同时屏蔽普通侧边栏与队伍颜色侧边栏。
     */
    @Inject(
            method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/scores/Objective;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void scoreboardhide$hideSidebar(GuiGraphicsExtractor extractor, Objective objective, CallbackInfo ci) {
        if (ScoreboardhideClient.isScoreboardHidden()) {
            ci.cancel();
        }
    }
}
