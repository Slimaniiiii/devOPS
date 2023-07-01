pipeline {
    agent any

    stages {
        stage('Pulling from git...') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/Slimaniiiii/devOPS'
            }
        }
        
        stage("Build") {
            steps {
                sh 'mvn install -DskipTests=true'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose up -d'
            }
        }
        
        stage("SonarQube Analysis") {
            steps {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -Dsonar.host.url=http://193.95.105.45:9000'
            }
        }
        
        stage('Nexus Deploy ') {
            steps {
                nexusArtifactUploader artifacts: [
                    [
                        artifactId: 'achat',
                        classifier: '',
                        file: 'target/achat.jar',
                        type: 'jar'
                    ]
                ],
                        credentialsId: 'nexus3',
                        groupId: 'tn.esprit.rh',
                        nexusUrl: 'localhost:8081',
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        repository: 'Achat-release',
                        version: '1.0.0'
            }
        }

        stage('Build backend docker image') {
            steps {
                sh 'docker build -t Saadaoui_tahri_houssem/spring .'
            }
        }

        stage('Push images to Dockerhub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    script {
                        // Username : yuppi
                        // password : svdwi_Still_in_Brown_E
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        sh 'docker push Saadaoui_tahri_houssem/spring'
                    }
                }
            }
        }

        stage('Running the unit test...') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage("Email notification sender ...") {
            steps {
                emailext attachLog: true, body: "${env.BUILD_URL} has result ${currentBuild.result}", compressLog: true, subject: "Status of pipeline: ${currentBuild.fullDisplayName}", to: 'saadaoui.mohamedaziz@esprit.tn,mohamedaziz.tahri@esprit.tn,houssem.slimani@esprit.tn'
            }
        }
    }
}
