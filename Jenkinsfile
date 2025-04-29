pipeline {
    agent any

    tools {
        gradle 'Gradle'
        jdk 'JDK 21'
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube:9002"
        SONAR_PROJECT_KEY = "r-demo"
        SONAR_PROJECT_NAME = "r-demo"
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
                withCredentials([string(credentialsId: 'usonar-token', variable: 'SONAR_TOKEN')]) { // Updated credential ID
                    sh """
                        ./gradlew sonar \\
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \\
                            -Dsonar.projectName='${SONAR_PROJECT_NAME}' \\
                            -Dsonar.host.url=${SONAR_HOST_URL} \\
                            -Dsonar.token=${SONAR_TOKEN} \\
                            -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                    """
                }
            }
        }

        stage('SonarQube Report to Slack') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'usonar-token', variable: 'SONAR_TOKEN')]) { // Updated credential ID
                        def metrics = "code_smells,bugs,vulnerabilities,new_code_smells,new_bugs,new_vulnerabilities,coverage,new_coverage,duplicated_lines_density,sqale_rating,reliability_rating,security_rating"

                        def response = sh(
                            script: """curl -s -u ${SONAR_TOKEN}: "${SONAR_HOST_URL}/api/measures/component?component=${SONAR_PROJECT_KEY}&metricKeys=${metrics}" """,
                            returnStdout: true
                        ).trim()

                        def json = readJSON text: response
                        def measures = json.component.measures.collectEntries { [(it.metric): it.value] }

                        def projectUrl = "${SONAR_HOST_URL}/dashboard?id=${SONAR_PROJECT_KEY}"

                        env.SONAR_MSG = """
*SonarQube Full Report for ${SONAR_PROJECT_NAME}:*
• 🔍 Code Smells: *${measures['code_smells'] ?: '0'}*
• 🆕 New Code Smells: *${measures['new_code_smells'] ?: '0'}*
• 🐞 Bugs: *${measures['bugs'] ?: '0'}*
• 🐛 New Bugs: *${measures['new_bugs'] ?: '0'}*
• 🔓 Vulnerabilities: *${measures['vulnerabilities'] ?: '0'}*
• 🛡️ New Vulnerabilities: *${measures['new_vulnerabilities'] ?: '0'}*
• 🧪 Coverage: *${measures['coverage'] ?: 'N/A'}%*
• 🧬 New Code Coverage: *${measures['new_coverage'] ?: 'N/A'}%*
• ♻️ Duplicated Lines: *${measures['duplicated_lines_density'] ?: '0'}%*
• 📉 Maintainability Rating: *${measures['sqale_rating'] ?: 'N/A'}*
• ✅ Reliability Rating: *${measures['reliability_rating'] ?: 'N/A'}*
• 🔐 Security Rating: *${measures['security_rating'] ?: 'N/A'}*
• 🔗 [View Project on SonarQube](${projectUrl})
"""
                    }
                }
            }
        }
    }

    post {
        success {
            slackSend channel: '#sonarqube', color: 'good', message: "${env.SONAR_MSG}"
        }
        failure {
            slackSend channel: '#sonarqube', color: 'danger', message: "❌ SonarQube analysis failed for *${env.SONAR_PROJECT_NAME}*"
        }
    }
}
