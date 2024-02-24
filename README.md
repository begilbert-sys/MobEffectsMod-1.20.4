This project is intended to be a Minecraft mod which allows players to inherit abilities from mobs.

### Disclaimer 

As you might imagine, this project reuses some of the game's source code. Any method swiped from the source is marked with a path to the original method. 
### Mobs implemented so far:

| Mob            | Positive/Neutral Abilities                                                     | Negative Abilities                                            | Empty-Hand Ability                  | 
|----------------|--------------------------------------------------------------------------------|---------------------------------------------------------------|-------------------------------------|
| Blaze          | Slow floating /  Fire resistance                                               | Damaged by water                                              | Shoot a fireball                    |
| Cat/Ocelot     | Fast / Immune to fall damage / Scare creepers / Extra saturation from raw fish |                                                               |                                     |
| Chicken        | Feather falling / Seeds are edible                                             |                                                               | Spawn an egg                        |
| Creeper        |                                                                                | Fire causes player to explode                                 | Explode. This will kill the player. |
| Enderman       | Teleport randomly when hit                                                     | Damaged by water (note: this means rain will likely kill you) | Teleport randomly                   |
| Evoker         |                                                                                |                                                               | Evoker fangs                        |
| Guardian       | Thorns / Underwater breathing                                                  |                                                               | Guardian Beam                       |
| Elder Guardian | Thorns / Underwater breathing                                                  |                                                               | Guardian Beam                       |
| Panda          | Bamboo is edible                                                               |                                                               |                                     |
| Pufferfish     | Poison thorns / Underwater breathing                                           | Suffocate on land                                             |                                     |
| Parrots        | Limited flying / AOE healing from jukeboxes / Seeds are edible                 |                                                               |                                     |
| Slime          | All blocks are bouncy                                                          |                                                               |                                     |
| Sniffer        | Dirt-type blocks sometimes drop sniffer items / Torchflower seeds are edible   | Slowness                                                      |                                     |

### Planned Implementations 
| Mob                       | Positive/Neutral Abilities                                                                            | Negative Abilities                                       | Empty-Hand Ability                                                     | 
|---------------------------|-------------------------------------------------------------------------------------------------------|----------------------------------------------------------|------------------------------------------------------------------------|
| Hoglin                    | Fling targets into the air, lower knockback                                                           |                                                          |                                                                        |
| Zoglin                    | Fling targets into the air, lower knockback                                                           |                                                          |                                                                        |
| Axolotl                   | Fast underwater, permanent regeneration 1, immune to mining fatigue                                   | Slow on land                                             |                                                                        |
| Allay                     | Slow flying, feather falling, AOE healing from jukeboxes                                              |                                                          |                                                                        |
| Bat                       | Slow flying                                                                                           |                                                          |                                                                        |
| Camel                     | Cacti are edible, dash charging                                                                       |                                                          |                                                                        |
| Cod/Salmon/Tropical       | Water breathing                                                                                       | Suffocate on land                                        |                                                                        |
| Cow                       | Wheat is edible                                                                                       |                                                          | Generate milk (when holding bucket)                                    |
| Donkey                    |                                                                                                       |                                                          |                                                                        |
| Frog                      | Slime balls are edible, killed mobs drop froglight, fast water movement, water breathing, higher jump | Slow land movement                                       |                                                                        |
| Glow Squid                | Underwater breathing, illuminate underwater                                                           | Suffocate on land                                        |                                                                        |
| Horse                     |                                                                                                       |                                                          |                                                                        |
| Mooshroom                 | Wheat is edible                                                                                       |                                                          | Generate milk (when holding bucket), generate stew (when holding bowl) |
| Mule                      |                                                                                                       |                                                          |                                                                        |
| Pig                       | Raw potatoes and beetroots are edible                                                                 |                                                          |                                                                        |
| Pufferfish                | Poison thorns, underwater breathing                                                                   | Suffocate on land                                        |                                                                        |
| Rabbit                    | Jump boost, dandelions are edible                                                                     | Wolves are hostile                                       |                                                                        |
| Sheep                     |                                                                                                       |                                                          |                                                                        |
| Skeleton Horse            |                                                                                                       |                                                          |                                                                        |
| Sniffer                   |                                                                                                       |                                                          |                                                                        |
| Snow Golem                | Leave trail of snow, immune to powder snow                                                            | Water damage, temperature damage (mitigated by fire res) | Throw snowballs                                                        |
| Squid                     | Water breathing                                                                                       | Suffocation on land                                      |                                                                        |
| Strider                   | Fire res, walk on lava, warped fungus is edible                                                       | Water damage, slow on land,                              |                                                                        |
| Tadpole                   |                                                                                                       |                                                          |                                                                        |
| Turtle                    | Fast in water, underwater breathing                                                                   | Slow on land                                             |                                                                        |
| Villager/Wandering Trader |                                                                                                       |                                                          |                                                                        |
| Bee                       | Slow limited flying, honey is edible                                                                  |                                                          |                                                                        |
| Cave Spider               | Blocks are climbable, attacks inflict poison, not slowed by string                                    |                                                          |                                                                        |
| Dolphin                   | Fast swimming, extended water breathing, extra saturation from raw fish                               | Suffocates slowly out of water                           |                                                                        |
| Drowned                   |                                                                                                       |                                                          |                                                                        |
| Enderman                  | Teleport when damaged                                                                                 | Water damage                                             |                                                                        |
| Fox                       |                                                                                                       |                                                          |                                                                        |
| Goat                      |                                                                                                       |                                                          |                                                                        |
| Iron Golem                | Increased defense                                                                                     | Sink faster in water                                     | Heal when holding iron ingot                                           |
| Llama                     | Hay bales are edible                                                                                  |                                                          | Spit                                                                   |
| Wolf                      |                                                                                                       |                                                          |                                                                        |
| Zombified Piglin          | Fire res                                                                                              |                                                          |                                                                        |
| Creeper                   |                                                                                                       |                                                          |                                                                        |
| Endermite                 |                                                                                                       |                                                          |                                                                        |
| Ghast                     |                                                                                                       |                                                          |                                                                        |
| Hoglin                    |                                                                                                       |                                                          |                                                                        |
| Husk                      | Moderate damage boost, inflict hunger on contact                                                      |                                                          |                                                                        |
| Magma Cube                |                                                                                                       |                                                          |                                                                        |
| Phantom                   |                                                                                                       |                                                          |                                                                        |
| Piglin Brute              |                                                                                                       |                                                          |                                                                        |
| Pillager                  |                                                                                                       |                                                          |                                                                        |
| Ravager                   |                                                                                                       |                                                          |                                                                        |
| Shulker                   |                                                                                                       |                                                          |                                                                        |
| Silverfish                |                                                                                                       |                                                          |                                                                        |
| Skeleton                  |                                                                                                       |                                                          |                                                                        |
| Slime                     |                                                                                                       |                                                          |                                                                        |
| Stray                     |                                                                                                       |                                                          |                                                                        |
| Vex                       |                                                                                                       |                                                          |                                                                        |
| Vindicator                |                                                                                                       |                                                          |                                                                        |
| Warden                    | Huge damage boost                                                                                     | Blindness                                                | Sonic boom                                                             |
| Witch                     |                                                                                                       |                                                          |                                                                        |
| Wither Skeleton           |                                                                                                       |                                                          |                                                                        |
| Zombie/Zombie Villager    | Moderate damage boost                                                                                 | Burn in sunlight                                         |                                                                        |


Known Bugs:
* Incorrect death message for creeper explosion death 
* GuardianEntity should not be using TrackedData (TrackedData is bad for mods)
* Blaze floating doesn't deactivate till ground is hit
* Jukebox AOE Healing is sometimes delayed when the world is loaded 