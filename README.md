# Bukkit GUI api

This is an MVC framework to build GUI in Bukkit.

## Usage

Add library to project via JitPack.

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.NanitApi:napi-gui:main-SNAPSHOT'
}
```

## Item map format

There is ability to deserialize item from Map (`Map<String, Object>`) object.
This map can have this values:

* `type` - Material of item (name or id).
* `data` - Material data (for 1.12 and lower).
* `amount` - Amount of items in stack.
* `damage` - Item damage.
* `name` - Display name.
* `lore` - Lore (description).
* `enchantments` - Enchantments map.
* `flags` - Flags list.
* `texture` - Texture hash to create heads.
* `skull_owner` - Skull owner to create heads.
* `unbreakable` - Make item unbreakable.
* `color` - Color of potion of leather items.
* `model` - Custom model data (1.14+).
* `potion` - Potion data.
* `firework` - Firework data.
* `book` - Book data.
* `banner` - Banner data.

### Potion data

Potion data is a list of Map with keys and values:

* `type` - String. Required.
* `amplifier` - Int. Required.
* `duration` - Int. Required.
* `ambient` - Bool.
* `particles` - Bool.
* `icon` - Bool.

### Firework data

### Book data

### Banner data