plugins {
  java
  jacoco
}

repositories {
	mavenCentral()
}

dependencies {
  testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
  testCompile("org.mockito:mockito-all:2.0.2-beta")
  implementation("org.json:json:20180813")
  implementation("com.googlecode.json-simple:json-simple:1.1.1")
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
        fileTree(it) { exclude("**/driver/**") }
      }))
    }
  }
}

task("runnoerror", JavaExec::class) {
   main = "airportinfo.driver.AirportStatusDriver"
   classpath = sourceSets["main"].runtimeClasspath
   args(listOf("airportcodes.txt"))
}

task("runwitherror", JavaExec::class) {
   main = "airportinfo.driver.AirportStatusDriver"
   classpath = sourceSets["main"].runtimeClasspath
   args(listOf("airportcodeswerr.txt"))
}

task("run") {
   dependsOn("runnoerror", "runwitherror")
}
 
defaultTasks("clean", "test", "jacocoTestReport")