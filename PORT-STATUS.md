# Port status

Target: Minecraft 1.21.1, NeoForge 21.1.265, Hexa unofficial Tinkers' Construct 1.21.1-1.6.1-NeoForge (Modern Foundry), and its bundled Hilt 1.13.

Completed checks:

- Java 21 build successful.
- Real dedicated world without metal tags: 12 checks passed. Both conditional material definitions and all ten recipes remain disabled.
- Real dedicated world with test metal tags: 25 checks passed. Both materials, ten recipes, original head durability, melee/ranged traits, part creation and serialization, tagged ingot values, and tungsten chestplate stats are verified.
- Integrated client world: the same 25 server checks passed; six client checks passed for material synchronization, original material colors, and nonmissing tool-part models.

The validation-only tag fixtures alias vanilla gold/iron items to the metal tags. These fixtures and the test mod are not packaged in the production addon. They test the tag-based integration without requiring an unrelated metal mod.

Limits observed in the dependency: the client logged an error loading `modernfoundry:staff`'s embellishment modifier model map (`modernfoundry:material` model loader) and warnings about missing modifier/armor-trim textures. The addon's tested material render information and pick-head models loaded successfully. This port does not claim to repair those unrelated dependency features. Other modpacks, other unofficial ports, and old-world migration have not been validated.

Optional `tinkers_things` texture resources are carried forward with remapped material suffixes; compatibility with a separate 1.21.1 Tinkers' Things mod has not been validated.
