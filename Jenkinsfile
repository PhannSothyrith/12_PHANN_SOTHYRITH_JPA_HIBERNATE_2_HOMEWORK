pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Ensure Gradle tool is correctly configured in Jenkins
        jdk 'JDK 21'         // Ensure JDK 21 is correctly configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube-202511104738-sonarqube-1:9000"   // Ensure SonarQube is running on this URL
        SONAR_LOGIN = "sqp_5a11b9330c12242e947e0916ebfb6719f90cdba1" // Your SonarQube token
    }

         stages {
                stage('Git Checkout') {
                    steps {
                        checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/RetSokhim/13_RET_SOKHIM_SPRING_DATA_JPA_HOMEWORK.git']])
                        echo 'Git Checkout Completed'
                    }
                }


        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {  // Ensure SonarQube is configured in Jenkins
                    withCredentials([string(credentialsId: 'demo', variable: 'GRADLE_TOKEN')]) {
                        script {
                            // Run the Gradle build and SonarQube analysis
                            sh """
                                ./gradlew sonar \
                                    -Dsonar.projectKey=demo \
                                    -Dsonar.projectName="demo" \
                                    -Dsonar.host.url=${SONAR_HOST_URL} \
                                    -Dsonar.login=${SONAR_LOGIN}
                            """
                        }
                        echo 'SonarQube Analysis Completed'
                    }
                }
            }
        }
    }
}
