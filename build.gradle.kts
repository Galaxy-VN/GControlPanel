plugins {
    `maven-publish`
    id("java")
    id("io.izzel.taboolib") version "1.26"
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "io.github.galaxyvn"
version = "1.0"
description = "Advanced Control Player Thought A Gui"

taboolib {
    install(
        "common",
        "common-5",
        "module-kether",
        "module-ui",
        "module-ui-receptacle",
        "module-lang",
        "module-chat",
        "module-configuration",
        "module-nms",
        "module-nms-util",
        "platform-bukkit"
    )

    description {
        contributors {
            name("GalaxyVN")
        }

        dependencies {
            name("PlaceholderAPI").optional(true)
            name("SkinsRestorer").optional(true)
        }

        classifier = null
        version = "6.0.1-8"
    }
}

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly("ink.ptms.core:v11604:11604:all")
    compileOnly("ink.ptms.core:v11600:11600:all")
    compileOnly("ink.ptms.core:v11200:11200:all")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.shadowJar {
    dependencies {
        taboolib.modules.forEach { exclude(dependency("io.izzel:taboolib:${taboolib.version}:$it")) }
        exclude(dependency("com.google.code.gson:gson:2.8.6"))

        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/maven")
        exclude("lang")
        exclude("*.yml")
        exclude("plugin.yml.old")
        exclude("io/githun/galaxyvn/gcontrolpanel/module/internal")
    }
    relocate("taboolib", "${project.group}.taboolib")
    archiveClassifier.set("pure")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
            groupId = "io.github.galaxy"
        }
    }
    repositories {
        maven {
            url = uri("https://repo.iroselle.com/repository/maven-releases/")
            credentials {
                file("access.txt").also {
                    if (!it.exists()) return@credentials
                }.readLines().apply {
                    username = this[0]
                    password = this[1]
                }
            }
        }
    }
}