package Scoreboard.hide.scoreboardhide.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class ScoreboardhideClient implements ClientModInitializer {

    /**
     * 记分板默认隐藏，按切换键可随时显示/隐藏。
     */
    private static boolean scoreboardHidden = true;

    private static KeyMapping toggleKey;

    public static boolean isScoreboardHidden() {
        return scoreboardHidden;
    }

    @Override
    public void onInitializeClient() {
        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.scoreboardhide.toggle",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_O,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                scoreboardHidden = !scoreboardHidden;
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.translatable(
                            scoreboardHidden ? "message.scoreboardhide.hidden" : "message.scoreboardhide.shown"));
                }
            }
        });
    }
}
