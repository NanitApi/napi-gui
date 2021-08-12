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

* `type` - [String/Int] Material of item (name or id).
* `data` - [Int] Material data (for 1.12 and lower).
* `amount` - [Int] Amount of items in stack.
* `damage` - [Int] Item damage.
* `name` - [String] Display name.
* `lore` - [Strings list] Lore (description).
* `enchantments` - [Map] Enchantments map.
* `flags` - [Strings list] Flags list.
* `texture` - [String] Texture hash to create heads.
* `skull_owner` - [String] Skull owner to create heads.
* `unbreakable` - [Bool] Make item unbreakable.
* `color` - [String] Color of potion of leather items. (`#ffffff` or `WHITE`, `GREEN`, etc.)
* `model` - [Int] Custom model data (1.14+).
* `potion` - [Map] Potion data.
* `firework` - [Map] Firework data.
* `book` - [Map] Book data.
* `banner` - [Map] Banner data.

### Potion data

Potion data is a list of Map with keys and values:

* `type` - [String]. Required.
* `amplifier` - [Int]. Required.
* `duration` - [Int]. Required.
* `ambient` - [Bool].
* `particles` - [Bool].
* `icon` - [Bool].

### Firework data

### Book data

### Banner data