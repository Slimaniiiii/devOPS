pipeline {
    agent any

    environment{
        SONARSERVER = 'sonarserver'
        SONARSCANNER = 'sonarscanner'
    }

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
        // stage('Deploy') {
        //     steps {
        //         sh 'docker-compose up -d'
        //     }
        // }
        
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
                stage("Nexus Deploy") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version} ARTVERSION";
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: 'http://193.95.105.45:8081',
                            groupId: 'tn.esprit.rh',
                            version: '1.0.0',
                            repository: 'Achat-release',
                            credentialsId: 'nexusid',
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } 
		    else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }


        stage("SonarQube Analysis") {
            environment {
                scannerHome = tool "${SONARSCANNER}"
            }
            steps {
                withSonarQubeEnv("${SONARSERVER}"){
                    sh '''${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=devOPS \
                    -Dsonar.projectName=devOPS \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.sources=src/ \
                    -Dsonar.java.binaries=target/classes \
                    -Dsonar.junit.reportsPath=target/surefile-reports/ \
                    -Dsonar.jacoco.reportPaths=target/jacoco.exec \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml'''
                }
            }
        }
        stage('Running the unit test...') {
            steps {
                sh 'mvn test'
            }
        }

        // stage('Nexus Deploy ') {
        //    steps {
        //         nexusArtifactUploader(
        //         nexusVersion: 'nexus3',
        //         protocol: 'http',
        //         nexusUrl: 'http://193.95.105.45:8081',
        //         groupId: 'tn.esprit.rh',
        //         version: '1.0.0',
        //         repository: 'Achat-release',
        //         credentialsId: 'nexusid',
        //         artifacts: [
        //             [  artifactId: 'achat',
        //                 classifier: '',
        //                 file: 'target/achat.jar',
        //                 type: 'jar']
        //         ]
        //         )
        //                 }
        //             }

 
        stage("Email notification sender ...") {
            steps {
                emailext attachLog: true, body: "${env.BUILD_URL} has result ${currentBuild.result}", compressLog: true, subject: "Status of pipeline: ${currentBuild.fullDisplayName}", to: 'saadaoui.mohamedaziz@esprit.tn,mohamedaziz.tahri@esprit.tn,houssem.slimani@esprit.tn'
            }
        }
    }
}
