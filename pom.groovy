project {
  modelVersion '4.0.0'
  parent {
    groupId 'org.appfuse'
    artifactId 'appfuse-web'
    version '3.5.0'
  }
  groupId 'com.raibledesigns'
  artifactId 'noxml'
  version '1.0-SNAPSHOT'
  packaging 'war'
  name 'AppFuse Spring MVC Application'
  properties {
    'java.version' '1.7'
    'appfuse.version' '3.5.0'
    'db.name' 'noxml'
    'amp.fullSource' 'false'
    'amp.genericCore' 'true'
    'dao.framework' 'jpa'
    'web.framework' 'spring'
  }
  dependencies {
    dependency {
      groupId 'org.appfuse'
      artifactId 'appfuse-${web.framework}'
      version '${appfuse.version}'
      type 'pom'
      exclusions {
        exclusion {
          artifactId 'appfuse-hibernate'
          groupId 'org.appfuse'
        }
      }
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-web'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-data-jpa'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter'
      exclusions {
        exclusion {
          groupId 'org.springframework.boot'
          artifactId 'spring-boot-starter-logging'
        }
      }
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-log4j2'
    }
    dependency {
      groupId 'com.h2database'
      artifactId 'h2'
    }
    dependency {
      groupId 'opensymphony'
      artifactId 'sitemesh'
    }
    dependency {
      groupId 'struts-menu'
      artifactId 'struts-menu'
    }
  }
  repositories {
    repository {
      releases {
        enabled 'false'
      }
      snapshots {
        enabled 'true'
      }
      id 'appfuse-snapshots'
      url 'https://oss.sonatype.org/content/repositories/appfuse-snapshots'
    }
  }
  build {
    plugins {
      plugin {
        groupId 'de.juplo'
        artifactId 'hibernate4-maven-plugin'
        configuration {
          scanDependencies 'none'
        }
      }
      plugin {
        groupId 'org.codehaus.mojo'
        artifactId 'dbunit-maven-plugin'
      }
    }
  }
  reporting {
    plugins {
      plugin {
        groupId 'org.codehaus.mojo'
        artifactId 'webtest-maven-plugin'
      }
    }
  }
  profiles {
    profile {
      id 'itest'
      build {
        plugins {
          plugin {
            groupId 'org.codehaus.cargo'
            artifactId 'cargo-maven2-plugin'
          }
          plugin {
            groupId 'org.codehaus.mojo'
            artifactId 'webtest-maven-plugin'
          }
        }
      }
    }
  }
}
