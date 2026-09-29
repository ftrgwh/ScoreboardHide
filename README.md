# ScoreboardHide

一个 **Minecraft Fabric 客户端 mod**：在客户端隐藏游戏内的记分板侧边栏（sidebar），不影响其他玩家和服务器数据。

- 目标版本：**Minecraft 26.3**（适配新版未混淆 + 渲染状态管线）
- 加载器：Fabric Loader `0.19.5+`
- 依赖：Fabric API（任意近期版本）
- 运行环境：Java 25（MC 26.3 的要求，正版启动器会自动准备）

## 功能

- 进游戏后记分板**默认隐藏**
- 按 **O** 键（可在「选项 → 控制」中修改）切换显示/隐藏
- 切换时在快捷栏上方显示提示（中英文随游戏语言）
- 仅修改本地渲染：服务器记分板数据原样保留，纯客户端 mod，单人/服务器均可用

## 使用

1. 安装 [Fabric Loader](https://fabricmc.net/use/installer/)
2. 把 [Fabric API](https://modrinth.com/mod/fabric-api) 和本 mod 的 jar 放进 `.minecraft/mods/`
3. 启动游戏即可，按 O 键切换

## 构建

```bash
./gradlew build
```

产物在 `build/libs/scoreboardhide-1.0.0.jar`（`*-sources.jar` 是源码包，不需要安装）。

## 项目结构

```
src/client/java/.../client/ScoreboardhideClient.java   # 客户端入口：注册按键、切换状态
src/client/java/.../mixin/client/HudMixin.java         # Mixin：取消记分板侧边栏渲染
src/client/resources/.../lang/                          # 中英文语言文件
src/main/resources/fabric.mod.json                      # mod 元数据
```

## 实现原理

26.3 的 HUD 使用渲染状态管线，记分板侧边栏在
`net.minecraft.client.gui.Hud#displayScoreboardSidebar(GuiGraphicsExtractor, Objective)`
中写入渲染状态。本 mod 通过 Mixin 在该方法入口处按开关状态取消调用（cancellable inject），
普通侧边栏与队伍颜色侧边栏（如 `sidebar.team.red`）一并隐藏。

## 适配其它 Minecraft 版本

修改 `gradle.properties` 中的 `minecraft_version`、`loader_version`、`fabric_version` 即可（版本号可在 https://modmuss50.me/fabric.html 查询）。

注意：26.3 之前的版本（≤1.21.x）HUD 类名和结构不同，需要把 `HudMixin` 的目标改为旧版的
`net.minecraft.client.gui.Gui#renderScoreboardSidebar`。
