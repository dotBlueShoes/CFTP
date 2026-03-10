## Chapter 1. Creeps.

- configVersion validation
- isSpawnInLightButEasier
- isUponDeathEnabled
- isShearAndGildResistant
- isCreeperShearingEnabled
- isCreeperRefusingEnabled
- isCreeperGildingEnabled
- make sure everywhere "this.power" is used inside each creeper logic ! (theres at least 2 that dont - cookie, ender)

// 28.
"Lighting Creeper",	// done // ?
"Cookie Creeper",		// done // 3-6 cookies
"Ender Creeper",		// done // 1 ender eye
"Water Creeper",		// done // 1 water charge
"Earth Creeper",		// done // 1 earth charge
"Fire Creeper",		// done // 1 fire charge
"Dirt Creeper",		// done // 4-12 dirt blocks
"Lava Creeper",		// done // 2-3 fire charges // maybe call it lava charge? and create lava at place ??
"Wind Creeper",		// done // 1 wind charge
"Ghost Creeper",		// done // ghost egg (done)
"Nether Creeper",		// done // 4-12 netherrack
"Flip Creeper",		// done // ?
"Friendly Creeper",	// done // (none)
"Snow Creeper",		// done // 4-12 snow_blocks
"Swamp Creeper",		// done // 1 water/fire/earth charge
"Dark Creeper",		// done // ?
"Ballistic Creeper",	// done // 1 tnt
"Golden Creeper",		// done // 4-12 gold ingots
"Bridger Creeper",	// done // ?
"Piggy Creeper",		// done // 4-12 porkchops
"Amalgam Creeper",	// done // more gunpowder more elemental powder (done)
"Amethyst Creeper",	// done // 1-4 amethyst
"Brewer Creeper",		// done // ?
"Giant Creeper",		// done // ?
"Harvest Creeper",	// done // ?
"Oceanid Creeper",	// done // 1-4 coral block
"Sand Creeper",		// done // 4-12 sand blocks
"Sculk Creeper",		// done // ?
"Herobrine Creeper",  //		// 1-4 diamond or 4-16 iron


- change creepers weight											//
- rename amethyst to geode creeper									//
- make dirt creeper spawn one dirt block at it's position			//
- see why ghost creeper is not effected by wind creeper explosion	//
- see why mobs do this weird head thing when in survival			//
- make sculk creeper sprite animated								//
- see if lighting creeper is fast enough and low health				//
- give dirt a chance for rooted_dirt								//
- giant creeper														//
- herobrine creeper													// (maybe also gives drinkable )
- earth_charge creates rooted_dirt block at collision point			//
- redo fire_charge so it can be thrown								//
- make all creepers also drop 3rd item that is their element		// (looting adds chance so it's : 25%, 33%, 66%, 75%)
--------------------------------------------------------

finish creepers
: water, earth, fire, wind, lighting -> plains
: + spawn conditions, re-balance, 

: water, earth, fire, wind, lightning, ender, giant, ghost, flip -> plains

: cookie, ender, brewer, dark -> jungle
: sand, wind, dark, fire  -> desert
: snow, wind, flip, giant -> snowy

: ocenaid, ender -> beach
: amethyst, dark, dirt, ender -> caves
: fire, sculk, friendly, earth -> special underground biomes
: nether, lava, ghost -> nether

: piggy, amalgam, golden, harvest -> special

herobrine_creeper - teleports player to the nether
raw_creeper_fish - explodes when put into an oven which has coal, but 
used in recipe with sheers gives safe_raw_creeper_fish - can be used in trading

### Creeper Spectre
- Spawns with a small chance from ghost creeper death but only in nether (when overcharged it's 100% to spawn, 25% it's going to be overcharged too)


// nope // ### Creeper Stone (block)
// nope // 1. When mined or set a flame using flint_and_steel or fireball it releases a creeper-like spirit (particle/s).
// nope // 2. Either applies a timed or forever debuff making wilder creepers to spawn in the world.
// nope // 3. Needs a retexture (Cracked and/or light up sometimes by itself (so the user knows something's wrong), maybe make noise like "tssss" when it is being mined.)
// nope // 4. It also drops a normal stone.

### Retexture ideas
1. It might work to simply shift pixels on creeper's textures. - creeper texture

### Creeper Sculk
3. Missing proper spawn condition.
4. Missing random size.
5. Missing distinguish stats.
7. Missing loot-table.

### Creeper Sand 
1. Missing explode logic. -> could make a fun use of sand-like blocks replace blocks under said sand with primed-tnt/lava/air/monsters, or like make all blocks except bedrock in a chunk be affected by gravity.
2. Missing texture.
3. Missing proper spawn condition.
4. Missing random size.
5. Missing distinguish stats.
6. Missing charged behavior.
7. Missing loot-table.
8. Missing sand fall particle walking effect.
9. Missing custom explode sound. -> (No sound?)

### Creeper Oceanid (maybe instead block up movement for a duration of time)
1. Missing explode logic. -> water, fishes/water-animal, oceanic-greenery. on hard also gives mining fatigue for short duration of time.
2. Missing proper spawn condition. -> could only spawn certain moon-phase.
3. Missing random size.
4. Missing distinguish stats.
5. Missing charged behavior.
6. Missing loot-table.
7. Missing custom explode sound. -> water-splash
8. He spawns always in water. Has water breathing.
9. Does not swim but walks on the ocean floor instead.

### Creeper Harvest
1. Missing explode logic. -> makes props grow immediately, land-greenery. ParticleTypes.EGG_CRACK
2. Missing proper spawn condition. -> should only spawn certain moon-phase.
3. Missing random size.
4. Missing distinguish stats.
5. Missing charged behavior.
6. Missing loot-table.
7. Missing custom explode sound. -> (like harvest/plant/grass-cut)

### Creeper Giant
1. Missing explode logic. -> could make a bigger explosion and or summon normal creepers.
2. Missing texture.
3. Missing proper spawn condition. -> could only spawn certain moon-phase/structure/action.
4. Missing random size.
5. Missing distinguish stats.
6. Missing charged behavior.
7. Missing loot-table.
8. Missing custom giant sounds.

### Creeper Brewer
1. Missing explode logic. -> will explode in an effect of a random potion + can be applied a potion.
2. Missing proper spawn condition. -> could only spawn certain moon-phase/structure/action.
3. Missing random size.
4. Missing distinguish stats. 
5. Missing charged behavior. -> could add a level to said potion. 
6. Missing loot-table. 
7. Missing potion particle walking effect.
8. Missing custom explode sound. -> like potion splash sound (but no glass, but puff)

### Creeper Amethyst
1. Missing proper spawn condition. -> could only spawn certain moon-phase.
2. Missing random size.
3. Missing distinguish stats. 
4. Missing charged behavior. -> Instead now creates a normal size geode. 
5. Missing loot-table. 
6. Missing Amethyst sparkle walking effect.
7. Missing custom explode sound. -> like experience sound

### Creeper Amalgam
1. Missing explode logic. -> Explodes into easy/normal/hard <-> 2/3/4 random creepers.
2. Missing texture. -> It's texture has a bit of every creeper.
3. Missing proper spawn condition. -> should only spawn via action.
4. Missing random size.
5. Missing distinguish stats.
6. Missing charged behavior. -> explodes into 1.75 more random creepers.
7. Missing loot-table.
8. Missing custom explode particle.
9. Missing custom explode sound.

### Water Creeper 
1. Now sets the player water-breathing to easy/normal/hard <-> 10/5/0.
2. Missing proper spawn condition -> could only spawn certain moon-phase.
3. Missing custom explode sound. -> water-splash

### Dirt Creeper
1. it does however change sand/gravel into suspicious variants
2. Spawning condition.

### Bridger Creeper
1. missing logic.
2. Spawning condition - should spawn quite often but maybe not always (moon-phase or action locked).
3. it also needs a simple retexture (find old creeper texture).

---

### Creeper Giant

1. Spawning condition.
Twice the size. Twice/Thrice the health. Regenerates health.
Twice the fuse time. Twice the explosion radius! But also slightly slower.
Player has to slowly and safely kill it or skip it.
Spawns in Caves or during night in overworld only. Has a very small chance of spawning.

### Creeper Fuse

1. Retexture (it needs to look more like 2 cubes not diamonds).
2. Make for all creepers option to be sheared.
3. Implement creeper_shear action via scissors item.
4. Setting for enabling/disabling shearing creepers.
5. gunpowder + sawdust + creeper_fuse + sulphur -> C4? - old tnt sound???
> or creeper_fuse + elemental_dust + tnt -> elemental_creeper
> or creeper_fuse + gunpowder + tnt -> gunpowder_creeper
> or fuse + clock + tnt -> timed_tnt
> or simply fuse can replace one gunpowder in tnt recipe

/// change yellow and blue mushrooms to candle-like blocks (more than one mushroom inside one block)
/// grapes ? make leaves not only drop sticks/apple but also something more? -> birch catkins
/// recipe sawdust_block -> 4sawdust
/// sawdust block -> Block of Sawdust
/// golden and piggy creeper dont explode just by seeing a player.
/// positive creepers and hard creepers have lower chance of spawning
/// piggy creeper should also drop porkchop
/// lurker zombie -> 1block collision, always crawling, actually a bit faster then normal zombie.
/// fiery skeleton -> red, on fire, shots fire arrows spawns in fortress. (drops fire arrows?)
/// explosive shield -> when right-clicked will make a windy explosion
/// make turtle hat scares creepers away. or neutral.
/// funny idea ... portal creeper -> teleports player overworld->nether->end
///
/// it also would be cool if zombies could frenzy -> like rage every now and then making them faster for a limited time.
/// nah. still not good. -> but what if elemental powder could be eaten to give like 4-10 seconds of mining/running speed.
/// ### Elemental Dust -> Elemental Powder
/// 1. Rename.
/// 2. Make it look pore like pixie dust from terraria.
/// 3. burns just like placed sawdust but does not destroy itself?
/// 4. See if what creepers drop it what don't (some should drop elemental powder other gunpowder)

### Ender Creeper

1. Eyes on a different layer.
2. Should have an AI to teleport sometimes + water avoiding.
3. Spawning condition.

### Piggy Creeper

1. Spawning condition.

### Wind Creeper

1. Spawning condition.

### Dark Creeper

1. Spawning condition.

### Golden Creeper

1. Should give fewer hearts and spawn in groups of 1 always.
2. Spawning condition.

### Creeper Cookie

1. Should have a different explosion sound. (rewarding)
2. Make him drop random amount of cookies. Amount depends on difficulty setting.
3. Ensure the drop works for both explosion and kill.
4. Think of a good place where should this entity spawn.
5. Spawning condition - Spawns too much (should be jungle-like only biomes).

### Creeper Friendly

1. Make him go after other hostile mobs not only explode close range.
2. Make him tamable like wolves and cats.
3. Make his explosion exclude the player the creeper belongs to.
4. Spawning condition - definitely should only spawn at mushroom island in caves.

### Ghost Creeper

1. Something different should drop. not a ghost creeper spawn egg. Think about it.
2. Should just as piglin have a chance to spawn through nether portal.
3. On hard there's a chance upon death to create a spectre creeper instead.
4. Spawning condition.

### Deep Dark Creeper

1. Has a texture similar to the XP block.
2. Creates those XP blocks with a chance of generating skulkers, etc.
3. Should have an animated texture.
4. Spawning condition.

### Earth Creeper

1. Spawning condition.

### Nether Creeper

1. Needs a retexture (more contrast or missing detail)
2. Spawning condition - spawns too much. Should just as piglin have a chance to spawn through nether portal.

### Water Creeper

1. On hard difficulty also summons a 0-2 fishes.
2. Spawning condition.

### Earth Charge

1. When hit there should be a particle burst.
2. Ensure the recipe does not use ingot but the scarp item.
3. Maybe instead of crafting-bench require use of anvil?

### Fiery Horse

1. Add fire-resistant saddle. The effect only works when player is on an entity equipped with said saddle.
2. His goals are invalid as they look for grass around. fix it.
3. Make it drop leather like regular horse.
4. Make him not to go into lava by itself.
5. New sounds death, hurt, jump_run_something.

### Overwrites

1. See if I changed getLimitPerChunk for zombies and skeletons. What are implications of that.
2. See if I changed it for creepers. What are implications of that?
3. If not what did i change? why are there so many creepers?

## TESTS

### Farmland retexture.
1. so that it is not just dirt on the sides. (preferably a gradient from farmland-top to normal dirt at the bottom).

### Spider

1. Make the textures less dark. or like have more contrast.
2. ? Maybe getting bit would make player smaller or bigger for a small duration of a time. 

### Potions

1. Make the potion that makes the player smaller for a duration of a time.
2. Make the potion that makes the player bigger for a duration of a time.

### Spider Hole

1. Make the spider_hole visually look better.
2. Make the spider_hole protected from any fluid generation.
3. Make the spider_hole summon my custom spiders.
4. Divide spider_hole gen into spider_hole_swamp and spider_hole_jungle.

### Sulphur

1. Make sulphur_cloud spawn dust particles constantly.
2. Sound for when bucket is used (pfff) like throwing enormous amount of light dust.
3. Same sound for when a block is created using pickaxe.
4. Fix bug sometimes neighbouring sulphur cloud disappears (no sulphur cloud entity spawns) ... Why.
5. diamond-tier only (+ config option).

### New Villagers

1. New model/s, new textures, mew sounds.
2. Read, check code how can this be done. Maybe this can be done fully using resource-packs.

### Other

1. Make a spawn predicate so that spawning of a creature changes with difficulty set as to what biome and what group size.
2. Make some of the creepers rare to spawn (piggy, friendly).
3. Remove coins drop for now.

## FEATURES

### Elemental Vortex

1. A spawner like block transparent block, particle, emissive only, that might appear over every 2 night.
2. Spawns Elemental Creepers
3. Has a Nether variant.
4. OR! an entity which stays in place and creates creepers only if the player is somewhat close to it.

### Elemental Box 

1. An item that can be obtained only via looting. Contains a random elemental thing inside.
2. Water/Fire/Earth/Wind Creeper Egg, Water/Fire/Earth/Wind Charge, Elemental Powder or Spawn an elemental Vortex
3. Has a Nether and Overworld variant. (Gust/Lava/Nether/Wind Creeper Egg)

 Mushrooms

1. ? Make yellow mushroom grow in light
2. Make yellow mushroom only able to place and grow at swamp biomes
3. Make blue mushroom only able to place and grow at jungle biomes

### Config

1. An option to make all creepers always drop their explosion block loot_table at creeper's position.


### Ice Charge

1. Cannot be crafted but can be obtained via trading or loot-drop like from chest.
2. Places an ICE block when collided with a different block (turns lava into obsidian).
3. When hit a livingEntity gives them slow and effect of powder snow for a duration of 5 seconds.

### Invisible Creeper

1. Only visible in very close range, same explosion as normal creeper.

### Amalgam Creeper

1. Has a chunky-rainbow like look.
2. Spawns like 6-9 random elemental creepers.
3. Is very rare.

### Other

1. Randomize a little the sphere shape of all the creepers.
2. Right-clicking a normal creeper with a special item makes it a special kind of creeper like cookie_creeper with cookie.
3. Creeper in the bottle throw an elemental or normal creeper as a charge or potion or arrow.
4. An enchantment that increases the amount of experience gain when killing an enemy does not work with mending.
5. An enchantment to possess elemental creeper power.
6. Add a magical biome like from Thaumcraft. -> new wood type (maybe floating islands ?).
7. Make a fire bat (sets the player on fire on contact).
8. Make a poisonous bat (applies the poison on contact).
9. Make a bat-spider from times to time flyes around or flyes to the player.
10. ghost_creeper that is under the effect of any potion or other effect will now apply that effect to any living entities in a radius upon explosion.
11. Horse fiery still does not spawn as many times as it should...
    > This is due to lava. Strider entity is just more flexible and when said entity spawns there's no more need for horse to spawn.
    > Ideally I should separate IN_LAVA and ON_GROUND spawning for this to work...
    > To test this first I should remove strider entity from pool to see if the result would be better.
12. snow-creeper maybe should generate more POWDER_SNOW ?
13. giant octopus (meat-like orange color).


# OLD TODO

SoulOre + Souls
soul_fiery_ore did not work. HOWEVER a soul_ore_block with an animated texture like that would be so cool.
It would require the best shovel or pickaxe, maybe both, or something completely new?
And it would create a way to craft spawn eggs. Soul's could have nbt data that says what creature it is connected to.
and 8 rotten flesh + soul would create the egg.

Make stone variants with heads of monsters and maybe not only.
This block when mined it gives regular stone.
But when right-clicked with soul something special happens.
I also think with could be a teleporter maybe a marked teleporter.
A creeper-stone will only teleport to other creeper-stones or like
a dirt-block-stone will teleport to the alpha world dim ?

33. I copper rods, II blaze rods, III breeze rods allow control of the elemental charge
 I. the particle can now be controlled in the air for a limited time.
 II. Consumes 2 instead to create a stronger effect.
 III. Capable of holding Arcana energy (charges can be transformed into arcana using elemental extractor)
34. elemental extractor can be built using 8 magic cores (furnace) (up->rod capable of holding, down->charge)
35. magic core block can be made using 1 copper block and 4 elemental dusts
36. fire ingot, water ingot, wind ingot can be created using one copper ingot and a specific charge
37. new ingots along with new crystals and other hard to obtain items craft items with new abilities but can also be sold for money.


73. Cyborg Zombie
74. flesh rain
75. John creature
76. Spider creeper (spider 2.0 AI with creeper like texture which explodes just as normal creeper)
77. IMPORTANT! Make dark creeper also play a kinda scary sound effect.
79. Make jungle blue mushroom gen ~2x less
80. Elemental Ingot + Elemental Block (copper ingot + elemental power (not powder) ) 
81. Make ballistic creeper only spawn on hard difficulty.
82. Slimes have the simplified AI -> Pathfinding. This means I can create a creeper_bridger / breaker
-> It always goes player direction (no advanced pathfinding) and explodes when meet with any collision
83. Golden Creeper -> Gives Absorption healths for a duration of a time to all entities in an area.
84. Ender creeper should also be able to teleport other living entities.
85. Add a bug based of endermite -> Maybe some variants like normal/jungle/snow/nether (maybe they would interact with sludge liquid!)
86. Make Golden Creeper -> gives absorption hearts, maybe even a gold-ore block -> or replaces stone/deepstone/netherrack in a small radius with gold!
87. Flesh block does not drop itself it can be crafted with 8 flesh or found in caves as generated as block when destroyed gived john curse for the duration of a day - makes johns spawn.
88. Stomper invisible entity that makes fast moving noises spawns in caves or at night - does not attack (maybe does if certain condition is activated...)
89. Lurking Zombie (white flesh) - goes after light torches and destroys them or makes them unlit for easy mode.
90. Bridging Zombie (has a pickaxe) - slowly mines through to rich the player in straight line.
92. make ballistic creeper throw up to n tnt's and maybe recharge after ?
93. Skeletons with slowness arrows, skeletons which can eat golden apples ????
94. slider zombie it is in crawling pose always it can squeeze through 1 space. 
95. firestarter zombie - will set on fire any blocks in his path
97. maybe I could make it so golden hearts from creeper are not given fully if the total amount of hearts is higher then 30
98. cultists of herobrine (known as just cultists) they will attack the player, mobs, all (they are very strong early), player can follow clues left by them. following their quest will slowly make weird things. eventually readding herobrine back. 
99. molotov item thrown kinda like bow can have power of throw and when collided creates fire in a circular radius.
100. runic/elemental skeleton - glows in the dark has purple bones - maybe used for discovery.
101. cultists make a repeat sound like "one of use" maybe it could grow in intensity the more cultists are there.
102. red blaze (a stronger variant)
103. stonze (a blaze like creature but throws stone blocks at player?)
110. see why sometimes its red fire sometimes its blue both on soul block ???
111. could make the fire the entity is in also blue when in blue fire ???
112. firebat/firebee with firehive -> embedded in netherrack
113. new nether fluid molten-x (copper/iron/gold/?tin) it's a slightly tinted lava with a little more contrast
115. fishing rod zombie
119. wisps would be such a good addition cube like model - there wood always be a different one for each biome. They might be hard to kill, do not attack back, when killed drop "soul"
123. SlendermanRenderer uses EndermanEntityRenderState which is wrong as slenderman does not use special eyes texture and does not hold a block model.
124. Make a camera noise filter for slenderman entity.
125. Make a Slenderman spawn condition. (during special moon phase ?)
127. IMPORTANT! FIRE_CREEPER -> make the livingEntities in range deal 2-6 hearts of damage if no fire res. explosion.
128. FIRE_CREEPER -> If there would be unlit torches it could light them back on.
129. make snow_creeper able to change lava into obsidian ?
131. ash, ash block (can be colorized?) (applies slowness like soulsand?) (crafting item)
132. IMPORTANT! sulphur ore that is rare and more common in nether, + sulphur that is a possible to craft all that gunpowder crafts to.
133. smelting sulphur ore will make an explosion just like tnt does. sulphur ore will also explode if ignited just like tnt.
-> maybe just flammable. sulphur might not explode when in chunks.
134. rain or snow could/should create something special like a new type of flower or mushroom, maybe allow spawning of new entity, could increase the chances for water creepers
135. dependency -> https://www.curseforge.com/minecraft/mc-mods/burnt (some things are really cool, same energy, some not quite my thing)
137. coins need to have translucent 3d draw when dropped. This is something that should be postponed to 1.21.9

139. IMPORTANT! make bridger creeper explode upon any collision make its pathfinding simple
141. IMPORTANT! add missing recipes/droptables for all creepers
142. make creepers right-clickable with scissors to diffuse them which adds creeper fuses + config
143. IMPORTANT! make cobwebs drop normal string
144. IMPORTANT! Creepers should not replace blocks that are entities without dropping items, or destroy blast resistant blocks
145. make swamp trees and jungle trees spawn cobwebs, yellow, blue variants too.
146. IMPORTANT! add creeper jumper.
147. IMPORTANT! config file
148. meaty squid variant, squid meat?
149. make yellow mushroom less bright
150. make mushroom island generate big yellow and blue mushrooms
151. IMPORTANT! make yellow and blue mushroom grow their big variants
152. IMPORTANT! make recipes/droptables for yellow/blue mushroom blocks
153. Mushroom creeper? -> generates all mushroom upon detonation
154. each creeper type can now only drop a very specific disk. definitely a config option
155. chester entity like from old terra-firma-craft modpack.
156. void creeper -> capable of destroying bedrock
157. ore creeper -> generates random ore blocks, webs, fluids, ...
158. maybe some creepers can only be spawned from amalgam creeper explosion
159. SULPHUR - make it more common in desert (hot biomes) less common in other biomes
160. darken darker spots in cobweb variants sprites.
161. sand in a bottle (as a useless item (maybe?))
161. gold tnt (timed tnt) -> tnt + clock 
162. make sulphur cloud ignitable
163. make sulphur cloud have an animated texture
164. make sulphur cloud collectable via bucket ?
165. make a config option which says whether a sulphur cloud is being created or not
    if not the ores explode with their normal ore texture.
166. make a config option to make sulphur clouds not air like / cleanable. but triggerable with a flint and steel.
167. make sulphur ore get triggered via fire arrows.
168. signaler (repeater like device, but it has an ui in which a specific time of a day/night can be picked) requires a clock in its recipe.
169. masked tnt (a dirt like texture tnt) that can be brought just like suspicious sand/gravel from a new type of villager (maniac)
170. ! make sulphur cloud not explode when replaced with a different block but make it so that action triggers adjacent sulphur clouds to prime.
171. ! make sulphur clouds generate but not near lava on not on air
172. make a trade using sawdust + water bucket + emeralds that will at max level give fertilized_farmland block
173. plants on fertilized_farmland grow instantly but after single use are transformed into normal farmlands. they also do not need water source.
174. if I would introduce bugs, ants etc. i could make fertilized_farmland resistant to some bad effect.



Creatures From The Past
Chapter 1. Creeps.
Chapter 2. Ogres and magi.
-> ogres (frost, fire, -, cave, armored, hellish, overthrown (cave Dripstone Caves variant))
-> wisps (slime based, different color for every biome)
-> thaumcraft forest
-> wands
-> elemental ingot?

sludge fluid, (poisonus)
quicksand but dirt texture, water with cobweb logic (so it looks like animated dirt texture) it also has a distingish sound on enter, swim and exit

fluid water variant but the top layer texture is green only. (organic, infested)
- Duckweed, algae, pond scum (best), water moss

AbstractFireBlock, FireBlock, SoulFireBlock
-> add a chance of instead destorying a block create an ash/ashblock
-> add a mechanic to change sand into glass

- unknown potion (I) -> gives virus infested effect for 10 seconds, during this time u can drink milk to remove this effect. If u don't u turn into a zombie. This zombie will keep all your inventory and equip aromor and best sword that was in players inventory.

- fish creepers ( sponge creeper - destroys water blocks and places a sponge?)
- desert creeper
- nether midnight biome (dark blue, stone ruins, bluish or reddish everfire)
- aether... aether creeper (dirt, stone, ore)
- expirience fluid -> non-infinite, 3 tiles flow, standing in it gives expirience...
- floating islands biome
- only using rods u can cast fire charge (it might place lava with an enchant ot special wand)
- enchants
  : explosivnes
  : ray (instant but lesser effect, great for big distances)
  : effectivnes (makes the effect of the projectile or ray better)
  : projectiles
  - needle to extract expirience
  - something with chainmail ???

[
wand recipe
rod + honey +
magma cream + nether wart
special string?
]

[
maybe fish creepers as fishing rod item??
that item would spark and if picked would do a small explosion
if waited till it stopped sparking it then can be picked normally.
]

[

petals
- meat block re-texture
- meat block animate texture
- meat rain weather effect (creates flesh blocks)
- pumpkin head on top of a flesh block creates john
]

Missing Creepers
- Jump Creeper (jumps when moving)
- Mama Creeper (summons child creepers at death)
- Child Creeper (faster than normal creeper)
- Illusion Creeper (when near player create 2-easy, 3-normal/hard illusions)
  (original creeper explodes just as normal creeper, illusions don't explode for real)

Swamp ideas
- (mehh) rainbow slimes look at sheep code (as sheep can be colored in rgb)
- (cool) 0 or 1 web or always a web generates on under swamp tree crown and 0-1 web spawns instead of leaves at the crown
- (cool) special kind of web (blueish, greenish, yellowish like)
- bog spider throws webs at player
- rare change for a single frog_light to generate
- (cool) rare chance for a chest with simple potions, lingerings, splashes, arrows along with simple nature like items
- (cool) higher chance for mimic to spawn (when in hard/normal mode also during the day)

- new nether only fluid ? - required to craft new armor sets

// gimp - always do hue-chroma change or use some new color models

// Actual entity spawning is handled inside:
// SpawnHelper.spawnEntitiesInChunk