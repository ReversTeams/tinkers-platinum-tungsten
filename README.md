# Tinkers' Platinum & Tungsten — NeoForge 1.21.1

Ports the original data/texture addon to **Minecraft 1.21.1 / NeoForge** for [Hexa's unofficial Tinkers' Construct port, version 1.6.1](https://modrinth.com/mod/hexa-tinkers-construct-unofficial-port/version/SbT1iise). That download identifies itself in-game as **Modern Foundry** (`modernfoundry`). This addon is not affiliated with SlimeKnights and does not target the separate Zazow port or a future official SlimeKnights port.

## Install

- Minecraft 1.21.1 and NeoForge 21.1.228 or newer within 21.1.x; tested on 21.1.265, Java 21.
- Install the linked unofficial port and this addon's release JAR in `mods/` on client and server.
- The linked port bundles the Hilt 1.13 build used in testing. A separate Hilt download is not needed. The standalone Modrinth Hilt 1.13 build crashed world loading during testing; use the bundled copy.
- Supply platinum/tungsten through a metal mod or datapack using `c:ingots/<metal>`, `c:nuggets/<metal>`, `c:storage_blocks/<metal>` and `c:molten_<metal>` fluid tags. This addon does not add ingots or ore generation itself.

Materials keep the original condition: enable them when the corresponding ingot tag exists, or when the base mod's `force_integration_materials` option is enabled. Conversion recipes require the relevant metal tags.

## What changed

- NeoForge low-code metadata with correct loader and required dependencies.
- Minecraft 1.21.1 recipe directories and NeoForge recipe conditions.
- `forge:` common tags become `c:`; Mantle condition/color references become Hilt references.
- Material, modifier, texture and translation references use the target port's `modernfoundry` namespace.
- Preserves original material stats, traits, fluid quantities, temperatures and texture image data, including tungsten armor stats.
- The old Forge test subproject remains as historical source; the root build now targets NeoForge only.

## Build

Use a Java 21 JDK:

```powershell
.\gradlew.bat build
```

The installable JAR is in `build/libs/`. The `-sources.jar` is for development. Dependency downloads for development runs are pinned to the tested Modrinth version.

## Validation

See [PORT-STATUS.md](PORT-STATUS.md) and the reproducible test harness in [validation/](validation/README.md).

## Credits and license

MIT. Original platinum/tungsten material implementation and textures by KnightMiner/SlimeKnights, restored in the original addon by Mitemi for ReversTeam. The original LICENSE is retained. The NeoForge port adapts the existing addon; dependency mods are not bundled in this addon's release JAR.
