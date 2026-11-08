package test;
import modernmods.modernfoundry.library.client.materials.MaterialRenderInfoLoader;
import modernmods.modernfoundry.library.materials.MaterialRegistry;
import modernmods.modernfoundry.library.materials.definition.MaterialId;
import modernmods.modernfoundry.tools.TinkerToolParts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
@EventBusSubscriber(modid="platinum_smoke",value=Dist.CLIENT)
public class PlatinumClientSmoke {
 private static int ticks;private static boolean done;
 @SubscribeEvent public static void tick(ClientTickEvent.Post event) {
  var mc=Minecraft.getInstance();if(done||mc.player==null||mc.level==null)return;
  if(++ticks<40)return;done=true;
  try {
   for(String metal:new String[]{"platinum","tungsten"}) {
    var id=new MaterialId("modernfoundry",metal);
    if(!id.matches(MaterialRegistry.getMaterial(id)))throw new AssertionError(metal+" not synced to client");
    var render=MaterialRenderInfoLoader.INSTANCE.getRenderInfo(id).orElseThrow();
    if(render.vertexColor()!=(metal.equals("platinum")?0xFFA3E7FE:0xFF6F6F62))throw new AssertionError(metal+" render data missing");
    var model=mc.getItemRenderer().getModel(TinkerToolParts.pickHead.get().withMaterial(id),mc.level,mc.player,0);
    if(model.getParticleIcon().contents().name().equals(MissingTextureAtlasSprite.getLocation()))throw new AssertionError(metal+" missing part texture");
   }
   System.out.println("ADDON_CLIENT_SUCCESS checks=6 world entered, materials synced, render data and part models loaded");
   java.nio.file.Files.writeString(java.nio.file.Path.of("addon-client-result.txt"),"PASS 6 client checks");
  }catch(Throwable t){t.printStackTrace();System.out.println("ADDON_CLIENT_FAILURE "+t);try{java.nio.file.Files.writeString(java.nio.file.Path.of("addon-client-result.txt"),"FAIL "+t);}catch(Exception ignored){}}
  mc.stop();
 }
}
