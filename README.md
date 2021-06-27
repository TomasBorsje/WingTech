![WingTech Banner](https://i.imgur.com/VjiKUxX.png)
Warning: This mod is in alpha! I am not responsible for world corruption, etc. (although that is very unlikely).
# Features
## Bio-Organic Constructor
Uses **Protein Paste** and power to assemble random animals.
## Atmospheric Collector
Uses power to collect **Nitrogen**, **Hydrogen**, and **Oxygen** from the atmosphere.
## Tea System
Teas are consumable items crafted with a **teacup** and another ingredient (usually a flower),
that provide a corresponding buff when consumed.

Add your own teas to the mod by putting a properly formatted JSON
file in the mod's config directory. WingTech will generate models,
recipes, lang files and registry entries automatically!
```
{
  "Type": "Dandelion", // Name of the Tea
  "Ingredient": "minecraft:dandelion", // Its crafting ingredient
  "Colour": 16770913, // RGB integer of the desired colour
  "EffectId": 10, // Effect id, see https://minecraft.fandom.com/wiki/Effect#Effect_IDs
  "EffectDuration": 6000, // Duration in ticks (20 per second)
  "EffectAmplifier": 1 // Effect level will be 1 + amplifier
}
```
If copy pasting this json, make sure to remove the comments! (// and onwards)
## Misc Items
**Industrial Fertilizer**: Bonemeal alternative.

**Awesome Flower**: Flower that generates in the world, crafts into orange dye.

**Tungsten Ore**: The main metal WingTech uses.

**Power Cell, Mesh, Nitrogen etc..**: Miscellaneous crafting ingredients.
