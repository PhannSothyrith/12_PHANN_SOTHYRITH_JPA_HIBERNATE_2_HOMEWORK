pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Ensure this Gradle tool is configured in Jenkins
        jdk 'JDK 21'         // Ensure this JDK is configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube-202511104738-sonarqube-1:9000"
        SONAR_LOGIN = "sqp_4cb612ac193c379687118ac3b12d7f8dbe1e3174"
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/PhannSothyrith/12_PHANN_SOTHYRITH_JPA_HIBERNATE_2_HOMEWORK.git'
                    ]]
                )
                echo '✅ Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    withCredentials([string(credentialsId: 'r-demo', variable: 'GRADLE_TOKEN')]) {
                        sh """
                            ./gradlew clean build -x test \
                                -Dsonar.projectKey=r-demo \
                                -Dsonar.projectName="r-demo" \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_LOGIN} \
                        """
                    }
                }
            }
        }

    }
}

