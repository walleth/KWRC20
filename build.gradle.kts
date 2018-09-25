buildscript {

    val kotlinVersion = "1.2.71"


    repositories {
        //google()
        jcenter()
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }
    
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("org.jetbrains.kotlin:kotlin-native-gradle-plugin:0.9")

    }
}

subprojects {
    repositories {
        jcenter()
    }
}
