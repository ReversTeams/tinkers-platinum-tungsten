package test;
import modernmods.modernfoundry.library.materials.MaterialRegistry;
import modernmods.modernfoundry.library.materials.definition.MaterialId;
import modernmods.modernfoundry.library.materials.stats.MaterialStatsId;
import modernmods.modernfoundry.library.recipe.material.MaterialRecipe;
import modernmods.modernfoundry.library.tools.part.IMaterialItem;
import modernmods.modernfoundry.tools.TinkerToolParts;
import modernmods.modernfoundry.tools.stats.HeadMaterialStats;
import modernmods.modernfoundry.tools.stats.PlatingMaterialStats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
@Mod("platinum_smoke")
public class PlatinumSmoke {
 private static int checks;
 private static void check(boolean condition,String message) {
  if(!condition)throw new AssertionError(message);
  checks++;System.out.println("ADDON_PASS "+message);
 }
 public PlatinumSmoke() {NeoForge.EVENT_BUS.addListener(this::run);}
 private void run(ServerStartedEvent event) {
  var server=event.getServer();boolean fixtures=Boolean.getBoolean("port.fixtureMetals");
  try {
   var registry=MaterialRegistry.getInstance();var manager=server.overworld().getRecipeManager();
   for(String metal:new String[]{"platinum","tungsten"}) {
    var id=new MaterialId("modernfoundry",metal);var material=MaterialRegistry.getMaterial(id);
    check(id.matches(material)==fixtures,metal+" material condition (fixtures="+fixtures+")");
    for(String recipe:new String[]{"ingot","nugget","block","casting","melting"})
     check(manager.byKey(ResourceLocation.parse("tinkersplatinumtungsten:"+metal+"/"+recipe)).isPresent()==fixtures,metal+" "+recipe+" condition");
    if(fixtures) {
     var stats=registry.<HeadMaterialStats>getMaterialStats(id,HeadMaterialStats.ID).orElseThrow();
     check(stats.durability()==(metal.equals("platinum")?600:850),metal+" original head durability");
     var traits=registry.getTraits(id,HeadMaterialStats.ID);
     check(traits.stream().anyMatch(t->t.getId().toString().equals("modernfoundry:"+(metal.equals("platinum")?"lustrous":"invariant"))),metal+" melee trait");
     var ranged=registry.getTraits(id,new MaterialStatsId("modernfoundry:limb"));
     check(ranged.stream().anyMatch(t->t.getId().toString().equals("modernfoundry:"+(metal.equals("platinum")?"olympic":"invariant"))),metal+" ranged trait");
     var stack=TinkerToolParts.pickHead.get().withMaterial(id);
     check(id.equals(IMaterialItem.getMaterialFromStack(stack)),metal+" tool part retains material");
     var saved=stack.save(server.registryAccess());var restored=ItemStack.parse(server.registryAccess(),saved).orElseThrow();
     check(id.equals(IMaterialItem.getMaterialFromStack(restored)),metal+" part survives serialization");
     var recipe=(MaterialRecipe)manager.byKey(ResourceLocation.parse("tinkersplatinumtungsten:"+metal+"/ingot")).orElseThrow().value();
     check(recipe.getIngredient().test(new ItemStack(metal.equals("platinum")?Items.GOLD_INGOT:Items.IRON_INGOT)) && recipe.getValue()==1 && recipe.getNeeded()==1,metal+" tagged ingot recipe");
    }
   }
   if(fixtures) {
    var armor=registry.<PlatingMaterialStats>getMaterialStats(new MaterialId("modernfoundry:tungsten"),new MaterialStatsId("modernfoundry:plating_chestplate")).orElseThrow();
    check(armor.durability()==420 && armor.armor()==6,"tungsten armor stats retained");
   }
   System.out.println("ADDON_TEST_SUCCESS checks="+checks+" fixtures="+fixtures);
   java.nio.file.Files.writeString(java.nio.file.Path.of("addon-server-result.txt"),"PASS "+checks+" fixtures="+fixtures);
  }catch(Throwable t){t.printStackTrace();System.out.println("ADDON_TEST_FAILURE "+t);try{java.nio.file.Files.writeString(java.nio.file.Path.of("addon-server-result.txt"),"FAIL "+t);}catch(Exception ignored){}}
  finally{if(server.isDedicatedServer())server.halt(false);}
 }
}
