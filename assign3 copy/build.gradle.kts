plugins {
  java
  jacoco
  pmd
  application
  id("org.openjfx.javafxplugin") version "0.0.7"
}

application {
 mainClassName = "diagram.ui.DiagramUI"
}

repositories {
	mavenCentral()
}

dependencies {
  testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
  implementation("org.apache.commons:commons-lang3:3.5")
  implementation("org.reflections:reflections:0.9.11")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
	testRuntime("org.junit.platform:junit-platform-console:1.2.0")
}
 
sourceSets {
  main {
    java.srcDirs("src")
  }
  test {
    java.srcDirs("test")
  }
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform {}
}

pmd {
  ruleSets = listOf()
  ruleSetFiles = files("../conf/pmd/ruleset.xml")
}

tasks {
  val treatWarningsAsError =
    listOf("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

  getByName<JavaCompile>("compileJava") {
    options.compilerArgs = treatWarningsAsError      
  }

  getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs = treatWarningsAsError
  }

  getByName<JacocoReport>("jacocoTestReport") {
    afterEvaluate {
      getClassDirectories().setFrom(files(classDirectories.files.map {
        fileTree(it) { exclude("**/ui/**") }
      }))
    }
  }
}

javafx {
    modules = listOf("javafx.controls", "javafx.fxml")
}
 
defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")