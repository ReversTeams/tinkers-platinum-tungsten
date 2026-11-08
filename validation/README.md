# Runtime validation

This is a separate development-only mod, excluded from the addon release JAR.
It runs against the published unofficial port and its bundled Hilt build.

Build the addon first with Java 21. From the repository root:

```powershell
.\gradlew.bat build
.\gradlew.bat -p validation clean runServer
.\gradlew.bat -p validation clean runServer -PmetalFixtures
```

Expected log markers: `ADDON_TEST_SUCCESS checks=12 fixtures=false` and `ADDON_TEST_SUCCESS checks=25 fixtures=true`.
Set `validation/run/eula.txt` to `eula=true` before the server run. Local server properties should use `server-ip=127.0.0.1`, `server-port=25588`, and `online-mode=false` for this isolated test only.

To run the client test, copy `validation/run/world` to `validation/run/saves/AddonTest`, then run:

```powershell
.\gradlew.bat -p validation runClient -PmetalFixtures
```

The client automatically opens the copied test world, checks synchronized materials, colors and part models, then closes. Expected marker: `ADDON_CLIENT_SUCCESS checks=6`.

Behavior checks write `addon-server-result.txt` / `addon-client-result.txt` in the test run directory. Check these files and the markers: a Gradle success alone does not prove the behavior checks passed.

Only the validation project supplies the fake metal tags. No fake gold/platinum or iron/tungsten associations are shipped in the real addon.
