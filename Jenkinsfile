pipeline {
    agent any

    stages {
        stage("Java Version Check") {
            steps {
                sh 'java -version'
            }
        }
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
        
        stage('Build backend docker image') {
            steps {
                sh 'docker build -t spring .'
                sh 'docker images'
            }
        }

        stage('Push images to Dockerhub') {
            steps {
                    script {
                        sh "docker login -u yupii -p svdwi_Still_in_Brown_E"
                        sh "docker tag spring yupii/spring"
                        sh 'docker push yupii/spring'
                    }
            }
        }

        stage('Running the unit test...') {
            steps {
                sh 'mvn clean test'
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
        // stage("SonarQube Analysis") {
        //     steps {
        //         sh 'mvn sonar:sonar'
        //     }
        // }
        stage("Email notification sender ...") {
            steps {
                emailext attachLog: true, body: "${env.BUILD_URL} has result ${currentBuild.result}", compressLog: true, subject: "Status of pipeline: ${currentBuild.fullDisplayName}", to: 'saadaoui.mohamedaziz@esprit.tn,mohamedaziz.tahri@esprit.tn,houssem.slimani@esprit.tn'
            }
        }
    }
}
