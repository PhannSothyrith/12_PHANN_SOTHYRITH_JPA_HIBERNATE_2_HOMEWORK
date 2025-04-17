pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Ensure this Gradle tool is configured in Jenkins
        jdk 'JDK 21'         // Ensure this JDK is configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube-202511104738-sonarqube-1:9000"
        SONAR_LOGIN = "sqp_3718835b2bf31c52c01ba7f84724d77cf9e1b997"
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/PhannSothyrith/12_PHANN_SOTHYRITH_JPA_HIBERNATE_2_HOMEWORK.git'
                    ]]
                )
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    withCredentials([string(credentialsId: 'demo', variable: 'DEMO-TOKEN')]) {
                        sh """
                            ./gradlew clean test jacocoTestReport sonar \
                                -Dsonar.projectKey=demo \
                                -Dsonar.projectName="demo" \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_LOGIN} \
                                -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                        """
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    script {
                        def qualityGate = waitForQualityGate()
                        if (qualityGate.status != 'OK') {
                            error "Pipeline failed due to SonarQube quality gate failure: ${qualityGate.status}"
                        }
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("gradle2-app:latest")
                    echo "Docker Image Built Successfully"
                }
            }
        }
    }
}
